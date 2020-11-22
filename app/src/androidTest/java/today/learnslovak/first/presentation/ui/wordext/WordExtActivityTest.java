package today.learnslovak.first.presentation.ui.wordext;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import today.learnslovak.first.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(Parameterized.class)

@LargeTest
public class WordExtActivityTest {

  @Parameterized.Parameter(0)
  public String query;
  @Parameterized.Parameter(1)
  public String suggestion;

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return Arrays.asList(
        new String[][] { { "by", "byť" }, { "ktory", "ktorý" }, { "ktor", "ktorý" } });
  }

  @Before
  public void launchActivity() {
    ActivityScenario.launch(WordExtActivity.class);
  }

  @Test
  public void clickOnFab() {
    for (int i = 0; i < 5; i++) {
      onView(withId(R.id.fab1)).perform(click());
      onView(withId(R.id.textView1)).check(matches(isDisplayed()));
    }
  }
}