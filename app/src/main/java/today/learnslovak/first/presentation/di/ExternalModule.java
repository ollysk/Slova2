package today.learnslovak.first.presentation.di;

import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class ExternalModule {

  @Provides public Gson provideGson() {
    return new Gson();
  }
}
