package today.learnslovak.first.presentation.ui.wordext;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import today.learnslovak.first.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static today.learnslovak.first.presentation.util.Util.menuStartClick;
import static today.learnslovak.first.presentation.util.matchers.TextViewMatcherUtil.matchTextViewWith;

@RunWith(Parameterized.class)

@LargeTest
public class WordExtActivitySkipTest {

  @Parameterized.Parameter(0)
  public String query;
  @Parameterized.Parameter(1)
  public String suggestion;
  List<String> words = Arrays.asList("byť", "v", "a", "na", "sa", "s");
  List<String> wordsTrans = Arrays.asList("to be", "in", "and", "on the", "self", "with");

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return Arrays.asList(
        new Object[][] { { "by", "byť" } });
  }

  @Before
  public void launchActivity() throws InterruptedException {
    {
      ActivityScenario.launch(WordExtActivity.class);
      //Thread.sleep(5000);
    }
  }

  @Test
  public void clickOnSkipIcon_shouldGoToNextWord() {
    menuStartClick();
    matchTextViewWith(wordsTrans.get(0));
    for (int i = 1; i < 6; i++) {
      skipIconClick(wordsTrans.get(i));
    }
  }

  public void skipIconClick(String matchText) {
    onView(withId(R.id.menu_remove_word)).perform(click());
    matchTextViewWith(matchText);
  }

  @After
  public void packageClear() throws IOException {
    InstrumentationRegistry.getInstrumentation()
        .getUiAutomation()
        .executeShellCommand("pm clear today.learnslovak.first")
        .close();
  }
}