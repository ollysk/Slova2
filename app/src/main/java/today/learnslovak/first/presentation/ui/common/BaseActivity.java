package today.learnslovak.first.presentation.ui.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;
import today.learnslovak.first.R;
import today.learnslovak.first.data.common.util.AssetsUtil;
import today.learnslovak.first.presentation.common.AppLocale;
import today.learnslovak.first.presentation.ui.common.popup.PopupActivity;
import today.learnslovak.first.presentation.ui.common.toolbar.SlovaToolbar;
import today.learnslovak.first.presentation.ui.common.toolbar.menu.CountryFlag;
import today.learnslovak.first.presentation.ui.common.toolbar.menu.SlovaMenu;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {

  public SlovaMenu slovaMenu;
  public BaseViewModel baseViewModel;
  @Inject public CountryFlag countryFlag;
  @Inject AppLocale appLocale;
  @Inject ScreenService screenService;
  @Inject WebAppInterface webAppInterface;
  @Inject AssetsUtil assetsUtil;
  private View view;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_base);
    setLocale();

    setUpDataBinding();
    setUpViewModel();
    setUpDataBindingVariables();
    setUpViewModelObservers();
    setUpToolbarWithHomeAsUp();
    ttsInit();
  }

  public abstract void setUpDataBinding();

  public abstract void setUpDataBindingVariables();

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onResume() {
    super.onResume();
    extendScreenTimeout();
  }

  @Override protected void onPause() {
    super.onPause();
    disableExtendedScreenTimeout();
  }

  @Override public void onUserInteraction() {
    super.onUserInteraction();
    extendScreenTimeout();
  }

  @Override protected void onStop() {
    super.onStop();
    disableExtendedScreenTimeout();
    ttsStop();
  }

  @Override protected void onDestroy() {
    ttsShutdown();
    super.onDestroy();
  }

  public void setLocale() {
    //appLocale.setLocale();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    //slovaMenu = new SlovaMenu(this, baseViewModel, baseViewModel, countryFlag);
    slovaMenu = SlovaMenu.builder()
        .baseActivity(this)
        .actions(baseViewModel)
        .search(baseViewModel)
        .countryFlag(countryFlag)
        .build();
    return slovaMenu.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    super.onOptionsItemSelected(item);
    int id = item.getItemId();
    if (id == R.id.menu_help) {
      //todo refactor. type change from string to enum, + add ability for context help (content depends on referer/intentExtra)
      Intent intent = new Intent(this, PopupActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("type", "HELP");
      startActivity(intent);
    }
    return slovaMenu.onOptionsItemSelected(item);
  }

  public void setUpViewModel() {
    baseViewModel = new ViewModelProvider(this).get(BaseViewModel.class);
  }

  public void setUpViewModelObservers() {

  }

  public void setUpToolbar() {
    getToolbar().init();
  }

  public void setUpToolbarWithHomeAsUp() {
    getToolbar().initWithHomeAsUp();
  }

  private SlovaToolbar getToolbar() {
    return SlovaToolbar.builder().toolbar(findViewById(R.id.toolbar)).baseActivity(this).build();
  }

  public String getCss() {
    return assetsUtil.getFromAssetsEscaped("css/base.css");
  }

  public abstract void setSkipWordId();

  public ScreenService getScreenService() {
    return screenService;
  }

  public void extendScreenTimeout(View view) {
    this.view = view;
    extendScreenTimeout();
  }

  private void extendScreenTimeout() {
    if (shouldExtendScreenTimeout()) {
      getScreenService().extendScreenTimeout(view, baseViewModel.getPrefScreenTimeout());
    }
  }

  private boolean shouldExtendScreenTimeout() {
    return view != null && baseViewModel.getPrefScreenTimeout() != 0;
  }

  public void disableExtendedScreenTimeout() {
    screenService.disableExtendedScreenTimeout();
  }

  public void applyTextZoom(WebView webView) {
    screenService.setWebViewTextZoom(webView, baseViewModel.getPrefFontSize());
  }

  @SuppressLint("SetJavaScriptEnabled")
  public void setUpWebView(WebView webView) {
    if (webView != null) {
      webView.addJavascriptInterface(webAppInterface, "Android");
      webView.getSettings().setJavaScriptEnabled(true);
      //webview remote debug
      //chrome://inspect/#devices
      WebView.setWebContentsDebuggingEnabled(true);
      //webSettings.setJavaScriptEnabled(true);
      //webView.setBackgroundColor(Color.parseColor("#919191"));
    }
  }

  public void setCustomFabSize(FloatingActionButton fab, int width) {
    screenService.setCustomFabSize(fab, width);
  }

  public void ttsInit() {
    baseViewModel.ttsInit();
  }

  public void ttsStop() {
    baseViewModel.ttsStop();
  }

  public void ttsShutdown() {
    baseViewModel.ttsShutdown();
  }
}