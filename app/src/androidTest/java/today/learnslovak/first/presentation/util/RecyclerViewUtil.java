package today.learnslovak.first.presentation.util;

import java.util.HashMap;
import java.util.Map;
import today.learnslovak.first.R;
import today.learnslovak.first.presentation.ui.words.Mode;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static today.learnslovak.first.presentation.util.Util.menuStartClick;

public final class RecyclerViewUtil {

  public static Map<Mode, String> modeMap = new HashMap<>();

  public static void initModeMap() {
    //["Semi-auto", "Manual", "Auto", "Silent"]
    String[] modeTitles = getResourcesArray(R.array.pref_words_mode_titles);
    String[] modes = getResourcesArray(R.array.pref_words_mode);
    for (int i = 0; i < modes.length; i++) {
      modeMap.put(Mode.valueOf(modes[i]), modeTitles[i]);
    }
  }

  public static String[] getResourcesArray(int resourceId) {
    return getApplicationContext().getResources().getStringArray(resourceId);
  }

  public static void setMode(Mode mode) {
    menuStartClick();
    onView(withId(R.id.words_mode)).perform(click());
    onData(allOf(is(instanceOf(String.class)), is(modeMap.get(mode)))).perform(click());
  }

  public static boolean isManualMode(Mode mode) {
    return mode == Mode.MANUAL;
  }

  public static void recyclerViewItemClick(int itemPosition) {
    onView(withId(R.id.rvWords))
        .perform(actionOnItemAtPosition(itemPosition, click()));
  }
}
