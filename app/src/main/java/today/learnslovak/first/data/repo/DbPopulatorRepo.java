package today.learnslovak.first.data.repo;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.db.Db;
import today.learnslovak.first.data.db.mapper.WordDbMapper;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.store.data.LocalDataStore;
import today.learnslovak.first.data.store.data.RemoteDataStore;

public class DbPopulatorRepo {
  private final LocalDataStore localDataStore;
  private final RemoteDataStore remoteDataStore;
  private final Db db;
  private final WordDbMapper wordDbMapper;
  private final ScheduledThreadPoolExecutor executor;

  @Inject public DbPopulatorRepo(LocalDataStore localDataStore,
      RemoteDataStore remoteDataStore, Db db,
      WordDbMapper wordDbMapper, ScheduledThreadPoolExecutor executor) {
    this.localDataStore = localDataStore;
    this.remoteDataStore = remoteDataStore;
    this.db = db;
    this.wordDbMapper = wordDbMapper;
    this.executor = executor;
  }

  //@Override
  public List<WordDb> getLocalWordDbs() {
    return localDataStore.getWordDbs();
  }

  public List<WordDb> getRemoteWordDbs() {
    return remoteDataStore.getWordDbs();
  }

  //@Override
  public List<SnippetDb> getLocalSnippetDbs() {
    return localDataStore.getSnippetDbs();
  }

  public List<SnippetDb> getRemoteSnippetDbs() {
    return remoteDataStore.getSnippetDbs();
  }

  public List<WordDb> getWordDbsPatch(int patchLevel) {
    return remoteDataStore.getWordDbsPatch(patchLevel);
  }

  public List<SnippetDb> getSnippetDbsPatch(int patchLevel) {
    return remoteDataStore.getSnippetDbsPatch(patchLevel);
  }

  public void populateAllFromLocal() {
    populateWordTable(getLocalWordDbs());
    populateSnippetTable(getLocalSnippetDbs());
  }

  public CompletableFuture<Void> populateWordTable() {
    return populateWordTable(getRemoteWordDbs());
  }

  private CompletableFuture<Void> populateWordTable(List<WordDb> wordDbs) {
    CompletableFuture.runAsync(() -> db.wordDao().insertAll(wordDbs), executor);
    Runnable insertAllAscii =
        () -> db.wordDao().insertAllAscii(wordDbMapper.toWordDbAscii(wordDbs));
    return CompletableFuture.runAsync(insertAllAscii, executor);
  }

  public CompletableFuture<Void> populateSnippetTable() {
    List<SnippetDb> snippetDbs = getRemoteSnippetDbs();
    return populateSnippetTable(snippetDbs);
  }

  public CompletableFuture<Void> patchWordTable(int patchLevel) {
    return populateWordTable(getWordDbsPatch(patchLevel));
  }

  public CompletableFuture<Void> patchSnippetTable(int patchLevel) {
    return populateSnippetTable(getSnippetDbsPatch(patchLevel));
  }

  //todo think how to insert by chunks to not get OutOfMemoryError
  private CompletableFuture<Void> populateSnippetTable(List<SnippetDb> snippetDbs) {
    Runnable insertAllSnippets = () -> {
      try {
        db.snippetDao().insertAll(snippetDbs);
      } catch (Exception e) {
        Timber.e(e);
      }
    };
    return CompletableFuture.runAsync(insertAllSnippets, executor);
  }

  public CompletableFuture<Integer> getWordCount() {
    return CompletableFuture.supplyAsync(() -> db.wordDao().getWordsCount());
  }
}
