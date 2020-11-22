package today.learnslovak.first.presentation.util.matchers;

import androidx.test.espresso.web.webdriver.Locator;

import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static org.hamcrest.CoreMatchers.containsString;

public final class WebViewMatcherUtil {
  public static void matchWebViewHeader(String matchText) {
    onWebView()
        .withContextualElement(findElement(Locator.XPATH,
            "/html/body/*[self::h1 or self::h2 or self::h3]"))
        .check(webMatches(getText(), containsString(matchText)));
  }
}
