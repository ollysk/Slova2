package today.learnslovak.first.presentation.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import today.learnslovak.first.data.db.Db;
import today.learnslovak.first.data.db.DbPopulator;

@InstallIn(SingletonComponent.class)
@Module
public class DbModule {

  @Singleton
  @Provides public Db provideDb(DbPopulator dbPopulator) {
    return dbPopulator.providePrepopulatedDb();
  }
}
