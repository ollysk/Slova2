package today.learnslovak.first.presentation.ui.wordext;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static today.learnslovak.first.presentation.util.Util.fabClick;
import static today.learnslovak.first.presentation.util.Util.inputSearch;
import static today.learnslovak.first.presentation.util.Util.menuFlagClick;
import static today.learnslovak.first.presentation.util.Util.menuFlagResetToInitState;
import static today.learnslovak.first.presentation.util.Util.menuStartClick;
import static today.learnslovak.first.presentation.util.Util.submitSearch;
import static today.learnslovak.first.presentation.util.Util.suggestionsEnSk;
import static today.learnslovak.first.presentation.util.Util.suggestionsSk;
import static today.learnslovak.first.presentation.util.Util.wordsSkEn;
import static today.learnslovak.first.presentation.util.matchers.SearchViewMatcherUtil.suggestionClickMatch;
import static today.learnslovak.first.presentation.util.matchers.TextViewMatcherUtil.matchTextViewIsDisplayed;
import static today.learnslovak.first.presentation.util.matchers.TextViewMatcherUtil.matchTextViewWith;
import static today.learnslovak.first.presentation.util.matchers.WebViewMatcherUtil.matchWebViewHeader;

//don't forget to disable all animations on emulator!
//AppCrawler cmd: java -jar crawl_launcher.jar --android-sdk /home/X/Android/Sdk --apk-file /home/X/Documents/AndroidStudioProjects/Slova/app/release/app-release.apk

@LargeTest
public class WordExtActivitySearchTest {

  @Before
  public void launchActivity() {
    ActivityScenario.launch(WordExtActivity.class);
  }

  @Test
  @DisplayName("WordId should be preserved between searches")
  //After search and click on suggestion, next dispayed word (after FAB click) should be word next to the word that was displayed before search,
  //not a word that is next to the word from search.
  //Heads up! This test should return correct results only with "HIDE_LEARNED" option disabled.
  public void searchClickOnSuggestion_checkWordOrderIsPreservedAfter() {
    searchClickOnSuggestionMatch(suggestionsSk, wordsSkEn);
  }

  @Test
  public void searchClickOnSuggestionEn_checkWordOrderIsPreservedAfter() {
    menuFlagClick();
    searchClickOnSuggestionMatch(suggestionsEnSk, wordsSkEn);
    menuFlagResetToInitState();
  }

  @Test
  public void searchSubmitCompleteWord_resultShouldMatch() {
    searchSubmitMatch(wordsSkEn);
  }

  /*      new String[][] { { "by", "byť" }, { "ktory", "ktorý" }, { "ktor", "ktorý" } });
    wordsSkEn = Arrays.asList(
        new String[][] {
      { "byť", "to be" }, { "v", "in" }, { "a", "and" }, { "na", "on the" }, { "sa", "self" },
      { "s", "with" }*/
  private void searchClickOnSuggestionMatch(List<String[]> suggestions, List<String[]> words) {
    suggestions.forEach((sugg) -> {
      String query = sugg[0];
      String suggestion = sugg[1];
      menuStartClick();
      matchTextViewWith(words.get(0)[1]);
      fabClick();
      inputSearch(query);
      suggestionClickMatch(suggestion);
      matchTextViewIsDisplayed();
      inputSearch(query);
      suggestionClickMatch(suggestion);
      fabClick();
      matchTextViewWith(words.get(2)[1]);
    });
  }

  private void searchSubmitMatch(List<String[]> words) {
    words.forEach(word -> {
      submitSearch(word[0]);
      matchWebViewHeader(word[0]);
      matchTextViewWith(word[1]);
    });
  }
}