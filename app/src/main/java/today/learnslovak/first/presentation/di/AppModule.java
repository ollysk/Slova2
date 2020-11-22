package today.learnslovak.first.presentation.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

  @Provides ScheduledThreadPoolExecutor provideScheduledThreadPoolExecutor() {
    return new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
  }
}
