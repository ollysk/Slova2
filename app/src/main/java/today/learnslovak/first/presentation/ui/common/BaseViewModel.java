package today.learnslovak.first.presentation.ui.common;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import java.util.List;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.usecase.GetPrefs;
import today.learnslovak.first.domain.usecase.GetWords;
import today.learnslovak.first.presentation.common.tts.TtsCallback;
import today.learnslovak.first.presentation.common.tts.TtsService;
import today.learnslovak.first.presentation.ui.common.toolbar.menu.MenuActions;
import today.learnslovak.first.presentation.ui.common.toolbar.menu.MenuSearch;

public class BaseViewModel extends ViewModel implements MenuActions, MenuSearch {

  private final GetWords getWords;
  private final GetPrefs getPrefs;
  private final TtsService ttsService;
  private final MutableLiveData<String> suggestionsQuery = new MutableLiveData<>();

  @ViewModelInject
  public BaseViewModel(GetWords getWords, GetPrefs getPrefs, TtsService ttsService) {
    this.getWords = getWords;
    this.getPrefs = getPrefs;
    this.ttsService = ttsService;
  }

  public int getPrefScreenTimeout() {
    int SCREEN_OFF_TIMEOUT = 900000;//hardcoded 15 minutes, it's ok until it will be not ok
    return getPrefs.getPrefForUi(PrefForUi.KEEP_SCREEN_ON, false) ? SCREEN_OFF_TIMEOUT : 0;
  }

  public int getPrefFontSize() {
    return getPrefs.getPrefForUi(PrefForUi.FONT_SIZE, 100);
  }

  @Override public LiveData<List<Word>> suggestions() {
    return Transformations.switchMap(suggestionsQuery, getWords::findByFuzzy);
  }

  @Override public void setSuggestionsQuery(String query) {

    suggestionsQuery.setValue(query);
  }

  @Override public Lang getSearchLang() {
    return getPrefs.getSearchLang();
  }

  @Override public void setSearchLang(Lang lang) {
    getPrefs.setSearchLang(lang);
  }

  @Override public void setSearchQuery(String query) {

  }

  @Override public void setTempWordId(int id) {

  }

  //todo refactor later. If multiple langs not installed, multiple Popups/notifications will be showed.
  //Should aggregate them in one.
  public void ttsInit() {
    //getPrefs.getActiveLangs(), new TtsCallback() {}
    getPrefs.getActiveLangs().forEach(ttsService::init);
  }

  public void ttsStop() {
    ttsService.stop();
  }

  public void ttsShutdown() {
    ttsService.shutdown();
  }

  public void ttsSetCallback(TtsCallback callback) {
    ttsService.setCallback(callback);
  }
}
