package today.learnslovak.first.presentation.uiautomator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;
import org.junit.Before;
import org.junit.Test;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.google.common.truth.Truth.assertThat;

public class BehaviorTest {
  private static final String BASIC_SAMPLE_PACKAGE
      = "today.learnslovak.first";

  private static final int LAUNCH_TIMEOUT = 5000;

  private static final String STRING_TO_BE_TYPED = "UiAutomator";

  private UiDevice device;

  @Before
  public void startMainActivityFromHomeScreen() {
    device = UiDevice.getInstance(getInstrumentation());

    device.pressHome();

    final String launcherPackage = getLauncherPackageName();
    assertThat(launcherPackage).isNotEmpty();
    device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    Context context = getApplicationContext();
    final Intent intent = context.getPackageManager()
        .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);

    device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
  }

  @Test
  public void checkPreconditions() {
    assertThat(device).isNotNull();
  }

  private String getLauncherPackageName() {
    final Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_HOME);
    PackageManager pm = getApplicationContext().getPackageManager();
    ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
    return resolveInfo.activityInfo.packageName;
  }
}
