package today.learnslovak.first.data.store.config;

import javax.inject.Inject;
import javax.inject.Singleton;
import today.learnslovak.first.domain.model.Config;

@Singleton
public class CachedConfigDataStore {

  private Config config;

  @Inject public CachedConfigDataStore() {
  }

  public void put(Config config) {
    this.config = config;
  }

  public Config get() {
    return config != null ? config : Config.builder().build();
  }

  public boolean isEmpty() {
    return config == null || config.getDataServerUrl() == null;
  }
}
