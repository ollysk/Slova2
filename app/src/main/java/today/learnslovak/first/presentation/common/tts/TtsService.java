package today.learnslovak.first.presentation.common.tts;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.TextUtils;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;
import today.learnslovak.first.domain.model.Lang;

@Singleton
public class TtsService implements TtsCallback {

  private final Map<Lang, TextToSpeech> ttsMap = new HashMap<>();
  private final Context context;
  private final float speechRate = 1;
  private final List<Map<String, Lang>> speakBatch = new ArrayList<>(10);
  private int lastUtteranceHash;
  private TtsCallback callback;

  @Inject public TtsService(@ApplicationContext Context context) {
    this.context = context;
  }

  private void initTtsIfNeeded(Lang lang) {
    if (!isTtsInited(lang)) {
      init(lang);
    }
  }

  boolean isTtsInited(Lang lang) {
    return getTts(lang).isPresent();
  }

  Optional<TextToSpeech> getTts(Lang lang) {
    return Optional.ofNullable(ttsMap.get(lang));
  }

  public void init(final Lang lang) {

    if (lang != null && !ttsMap.containsKey(lang)) {
      ttsMap.put(lang, getNewTts(lang));
    }
  }

  public void removeNotInited(final Lang lang) {
    Timber.d("TTS remove not inited %s", lang);
    ttsMap.remove(lang);
  }

  private TextToSpeech getNewTts(Lang lang) {
    return new TextToSpeech(context, status -> {
      if (status == TextToSpeech.SUCCESS) {
        prepareNewTts(lang);
      } else {
        Timber.w("TTS init failed for %s", lang);
      }
    }, "com.google.android.tts");
  }

  private void prepareNewTts(Lang lang) {
    getTts(lang).ifPresent((tts) -> {

      int result = setLanguage(tts, lang);

      if (isTtsLangAvailable(result)) {

        setOnUtteranceProgressListener(tts);
        Timber.i("TTS init OK: %s", lang);
      } else {
        removeNotInited(lang);
        getCallback().onTtsLangMissingData(lang, context);
      }
    });
  }

  int setLanguage(TextToSpeech tts, Lang lang) {
    return tts.setLanguage(new Locale(lang.name()));
  }

  boolean isTtsLangAvailable(int result) {
    return result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED;
  }

  @Override public void onTtsLangMissingData(Lang lang, Context context) {
    initInstallTtsDataIntent(lang);
  }

  TtsCallback getCallback() {
    return callback == null ? this : callback;
  }

  public void setCallback(TtsCallback callback) {
    this.callback = callback;
  }

  //todo refactor to 2 methods, eliminate try and how to install specific lang, not send to general tts langs screen?
  void initInstallTtsDataIntent(Lang lang) {
    //Toast.makeText(mContext, "TextToSpeech - not installed language: " + lang, Toast.LENGTH_SHORT).show();
    // new TtsCallback() {}.onLangMissingData(lang,context);

    Intent installIntent = new Intent();
    installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
    installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    installIntent.setPackage("com.google.android.tts");
    try {

      context.startActivity(installIntent);

/*            Intent intent = new Intent(context, PopupActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //if (!activityExtra.isEmpty())
            {

                intent.putExtra("type", "TTS");
            }
            context.startActivity(intent);*/

    } catch (Exception e) {
      Timber.e(e);
    }
  }

  void setOnUtteranceProgressListener(TextToSpeech textToSpeech) {
    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
      @Override public void onStart(String utteranceId) {

        //Timber.d("utterance-start %s", utteranceId);

      }

      @Override public void onDone(String utteranceId) {
        //Timber.d("utterance-done %s", utteranceId);

      }

      @Override public void onError(String utteranceId) {
        Timber.e("utterance-error %s", utteranceId);
      }
    });
  }

  public void addToSpeakBatch(String text, Lang lang) {
    speakBatch.add(Collections.singletonMap(text, lang));
  }

  public void speakBatch() {
    speakBatch.forEach(el -> el.entrySet()
        .forEach(e -> speak(e.getKey(), TextToSpeech.QUEUE_ADD, e.getValue(), 1)));
  }

  public void speak(String text, int queueMode, Lang lang) {
    speak(text, queueMode, lang, getSpeechRate(text));
    lastUtteranceHash = text.hashCode();
  }

  public void playSilence(long durationInMs, int queueMode) {
    if (durationInMs > 0) {
      getAnyTts().playSilentUtterance(durationInMs, queueMode, "");
    }
  }

  private TextToSpeech getAnyTts() {
    return ttsMap.values().stream().findAny().orElse(getNewTts(Lang.SK));
  }

  private void speak(final String text, final int queueMode, final Lang lang,
      final float speechRate) {

    if (TextToSpeech.QUEUE_FLUSH == queueMode) {
      stop();
    }
    initTtsIfNeeded(lang);

    getTts(lang).ifPresent((tts) -> {
      tts.setSpeechRate(speechRate);
      tts.speak(prepareText(text), queueMode, null, text);
    });
  }

  private float getSpeechRate(String text) {
    return text.hashCode() != lastUtteranceHash ? 1 : 0.1f;
  }

  private boolean isSingleChar(String text) {
    return !TextUtils.isEmpty(text) && text.trim().length() == 1;
  }

  /**
   * To force Google TTS pronounce "Z" as word instead of Zet, as letter name
   * Seems, not working for Russian language, sort it out later
   */
  private String correctSingleCharUtterance(String text) {
    return text + "-";
  }

  private String prepareText(String text) {
    return isSingleChar(text) ? correctSingleCharUtterance(text) : text;
  }

  public void stop() {
    ttsMap.entrySet().stream().filter(e -> e.getValue() != null).forEach(e -> e.getValue().stop());
  }

  public void shutdown() {
    ttsMap.entrySet()
        .stream()
        .filter(e -> e.getValue() != null)
        .forEach(e -> e.getValue().shutdown());
    ttsMap.clear();
    //    callback = null;
  }

  public void clearSpeakBatch() {
    speakBatch.clear();
  }
}