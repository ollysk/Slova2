package today.learnslovak.first.data.repo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.store.config.CachedConfigDataStore;
import today.learnslovak.first.data.store.config.RemoteConfigDataStore;
import today.learnslovak.first.domain.model.Config;
import today.learnslovak.first.domain.repo.ConfigRepo;

public class ConfigDataRepo implements ConfigRepo {

  private final CachedConfigDataStore cachedConfigDataStore;
  private final RemoteConfigDataStore remoteConfigDataStore;
  private final ScheduledThreadPoolExecutor executor;
  private Config config;

  @Inject public ConfigDataRepo(
      CachedConfigDataStore cachedConfigDataStore,
      RemoteConfigDataStore remoteConfigDataStore,
      ScheduledThreadPoolExecutor executor) {
    this.cachedConfigDataStore = cachedConfigDataStore;
    this.remoteConfigDataStore = remoteConfigDataStore;
    this.executor = executor;
    //to prevent multiple getAndCacheRemoteConfig() requests while cache is not completely inited (in init state)
    //important to invoke this class from background thread, to prevent main thread blocking by this method
    getConfigInstance();
  }

  private Config getConfigInstance() {
    return isConfigCached() ? cachedConfigDataStore.get() : getAndCacheRemoteConfig();
  }

  private boolean isConfigCached() {
    return !cachedConfigDataStore.isEmpty();
  }

  private Config getAndCacheRemoteConfig() {
    Timber.i("getAndCacheRemoteConfig");
    try {
      config = CompletableFuture.supplyAsync(remoteConfigDataStore::get).get();
      cachedConfigDataStore.put(config);
    } catch (ExecutionException | InterruptedException e) {
      Timber.e(e);
    }
    return config;
  }

  @Override public CompletableFuture<Config> getConfig() {
    return CompletableFuture.supplyAsync(this::getConfigInstance, executor);
  }
}
