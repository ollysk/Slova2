package today.learnslovak.first.data.repo;

import java.util.List;
import javax.inject.Inject;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.store.data.LocalDataStore;
import today.learnslovak.first.data.store.data.RemoteDataStore;

public class DbPopulatorRepo {
  private final LocalDataStore localDataStore;
  private final RemoteDataStore remoteDataStore;

  @Inject public DbPopulatorRepo(LocalDataStore localDataStore, RemoteDataStore remoteDataStore) {
    this.localDataStore = localDataStore;
    this.remoteDataStore = remoteDataStore;
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
}
