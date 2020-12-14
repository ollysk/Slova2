package today.learnslovak.first.presentation.di;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.Db;

@InstallIn(SingletonComponent.class)
@Module
public class DbModule {
  @Singleton
  @Provides public Db provideDb(@ApplicationContext Context context) {
    return Room.databaseBuilder(context, Db.class, DataConfig.DB_NAME)
        //.allowMainThreadQueries()
        //.addCallback(getCallback())
        .fallbackToDestructiveMigration().build();
  }
}
