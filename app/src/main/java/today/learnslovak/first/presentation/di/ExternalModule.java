package today.learnslovak.first.presentation.di;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@InstallIn(ActivityComponent.class)
@Module
public class ExternalModule {

  @Provides public Gson provideGson() {
    return new Gson();
  }
}
