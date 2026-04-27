package today.learnslovak.first.presentation.di;

import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class ExternalModule {

  @Provides public Moshi provideMoshi() {
    return new Moshi.Builder().build();
  }
}
