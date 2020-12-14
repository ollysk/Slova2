package today.learnslovak.first.presentation.ui.start;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.R;
import today.learnslovak.first.databinding.ActivityStartBinding;
import today.learnslovak.first.presentation.common.AppLocale;
import today.learnslovak.first.presentation.common.tts.TtsCallback;
import today.learnslovak.first.presentation.ui.common.BaseActivity;

@AndroidEntryPoint
public class StartActivity extends BaseActivity {

  StartViewModel viewModel;
  ActivityStartBinding binding;
  @Inject AppLocale appLocale;
  @Inject HtmlStartProvider htmlStartProvider;

  @SuppressLint("SetJavaScriptEnabled")
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    htmlStartProvider.setCss(getCss());
    viewModel.setWebView(htmlStartProvider.getHtml());
    viewModel.init();
  }

  @Override public void setUpDataBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
  }

  @Override public void setUpToolbarWithHomeAsUp() {
    super.setUpToolbar();
  }

  @Override public void setUpViewModel() {
    super.setUpViewModel();

    viewModel = new ViewModelProvider(this).get(StartViewModel.class);
  }

  @Override public void setUpDataBindingVariables() {
    binding.setViewModel(viewModel);
    //without this line LiveData will not be updated
    binding.setLifecycleOwner(this);
    setUpWebView(binding.webView);
  }

  @Override public void ttsInit() {
    super.ttsInit();
    TtsCallback ttsCallback = new TtsCallback() {
    };
    baseViewModel.ttsSetCallback(ttsCallback);
  }

  @Override public void setSkipWordId() {

  }

  @Override public void onBackPressed() {

    super.onBackPressed();
    Timber.i("One more back press will quit the app. Are you sure?");
    //if sure super.onBackPressed();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    menu.removeItem(R.id.menu_remove_word);
    return true;
  }
}