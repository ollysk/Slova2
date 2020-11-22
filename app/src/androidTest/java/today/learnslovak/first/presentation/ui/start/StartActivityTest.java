package today.learnslovak.first.presentation.ui.start;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Test;
import today.learnslovak.first.presentation.ui.BaseTestActivity;

import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;

@LargeTest
public class StartActivityTest extends BaseTestActivity {

  @Before
  @Override public void launchActivity() {
    launchActivity(StartActivity.class);
  }

  @Test
  public void clickStartButton_shouldStartActivity() {
    for (int i = 0; i < 5; i++) {
      onWebView()
          .withElement(findElement(Locator.ID, i + "")) // similar to onView(withId(...))
          .perform(webClick());
      Espresso.pressBack();
      // .check(webMatches(getText(), containsString(query)));
    }
  }
}