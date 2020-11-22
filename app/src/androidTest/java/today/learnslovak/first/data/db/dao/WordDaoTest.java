package today.learnslovak.first.data.db.dao;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import today.learnslovak.first.InstantExecutorExtension;
import today.learnslovak.first.data.db.Db;
import today.learnslovak.first.data.db.model.WordDb;

import static com.google.common.truth.Truth.assertThat;
import static today.learnslovak.first.LiveDataTestUtil.getLiveDataValue;

@ExtendWith(InstantExecutorExtension.class)
@LargeTest
class WordDaoTest {

  private static WordDao wordDao;
  private static Db db;
  private static WordDb wordDb;

  @BeforeAll static void setUp() {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, Db.class).allowMainThreadQueries().build();
    wordDao = db.wordDao();
    wordDb = WordDb.builder().id(5).sk("ktorý").en("which").ru("который").uk("який").build();
    wordDao.insert(wordDb);
  }

  @AfterAll static void tearDown() {
    db.close();
  }

  @Test
  void insert() {
  }

  @Test
  void testInsert() {
  }

  @Test
  void insertAll() {
  }

  @Test
  void insertAllAscii() {
  }

  @Test
  void setSkipId() {
  }

  @Test
  void findById() {
    List<WordDb> wordDbs = getLiveDataValue(wordDao.findById(5, 10));
    assertThat(Collections.singletonList(wordDb)).isEqualTo(wordDbs);
  }

  @Test
  void testFindById() {
  }

  @Test
  void testFindById1() {
  }

  @Test
  void findByLang() {
  }

  @Test
  void findOneByLang() {
  }

  @Test
  void findMaxWordId() {
    int id = getLiveDataValue(wordDao.findMaxWordId());
    assertThat(wordDb.getId()).isEqualTo(id);
  }
}