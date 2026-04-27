package today.learnslovak.first.presentation.ui.start;

import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import today.learnslovak.first.domain.usecase.GetDb;

@HiltViewModel
public class StartViewModel extends ViewModel {

  private final GetDb getDb;
  private final MutableLiveData<String> wvLiveData = new MutableLiveData<>("<html><body>");

  @Inject public StartViewModel(GetDb getDb) {
    this.getDb = getDb;
  }

  public void setWebView(String html) {
    wvLiveData.setValue(html);
  }

  public LiveData<String> getWebViewData() {
    return wvLiveData;
  }

  public void init() {
    getDb.populateDbIfNeeded();
  }
}
