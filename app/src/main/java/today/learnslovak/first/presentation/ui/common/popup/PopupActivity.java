package today.learnslovak.first.presentation.ui.common.popup;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import today.learnslovak.first.BuildConfig;
import today.learnslovak.first.R;
import today.learnslovak.first.databinding.ActivityPopupBinding;

public class PopupActivity extends AppCompatActivity implements View.OnClickListener {

  ActivityPopupBinding binding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setWindowAttributes();
    binding = DataBindingUtil.setContentView(this, R.layout.activity_popup);
    binding.setLifecycleOwner(this);
/*        binding.webView.addJavascriptInterface(webAppInterface, "Android");
        binding.webView.getSettings().setJavaScriptEnabled(true);*/
    binding.fab1.setOnClickListener(this);
    binding.fab1.setAlpha(0.75f);

    String activityType = getIntent().getStringExtra("type");

    if ("HELP".equalsIgnoreCase(activityType)) {

      showHelp();
      //finish();
    }

    if ("TTS".equalsIgnoreCase(activityType)) {

      showTts();
      //finish();
    }
  }

  void setWindowAttributes() {
    Window window = getWindow();
    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    window.setLayout((int) (width * .8), (int) (height * .8));
    WindowManager.LayoutParams windowAttributes = window.getAttributes();
    windowAttributes.dimAmount = 0.50f;
    windowAttributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    window.setAttributes(windowAttributes);
  }

  public void showTts() {
    showPopup("install tts lang first");
  }

  public void showHelp() {
    // FIXME:  appserverurl change to correct
    String APP_SERVER_URL = "http://localhost";
    String referrer = this.getLocalClassName();
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    String html;
    String footer = "<hr>Ver." + versionName;
    footer = footer
        + "<p style='font-size:60%'><a href="
        + APP_SERVER_URL
        + "/policies/privacy-policy.html>Privacy policy </a></p>";

    switch (referrer) {
            /*
            case "Stats":
                // html = "Stats help placeholder";
                break;
                */
      default:
        html = (getString(R.string.help_info));
        html = html + footer;
    }
    showPopup(html);

    //finish();
  }

  void showPopup(String data) {
    binding.webView.loadData(data + "", "text/html; charset=utf-8", "utf-8");
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.fab1) {
      finish();
    }
  }
}