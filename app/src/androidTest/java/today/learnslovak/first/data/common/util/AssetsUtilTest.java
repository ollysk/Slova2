package today.learnslovak.first.data.common.util;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import today.learnslovak.first.data.store.data.DataStore;

import static com.google.common.truth.Truth.assertThat;

public class AssetsUtilTest {
  static Context context;
  static AssetsUtil assetsUtil;

  @BeforeAll static void setUp() {
    context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assetsUtil = new AssetsUtil(context);
  }

  @Test
  void getFromAssetsEscaped() {
    assertThat(getFromAssets(DataStore.WORD_JSON_FILENAME)).doesNotContain("#");
    assertThat(getFromAssets(DataStore.SNIPPET_JSON_FILENAME)).doesNotContain("#");
  }

  @Test
  void getFromAssets() {
    assertThat(getFromAssets(DataStore.WORD_JSON_FILENAME)).isNotEmpty();
    assertThat(getFromAssets(DataStore.SNIPPET_JSON_FILENAME)).isNotEmpty();
  }

  private String getFromAssets(String fileName) {
    String ASSETS_JSON_PATH = "json/";
    return assetsUtil.getFromAssets(ASSETS_JSON_PATH + fileName);
  }
}