package today.learnslovak.first.presentation.util.matchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public final class ViewMatcherUtil {
  public static void matchViewIsDisplayedWith(String matchText) {
    onView(withText(matchText)).check(matches(isDisplayed()));
  }

  public static void matchViewIsNotDisplayedWith(String matchText) {
    onView(withText(matchText)).check(matches(not(isDisplayed())));
  }
}
