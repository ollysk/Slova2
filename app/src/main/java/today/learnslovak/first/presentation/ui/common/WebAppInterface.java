package today.learnslovak.first.presentation.ui.common;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.usecase.GetQuiz;
import today.learnslovak.first.presentation.common.tts.TtsService;

//todo don't forget to add in proguard # -keep class today.learnslovak.first.** { *; }
//-keepclassmembers class today.learnslovak.first.WebAppInterface {
//   public *;
//}

public class WebAppInterface {
  private final TtsService ttsService;
  private final GetQuiz getQuiz;
  private final Context context;

  @Inject public WebAppInterface(TtsService ttsService, GetQuiz getQuiz,
      @ApplicationContext Context context) {
    this.ttsService = ttsService;
    this.getQuiz = getQuiz;
    this.context = context;
  }

  @JavascriptInterface public void speak(String lang, String text, int queueMode) {
    ttsService.speak(text, queueMode, Lang.valueOf(lang));
  }

  @JavascriptInterface public void speakBatch() {
    ttsService.speakBatch();
  }

  @JavascriptInterface public void launchActivity(String activityName, String activityExtra) {

    String packageName = "today.learnslovak.first.presentation.ui";
    try {
      activityName = packageName + "." + activityName;
      Class<?> activityClass = Class.forName(activityName).asSubclass(AppCompatActivity.class);
      Intent intent = new Intent(context, activityClass);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      if (!activityExtra.isEmpty()) {
        // TODO: change to enum?
        intent.putExtra("type", activityExtra);
      }
      context.startActivity(intent);
    } catch (Exception e) {
      //Toast.makeText(context, "invalid activity name: " + activityName, Toast.LENGTH_SHORT).show();
      Timber.e(e);
    }

    // Intent intent = new Intent(mContext,activityName);
    //mContext.startActivity(intent);
  }

  //fixme? possible clean architecture integrity violation. Use case is used directly, without viewmodel layer
  //but viewmodel not only unnecessary, but also isn't usable in non-UI class
  //think about it later

  @JavascriptInterface public int getScore() {
    return getQuiz.getScore();
  }

  @JavascriptInterface public void scoreAdd(int score, String word) {

    getQuiz.scoreAdd(score, word);
  }
}
