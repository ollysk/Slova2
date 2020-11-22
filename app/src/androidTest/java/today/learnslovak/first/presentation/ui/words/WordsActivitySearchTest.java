package today.learnslovak.first.presentation.ui.words;

import androidx.test.filters.LargeTest;
import org.junit.Before;
import today.learnslovak.first.presentation.ui.BaseTestActivity;

@LargeTest
public class WordsActivitySearchTest extends BaseTestActivity {

  @Before
  @Override public void launchActivity() {
    launchActivity(WordsActivity.class);
  }
}