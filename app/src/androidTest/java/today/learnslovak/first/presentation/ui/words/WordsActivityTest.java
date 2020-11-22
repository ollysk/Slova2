package today.learnslovak.first.presentation.ui.words;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Before;
import org.junit.Test;
import today.learnslovak.first.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static today.learnslovak.first.presentation.util.RecyclerViewUtil.initModeMap;
import static today.learnslovak.first.presentation.util.RecyclerViewUtil.recyclerViewItemClick;
import static today.learnslovak.first.presentation.util.Util.fabClick;
import static today.learnslovak.first.presentation.util.Util.menuStartClick;
import static today.learnslovak.first.presentation.util.Util.wordsSkEn;
import static today.learnslovak.first.presentation.util.matchers.RecyclerViewMatcherUtil.clickFabInModeMatch;
import static today.learnslovak.first.presentation.util.matchers.ViewMatcherUtil.matchViewIsDisplayedWith;

@LargeTest
public class WordsActivityTest {

  @Before
  public void launchActivity() {
    ActivityScenario.launch(WordsActivity.class);
    initModeMap();
  }

  @Test
  public void clickFabInAllModes_transShouldBeDisplayedAfterClick() {
    Arrays.stream(Mode.values()).forEach((mode) -> clickFabInModeMatch(wordsSkEn, mode));
  }

  @Test
  public void clickRandomItems_shouldBeOk() {
    menuStartClick();
    for (int i = 0; i < 20; i++) {
      int position = new Random().nextInt(10);
      onView(withId(R.id.rvWords))
          .perform(actionOnItemAtPosition(position, click()));
    }
  }

  @Test
  public void clickFab_shouldBeOk() {
    menuStartClick();
    for (int i = 0; i < 20; i++) {
      fabClick();
    }
  }

  @Test
  public void clickOnItem_transShouldBeDisplayed() {
    AtomicInteger i = new AtomicInteger();
    menuStartClick();
    wordsSkEn.forEach((word) -> {
      recyclerViewItemClick(i.get());
      matchViewIsDisplayedWith(word[1]);
      i.getAndIncrement();
    });
  }
}