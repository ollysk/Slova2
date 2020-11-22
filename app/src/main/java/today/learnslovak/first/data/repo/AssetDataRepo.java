package today.learnslovak.first.data.repo;

import javax.inject.Inject;
import today.learnslovak.first.data.store.asset.AssetDataStore;
import today.learnslovak.first.domain.repo.AssetRepo;

public class AssetDataRepo implements AssetRepo {

  private final AssetDataStore dataStore;

  @Inject public AssetDataRepo(AssetDataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override public String get(String fileName) {
    return dataStore.get(fileName);
  }
}
