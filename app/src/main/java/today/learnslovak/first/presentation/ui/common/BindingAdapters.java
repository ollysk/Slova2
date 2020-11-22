package today.learnslovak.first.presentation.ui.common;

import android.webkit.WebView;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

  @BindingAdapter({ "loadData" })
  public static void loadData(WebView view, String text) {
    view.loadData(text, "text/html; charset=utf-8", "utf-8");
  }
}
