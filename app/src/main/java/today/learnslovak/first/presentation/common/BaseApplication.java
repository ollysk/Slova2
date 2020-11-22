package today.learnslovak.first.presentation.common;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;
import today.learnslovak.first.BuildConfig;

@HiltAndroidApp
public class BaseApplication extends Application {
  public BaseApplication() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}
