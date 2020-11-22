package today.learnslovak.first.presentation.ui.start;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import today.learnslovak.first.domain.usecase.GetDb;

public class StartViewModel extends ViewModel {

  private final GetDb getDb;
  private final MutableLiveData<String> wvLiveData = new MutableLiveData<>("<html><body>");

  @ViewModelInject public StartViewModel(GetDb getDb) {
    this.getDb = getDb;
  }

  public void setWebView(String html) {
    wvLiveData.setValue(html);
  }

  public LiveData<String> getWebViewData() {
    return wvLiveData;
  }

  public LiveData<Integer> findDbMaxWordId() {
    return getDb.findMaxWordId();
  }

/*  public void populate() {
    getWords.populate();
  }*/
}
