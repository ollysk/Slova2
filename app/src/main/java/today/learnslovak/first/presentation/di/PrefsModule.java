package today.learnslovak.first.presentation.di;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ActivityComponent.class)
@Module
public class PrefsModule {

  @Provides SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
