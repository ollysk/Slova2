package today.learnslovak.first.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.model.SnippetDb;

@Dao public interface SnippetDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(SnippetDb snippetDb);

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insertAll(List<SnippetDb> snippetDbs);

  @Query("select * from " + DataConfig.TABLE_NAME_SNIPPET + " where id in(:ids) limit :limit")
  LiveData<List<SnippetDb>> findById(List<Integer> ids, int limit);

  @RawQuery(observedEntities = SnippetDb.class)
  LiveData<List<SnippetDb>> findByLang(SupportSQLiteQuery query);
}
