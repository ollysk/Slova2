package today.learnslovak.first.presentation.ui.start;

import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import today.learnslovak.first.R;
import today.learnslovak.first.presentation.html.HtmlProvider;

public class HtmlStartProvider implements HtmlProvider {
  private final Context context;
  private List<String> startIntentNames;
  private List<String> startIntentTexts;
  private List<String> startIntentExtras;
  private String css;
  private StringBuilder res;

  @Inject public HtmlStartProvider(@ApplicationContext Context context) {
    this.context = context;
  }

  @Override public String getHtml() {
    populateHeader();
    buttonsDecorate();
    populateFooter();
    return res.toString();
  }

  private void populateHeader() {
    res = new StringBuilder(getHeader(css));
  }

  void buttonsDecorate() {
    loadFromResources();
    res.append("<div class='start-container'>");
    for (int i = 0; i < startIntentNames.size(); i++) {
      res.append("<span id=")
          .append(i)
          .append(" onClick=\"javascript:Android.launchActivity('")
          .append(getElementAt(i, startIntentNames))
          .append("','")
          .append(getElementAt(i, startIntentExtras))
          .append("');\">")
          .append(getElementAt(i, startIntentTexts))
          .append("</span>");
    }
  }

  private void loadFromResources() {
    startIntentNames = getFromResourcesArray(R.array.start_intent_names);
    startIntentTexts = getFromResourcesArray(R.array.start_intent_texts);
    startIntentExtras = getFromResourcesArray(R.array.start_intent_extras);
  }

  private List<String> getFromResourcesArray(int resourceId) {
    return Arrays.asList(context.getApplicationContext().getResources().getStringArray(resourceId));
  }

  private String getElementAt(int position, List<String> list) {
    return position < list.size() ? list.get(position) : "";
  }

  private void populateFooter() {
    res.append(getFooter());
  }

  @Override public void setCss(String css) {
    this.css = css;
  }
}
