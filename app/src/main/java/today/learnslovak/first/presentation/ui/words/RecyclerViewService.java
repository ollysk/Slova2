package today.learnslovak.first.presentation.ui.words;

import android.speech.tts.TextToSpeech;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.presentation.common.tts.TtsService;

public class RecyclerViewService {

  private final TtsService ttsService;
  private final List<Word> words = new ArrayList<>();
  private final int PRELOAD_CHUNK_SIZE = 100;
  int clickPosition = 0;
  int clickOnItemCount = 0;
  //boolean isPlaying;
  Mode mode = Mode.SEMIAUTO;
  private int silenceDurationInMs = 0;

  @Inject public RecyclerViewService(TtsService ttsService) {
    this.ttsService = ttsService;
  }

  public List<Word> getWords() {
    return words;
  }

  boolean isLoadWordsNeeded() {
    return getLastPositionWordId() < getClickWordId() + PRELOAD_CHUNK_SIZE;
  }

  void addWords(List<Word> addWords) {
    if (isEligibleForAdd(addWords)) {
      //todo later mode - shuffle? decide what id to save in this mode
      //Collections.shuffle(words);
      this.words.addAll(addWords);
    }
  }

  private boolean isEligibleForAdd(List<Word> addWords) {
    return this.words.isEmpty() || getLastPositionWordIdFor(addWords) > getLastPositionWordId();
  }

  int getLastPositionWordId() {
    return getLastPositionWordIdFor(words);
  }

  private int getLastPositionWordIdFor(List<Word> words) {

    return words.size() > 0 ? words.get(words.size() - 1).getId() : 0;
  }

  int getClickWordId() {
    return isClickPositionWithinBounds() ? words.get(getClickPosition()).getId() : 0;
  }

  private boolean isClickPositionWithinBounds() {
    return !words.isEmpty() && words.size() > getClickPosition();
  }

  int getClickPosition() {
    return clickPosition;
  }

  public void setClickPosition(int position) {
    clickPosition = position;
  }

  public void clearClickPosition() {
    clickPosition = -1;
  }

  int getNextPosition() {
    return shouldStayInPosition() ? getClickPosition() : getClickPosition() + 1;
  }

  boolean shouldStayInPosition() {
    return clickOnItemCount <= 1 && mode == Mode.MANUAL && getClickPosition() >= 0
        || clickOnItemCount < 1 && getClickPosition() == 0;
  }

  int getClickOnItemCount() {
    int MAX_AVAILABLE_CLICK_POSITION = 1; //starting from 0
    return clickOnItemCount > MAX_AVAILABLE_CLICK_POSITION ? 0 : clickOnItemCount;
  }

  void setClickOnItemCount(int clickOnItemCount) {
    this.clickOnItemCount = clickOnItemCount;
  }

  public void setClickMode(Mode mode) {
    this.mode = mode;
  }

  void onItemClickWithIncrement() {
    onItemClick(getClickPosition());
    incrementClickOnItemCount();
  }

  void incrementClickOnItemCount() {
    clickOnItemCount = getClickOnItemCount() + 1;
  }

  void onItemClick(int position) {

    if (isSpeakNeeded()) {
      if (getClickOnItemCount() == 0 || mode != Mode.MANUAL) {
        speakSource(position);
      }
      if (mode != Mode.MANUAL) {
        playSilence();
      }
      if (getClickOnItemCount() == 1 || mode != Mode.MANUAL) {
        speakTrans(position);
      }
    }
  }

  boolean isSpeakNeeded() {
    return mode != Mode.SILENT && !words.isEmpty();
  }

  void speakSource(int position) {
    ttsService.speak(getItem(position).getSource(), TextToSpeech.QUEUE_FLUSH,
        getItem(position).getSourceLang());
  }

  void speakTrans(int position) {
    ttsService.speak(getItem(position).getTrans(), TextToSpeech.QUEUE_ADD,
        getItem(position).getTransLang());
  }

  void playSilence() {
    ttsService.playSilence(silenceDurationInMs, TextToSpeech.QUEUE_ADD);
  }

  public void setSilenceDuration(int durationInMs) {
    silenceDurationInMs = durationInMs;
  }

  public Word getItem(int position) {

    return getWords().size() > position && position >= 0 ? words.get(position)
        : Word.builder().build();
  }

  public void clear() {
    words.clear();
    clearClickPosition();
  }
}
