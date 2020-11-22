package today.learnslovak.first.presentation.util.matchers;

import androidx.test.espresso.matcher.RootMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public final class SearchViewMatcherUtil {

  public static void suggestionClickMatch(String suggestion) {
    onView(withText(suggestion))
        .inRoot(RootMatchers.isPlatformPopup())
        .perform(click());
  }
}
