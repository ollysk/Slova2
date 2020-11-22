package today.learnslovak.first.presentation.ui.start;

import androidx.test.filters.LargeTest;
import org.junit.Before;
import today.learnslovak.first.presentation.ui.BaseTestActivity;

@LargeTest
public class StartActivitySearchTest extends BaseTestActivity {

  @Before
  @Override public void launchActivity() {
    launchActivity(StartActivity.class);
  }
}