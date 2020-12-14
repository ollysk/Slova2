package today.learnslovak.first.data.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.sqlite.db.SimpleSQLiteQuery;
import com.google.gson.Gson;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.Db;
import today.learnslovak.first.data.db.mapper.WordDbMapper;
import today.learnslovak.first.data.db.mapper.WordMapper;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.store.skip.SkipDataStore;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.repo.PrefRepo;
import today.learnslovak.first.domain.repo.WordRepo;

import static java.util.stream.Collectors.toList;

public class WordDataRepo implements WordRepo {

  private final Db db;
  private final WordMapper wordMapper;
  private final SkipDataStore skipDataStore;
  private final PrefRepo prefRepo;

  @Inject public WordDataRepo(Db db, WordMapper wordMapper, SkipDataStore skipDataStore,
      PrefRepo prefRepo) {
    this.db = db;
    this.wordMapper = wordMapper;
    this.skipDataStore = skipDataStore;
    this.prefRepo = prefRepo;
  }

  private void init(LinkedHashSet<Lang> langs) {
    String json = "";
    db.wordDao().insertAll(new WordDbMapper(new Gson()).toWordDb(json));
  }

  @Override public LiveData<Word> findOneFrom(int id) {
    return transformToOne(getSuitable(id, 1));
  }

  @Override public LiveData<Word> findOneByExact(int id) {
    return transformToOne(getInclLearned(id, 1));
  }

  @Override public LiveData<Word> findOneByExact(String query, Lang lang) {
    SimpleSQLiteQuery q = getSql(query, lang, 1);
    return transformToOne(searchBySql(q));
  }

  @Override public LiveData<List<Word>> findByFuzzy(String query, Lang lang) {
    String fuzzyExt = "*";
    SimpleSQLiteQuery q = getSql(query + fuzzyExt, lang, 5);
    Timber.i(q.getSql().toLowerCase());
    return transformToList(searchBySql(q));
  }

  @Override public LiveData<List<Word>> findFrom(int id) {
    int limit = prefRepo.getFromString(Pref.WORDS_LIMIT, 100);
    return transformToList(getSuitable(id, limit));
  }

  private LiveData<Word> transformToOne(LiveData<List<WordDb>> wordDbs) {
    return Transformations.map(wordDbs, wordDb -> wordDb.stream()
        .findFirst()
        .map(wordEntity1 -> wordMapper.toWord(wordEntity1, prefRepo.get(Pref.SHOW_TRANS, true)))
        .orElse(Word.builder().source("FAILED").build()));
  }

  private LiveData<List<WordDb>> searchBySql(SimpleSQLiteQuery query) {
    return db.wordDao().findByLang(query);
  }

  private LiveData<List<Word>> transformToList(LiveData<List<WordDb>> wordDbs) {
    return Transformations.map(wordDbs, wordDb -> wordDb.stream()
        .map(wordEntity1 -> wordMapper.toWord(wordEntity1, false))
        .collect(toList()));
  }

  private LiveData<List<WordDb>> getSuitable(int id, int limit) {
    return shouldHideLearned() ? getWithoutLearned(id, limit) : getInclLearned(id, limit);
  }

  private boolean shouldHideLearned() {
    return prefRepo.get(Pref.HIDE_LEARNED, true);
  }

  private LiveData<List<WordDb>> getWithoutLearned(int id, int limit) {
    Set<Integer> skipIds = skipDataStore.getSkipIds(Lang.SK);
    Lang lang = prefRepo.get(Pref.LANG0, Lang.SK);

    return skipIds.isEmpty() ? db.wordDao().findById(id, lang, limit)
        : db.wordDao().findById(id, skipIds, limit);
  }

  private LiveData<List<WordDb>> getInclLearned(int id, int limit) {
    return db.wordDao().findById(id, limit);
  }

  private SimpleSQLiteQuery getSql(String searchQuery, Lang lang, int limit) {
    String query = composeFtsQuery(DataConfig.TABLE_NAME_MAIN_FTS, searchQuery, lang, limit);

    if (shouldSearchInAscii(searchQuery, lang)) {
      query += " UNION " + composeFtsQuery(DataConfig.TABLE_NAME_MAIN_ASCII_FTS, searchQuery, lang,
          limit);
    }

    query = String.format(Locale.ENGLISH,
        "select * from %1$s where id in (%2$s) order by id asc limit %3$d",
        DataConfig.TABLE_NAME_MAIN, query, limit);

    return new SimpleSQLiteQuery(query);
  }

  private String composeFtsQuery(String tableName, String searchQuery, Lang lang, int limit) {
    return String.format(Locale.ENGLISH,
        "SELECT * FROM (select id from %1$s where %2$s match '%3$s' order by id asc limit %4$d)",
        tableName, lang, searchQuery, limit);
  }

  private boolean shouldSearchInAscii(String searchQuery, Lang lang) {
    return lang.equals(Lang.SK) && searchQuery.contains("*");
  }

  @Override public Set<Integer> getSkipIds(Lang lang) {
    return skipDataStore.getSkipIds(lang);
  }

  @Override public void addSkip(int id, Lang lang) {
    skipDataStore.addSkipId(lang, id);
  }

  @Override public LiveData<Integer> findMaxWordId() {
    return db.wordDao().findMaxWordId();
  }

}
