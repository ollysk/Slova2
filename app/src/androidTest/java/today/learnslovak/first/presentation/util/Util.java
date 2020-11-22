package today.learnslovak.first.presentation.util;

import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import java.util.Arrays;
import java.util.List;
import today.learnslovak.first.R;
import today.learnslovak.first.presentation.ui.settings.SettingsActivity;
import today.learnslovak.first.presentation.util.matchers.TextViewMatcherUtil;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static today.learnslovak.first.presentation.util.matchers.SearchViewMatcherUtil.suggestionClickMatch;

//@UtilityClass
public final class Util {

  private static final int LANG_COUNT = 4;
  public static List<String[]> wordsSkEn = Arrays.asList(
      new String[][] {
          { "byť", "to be" }, { "v", "in" }, { "a", "and" }, { "na", "on the" }, { "sa", "self" },
          { "s", "with" }
      });
  public static List<String[]> suggestionsSk = Arrays.asList(
      new String[][] { { "by", "byť" }, { "ktory", "ktorý" }, { "ktor", "ktorý" } });
  public static List<String[]> suggestionsEnSk = Arrays.asList(
      new String[][] { { "to b", "byť" }, { "whi", "ktorý" }, { "which", "ktorý" } });
  static int flagIndex = 0;

  public static void menuStartClick() {
    openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
    onView(withText(R.string.start_again)).perform(click());
  }

  public static void menuSkipClickMatch(String matchText) {
    menuSkipClick();
    TextViewMatcherUtil.matchTextViewWith(matchText);
  }

  public static void menuSkipClick() {
    onView(withId(R.id.menu_remove_word)).perform(click());
  }

  public static void menuFlagClick() {
    onView(withId(R.id.menu_flag)).perform(click());
    flagIndex++;
  }

  /**
   * Back to the language flag that was before any @menuFlagClick()
   */
  public static void menuFlagResetToInitState() {
    while (flagIndex < LANG_COUNT) {
      onView(withId(R.id.menu_flag)).perform(click());
      flagIndex++;
    }
  }

  public static void fabClick() {
    onView(withId(R.id.fab1)).perform(click());
  }

  public static void inputSearch(String query) {
    onView(withId(R.id.app_bar_search)).perform(click());
    onView(isAssignableFrom(AutoCompleteTextView.class))
        .perform(click(), typeText(query));
    Espresso.closeSoftKeyboard();
  }

  //replaceText instead of typeText here is workaraound to be able to submit queries with diacritics
  public static void submitSearch(String query) {
    onView(withId(R.id.app_bar_search)).perform(click());
    onView(isAssignableFrom(AutoCompleteTextView.class))
        .perform(click(), replaceText(query))
        .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        .perform(clearText());
    Espresso.closeSoftKeyboard();
  }

  public static void searchClickOnSuggestion(List<String[]> suggestions) {
    suggestions.forEach((sugg) -> {
      String query = sugg[0];
      String suggestion = sugg[1];
      menuStartClick();
      inputSearch(query);
      suggestionClickMatch(suggestion);
      //suggestionClickMatch(query, suggestion);
    });
  }

  public static void toggleHideLearnedPreference() throws InterruptedException {
    ActivityScenario<SettingsActivity> activityScenario =
        ActivityScenario.launch(SettingsActivity.class);
    Thread.sleep(1000);
    onView(withId(R.id.recycler_view))
        .perform(actionOnItem(hasDescendant(withText(R.string.cb_hide_learned_words_t)), click()));
    Thread.sleep(1000);
    activityScenario.close();
  }
}
