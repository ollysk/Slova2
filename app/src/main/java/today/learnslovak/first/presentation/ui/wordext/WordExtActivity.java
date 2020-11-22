package today.learnslovak.first.presentation.ui.wordext;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import today.learnslovak.first.R;
import today.learnslovak.first.databinding.ActivityWordExtBinding;
import today.learnslovak.first.presentation.html.HtmlProviderFactory;
import today.learnslovak.first.presentation.ui.common.BaseActivity;
import today.learnslovak.first.presentation.ui.common.toolbar.menu.SlovaMenu;

@AndroidEntryPoint
public class WordExtActivity extends BaseActivity {

  public WordExtViewModel viewModel;
  private ActivityWordExtBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    extendScreenTimeout(binding.getRoot());
    applyTextZoom(binding.webView);
  }

  @Override public void setUpDataBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_word_ext);
  }

  @Override public void setUpViewModel() {
    super.setUpViewModel();
    viewModel = new ViewModelProvider(this).get(WordExtViewModel.class);
    viewModel.init();
    viewModel.setHtmlPresenter(HtmlProviderFactory.HtmlType.TTS, getCss());
  }

  @Override public void setUpDataBindingVariables() {
    binding.setViewModel(viewModel);
    //without this line LiveData will not be updated
    binding.setLifecycleOwner(this);
    setUpWebView(binding.webView);
    //setCustomFabSize(binding.fab1,200);
  }

  @Override public void setUpViewModelObservers() {
    super.setUpViewModelObservers();
    viewModel.getWordLive().observe(this, (word) -> viewModel.saveWordExtId());
  }

  @Override public void setSkipWordId() {
    viewModel.setSkipFromWordExt();
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    //super.onCreateOptionsMenu(menu);
    slovaMenu = SlovaMenu.builder()
        .baseActivity(this)
        .actions(baseViewModel)
        .search(viewModel)
        .countryFlag(countryFlag)
        .build();

    //slovaMenu.initRemoveWordItem(menu);
    return slovaMenu.onCreateOptionsMenu(menu);
  }

  //menu
  @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    int id = item.getItemId();
    if (id == R.id.menu_start_again) {
      viewModel.gotoStart();
    }
    return super.onOptionsItemSelected(item);
  }
}