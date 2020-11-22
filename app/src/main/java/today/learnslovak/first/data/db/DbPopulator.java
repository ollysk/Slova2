package today.learnslovak.first.data.db;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.mapper.WordDbMapper;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.repo.DbPopulatorRepo;

//todo make auto update json data on every start (download pathches and apply them in room)
//for correct patching save DBVersion(max(wordDbId)), to not redownload big files, only diffs
//also, think about gradually download data. if wordId>1000 - download one more chunk/thousand (but only for sentences, to prevent big space allocations for the app )
public class DbPopulator {

  private final DbPopulatorRepo dbPopulatorRepo;
  private final ScheduledThreadPoolExecutor executor;
  private final WordDbMapper wordDbMapper;
  private final Application application;
  private Db db;

  @Inject public DbPopulator(DbPopulatorRepo dbPopulatorRepo, ScheduledThreadPoolExecutor executor,
      WordDbMapper wordDbMapper, Application application) {
    this.dbPopulatorRepo = dbPopulatorRepo;
    this.executor = executor;
    this.wordDbMapper = wordDbMapper;
    this.application = application;
  }

  public Db provideDb() {
    db = getBuilder().fallbackToDestructiveMigration()
        //.allowMainThreadQueries()
        .build();
    return db;
  }

  public Db providePrepopulatedDb() {
    db = getBuilder().addCallback(getCallback()).fallbackToDestructiveMigration().build();
    return db;
  }

  private RoomDatabase.Builder<Db> getBuilder() {
    return Room.databaseBuilder(application, Db.class, DataConfig.DB_NAME);
  }

  private RoomDatabase.Callback getCallback() {
    return new RoomDatabase.Callback() {
      @Override public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        executor.submit(() -> populateWordTable());
        executor.submit(() -> populateSnippetTable());
        Timber.i("RoomDb populate");
      }
    };
  }

/*  private void polulateDb() {
    //fixme remove this sleep before release
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Timber.e(e);
    }
    populateWordTable();
    populateSnippetTable();
  }*/

  public void populateWordTable() {
    //todo idea is that local json is small (~10 words + snippets) and user will see app functionality right away, while full json is loading in background
    populateWordTable(dbPopulatorRepo.getLocalWordDbs());
    populateWordTable(dbPopulatorRepo.getRemoteWordDbs());
  }

  private void populateWordTable(List<WordDb> wordDbs) {
    db.wordDao().insertAll(wordDbs);
    db.wordDao().insertAllAscii(wordDbMapper.toWordDbAscii(wordDbs));
  }

  public void populateSnippetTable() {
    populateSnippetTable(dbPopulatorRepo.getLocalSnippetDbs());
    populateSnippetTable(dbPopulatorRepo.getRemoteSnippetDbs());
  }

  //todo think how to insert by chunks to not get OutOfMemoryError
  private void populateSnippetTable(List<SnippetDb> snippetDbs) {
    db.snippetDao().insertAll(snippetDbs);
  }
}
