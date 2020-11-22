package today.learnslovak.first.presentation.util.matchers;

import java.util.List;
import today.learnslovak.first.presentation.ui.words.Mode;

import static today.learnslovak.first.presentation.util.RecyclerViewUtil.isManualMode;
import static today.learnslovak.first.presentation.util.RecyclerViewUtil.setMode;
import static today.learnslovak.first.presentation.util.Util.fabClick;
import static today.learnslovak.first.presentation.util.Util.menuStartClick;
import static today.learnslovak.first.presentation.util.matchers.ViewMatcherUtil.matchViewIsDisplayedWith;
import static today.learnslovak.first.presentation.util.matchers.ViewMatcherUtil.matchViewIsNotDisplayedWith;

public final class RecyclerViewMatcherUtil {

  public static void clickFabInModeMatch(List<String[]> words, Mode mode) {
    setMode(mode);
    menuStartClick();
    words.forEach((word) ->
    {
      matchViewIsDisplayedWith(word[0]);
      matchViewIsNotDisplayedWith(word[1]);
      fabClick();
      matchViewIsDisplayedWith(word[1]);
      if (isManualMode(mode)) {
        fabClick();
      }
    });
  }
}
