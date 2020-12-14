package today.learnslovak.first.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import today.learnslovak.first.data.db.model.SkipDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.db.model.WordDbAscii;
import today.learnslovak.first.domain.model.Lang;

@Dao public interface WordDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(WordDb wordDb);

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(SkipDb skipDb);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAll(List<WordDb> wordDbs);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertAllAscii(List<WordDbAscii> wordDbAsciis);

  @RawQuery(observedEntities = WordDb.class)
  LiveData<WordDb> setSkipId(SupportSQLiteQuery query);

  @Query("select * from main where id>=:id and id not in(:skipIds) limit :limit")
  LiveData<List<WordDb>> findById(int id, Set<Integer> skipIds, int limit);

  @Query(
      "select * from main where id>=:id and id not in (select id from skip where id>=:id and lang=:lang) limit :limit")
  LiveData<List<WordDb>> findById(int id, Lang lang, int limit);

  @Query("select * from main where id>=:id limit :limit")
  LiveData<List<WordDb>> findById(int id, int limit);

  @RawQuery(observedEntities = WordDb.class)
  LiveData<List<WordDb>> findByLang(SupportSQLiteQuery query);

  @RawQuery(observedEntities = WordDb.class)
  LiveData<Optional<WordDb>> findOneByLang(SupportSQLiteQuery query);

  @Query("select max(id) from main")
  LiveData<Integer> findMaxWordId();

  @Query("select count(id) from main")
  int getWordsCount();
}
