package today.learnslovak.first.data.store.skip;

import androidx.sqlite.db.SimpleSQLiteQuery;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.db.Db;
import today.learnslovak.first.data.db.model.SkipDb;
import today.learnslovak.first.domain.model.Lang;

public class LocalDbSkipDataStore implements SkipDataStore {
  private final Db db;
  private final ScheduledThreadPoolExecutor executor;

  @Inject public LocalDbSkipDataStore(Db db, ScheduledThreadPoolExecutor executor) {
    this.db = db;
    this.executor = executor;
  }

  @Override public Set<Integer> getSkipIds(Lang lang) {
    // return skipDataStore.getSkipIds(prefLang);
    return Collections.emptySet();
  }

  @Override public void addSkipId(Lang lang, int id) {

    SimpleSQLiteQuery q = new SimpleSQLiteQuery("update main set skStatus = 6 where id = 14");

    // dbMain.wordDao().setSkipId(q);
    Timber.i(q.getSql());
    SkipDb skipDb = SkipDb.builder().id(id).lang(lang).build();
    executor.execute(() -> db.wordDao().insert(skipDb));
  }

  private String getLangSkipIdName(Lang lang) {
    return lang.toString().toLowerCase() + "Status";
  }
}
