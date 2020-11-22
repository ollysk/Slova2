package today.learnslovak.first.presentation.ui;

import android.app.Activity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Test;

import static today.learnslovak.first.presentation.util.Util.searchClickOnSuggestion;
import static today.learnslovak.first.presentation.util.Util.suggestionsSk;

@LargeTest
public abstract class BaseTestActivity {

  public <A extends Activity> void launchActivity(Class<A> activityClass) {
    ActivityScenario.launch(activityClass);
  }

  @Before
  public abstract void launchActivity();

  @Test
  //@DisplayName("")
  public void searchClickOnSuggestion_shouldBeOk() {
    searchClickOnSuggestion(suggestionsSk);
  }
}
