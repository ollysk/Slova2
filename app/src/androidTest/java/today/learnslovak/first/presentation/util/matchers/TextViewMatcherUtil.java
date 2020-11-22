package today.learnslovak.first.presentation.util.matchers;

import today.learnslovak.first.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public final class TextViewMatcherUtil {

  public static void matchTextViewWith(String matchText) {
    onView(withId(R.id.textView1)).check(matches(withText(matchText)));
  }

  public static void matchTextViewIsDisplayed() {
    onView(withId(R.id.textView1)).check(matches(isDisplayed()));
  }
}
