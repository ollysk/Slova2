package today.learnslovak.first.presentation.ui.common;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;

public class ScreenService {

  private static final int DISABLE_KEEP_SCREEN_ON = 0;
  private final Handler handler = new Handler();
  private final Context context;
  private View view;

  @Inject public ScreenService(@ApplicationContext Context context) {
    this.context = context;
  }

  private void removeCallbacks() {
    handler.removeCallbacksAndMessages(null);
  }

  public void disableExtendedScreenTimeout() {
    if (view != null) {
      view.setKeepScreenOn(false);
    }
    removeCallbacks();
  }

  public void extendScreenTimeout(View view, int timeout) {
    this.view = view;

    removeCallbacks();

    int systemScreenTimeout =
        Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 0);
    int totalDelay = timeout - systemScreenTimeout;

    //totalDelay = 3000;
    if (totalDelay > 0) {
      this.view.setKeepScreenOn(true);
      Runnable r = this::disableExtendedScreenTimeout;
      handler.postDelayed(r, timeout);
    }
  }

  public void setWebViewTextZoom(WebView webView, int textZoom) {
    if (webView != null && webView.getSettings().getTextZoom() != textZoom) {
      webView.getSettings().setTextZoom(textZoom);
    }
  }

  public void setCustomFabSize(FloatingActionButton fab, int width) {
    if (fab != null && width > 56) {
      ConstraintLayout.LayoutParams layoutParams =
          (ConstraintLayout.LayoutParams) fab.getLayoutParams();
      layoutParams.width = width;
      fab.setCustomSize(width);
    }
  }
}
