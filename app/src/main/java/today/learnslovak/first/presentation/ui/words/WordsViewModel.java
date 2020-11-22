package today.learnslovak.first.presentation.ui.words;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import java.util.List;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.usecase.GetPrefs;
import today.learnslovak.first.domain.usecase.GetWords;
import today.learnslovak.first.presentation.ui.common.PrefForUi;

public class WordsViewModel extends ViewModel {

  private final GetWords getWords;
  private final RecyclerViewService rvService;
  private final GetPrefs getPrefs;

  private final MutableLiveData<Integer> clickPosition = new MutableLiveData<>(0);
  private final MutableLiveData<Integer> wordId = new MutableLiveData<>();

  @ViewModelInject
  public WordsViewModel(GetWords getWords, RecyclerViewService rvService, GetPrefs getPrefs) {
    this.getWords = getWords;
    this.rvService = rvService;
    this.getPrefs = getPrefs;
  }

  public void init() {
    // wordId = new MutableLiveData<>(getLastSavedWordId());
    setWordId(getLastSavedWordId());
    applySilenceDuration();
  }

  public void setWordId(int id) {
    wordId.setValue(id);
  }

  public void gotoStart() {
    rvService.clear();
    wordId.setValue(0);
    saveWordsId();
  }

  public LiveData<Integer> clickPosition() {
    return clickPosition;
  }

  public LiveData<List<Word>> words() {

    return Transformations.switchMap(wordId, getWords::findFrom);
  }

  void addWords(List<Word> words) {
    rvService.addWords(words);
  }

  public List<Word> getWords() {
    return rvService.getWords();
  }

  public void onFabClick() {
    //todo think. Code smell. Hidden temporal coupling.
    // Order of method calls is important. Ideally second method should get param that is return data from first method to not be able to use wrong methods order.
    setClickPosition(rvService.getNextPosition());
    loadWordsIfNeeded();
    rvService.onItemClickWithIncrement();
    saveWordsId();
  }

  public void setClickPosition(int position) {
    clickPosition.setValue(position);
    //todo think. This double init for different classes is kinda code smell. Think later.
    rvService.setClickPosition(position);
  }

  private void loadWordsIfNeeded() {
    if (rvService.isLoadWordsNeeded()) {
      loadWords();
    }
  }

  private void loadWords() {
    wordId.setValue(getLoadWordsStartId());
  }

  private int getLoadWordsStartId() {
    int startId = rvService.getLastPositionWordId();

    return startId == 0 ? getLastSavedWordId() : startId + 1;
  }

  private int getLastSavedWordId() {
    return getPrefs.getWordsStartId();
    //return 0;
  }

  void nullifyClickOnItemCount() {
    rvService.setClickOnItemCount(0);
  }

  public void setClickMode(Mode mode) {
    rvService.setClickMode(mode);
  }

  private void applySilenceDuration() {
    rvService.setSilenceDuration(getPrefs.getPrefForUi(PrefForUi.WORDS_PAUSE_DURATION, 0));
  }

  private void saveWordsId() {
    //getWords.saveLastWordId(rvService.getClickedWordId());
    getPrefs.saveWordsId(rvService.getClickWordId());
  }

  void onItemClick(int position) {
    rvService.onItemClick(position);
  }

  public Word getItem(int position) {
    return rvService.getItem(position);
  }

  public void setSkipFromWords() {
    getPrefs.setSkipFromWords();
  }

  public int getPrefFabSize() {
    return getPrefs.getPrefForUi(PrefForUi.FAB_SIZE, 56);
  }

  public Mode getPrefWordsMode() {
    return getPrefs.getPrefForUi(PrefForUi.WORDS_MODE, Mode.SILENT);
  }

  public void setPrefWordsMode(Mode mode) {
    getPrefs.setPrefForUi(PrefForUi.WORDS_MODE, mode);
  }
}
