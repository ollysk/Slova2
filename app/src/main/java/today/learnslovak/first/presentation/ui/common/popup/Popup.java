package today.learnslovak.first.presentation.ui.common.popup;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.PopupWindow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import today.learnslovak.first.R;

public class Popup {

  public void show(final View view, String text) {

    LayoutInflater inflater =
        (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View popupView = inflater.inflate(R.layout.activity_popup, null);
    popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    int width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * .8);
    int height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * .8);

    boolean focusable = true;

    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

    // popupWindow.getHeight()
    popupWindow.setWidth((int) (popupWindow.getWidth() * .8));

    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    WebView webView = popupView.findViewById(R.id.webView);
    webView.loadData(text, "text/html; charset=utf-8", "utf-8");

    FloatingActionButton fab1 = popupView.findViewById(R.id.fab1);
    fab1.setOnClickListener(v -> popupWindow.dismiss());
  }
}
