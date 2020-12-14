package today.learnslovak.first.domain.repo;

import java.util.concurrent.CompletableFuture;
import today.learnslovak.first.domain.model.Config;

public interface ConfigRepo {

  CompletableFuture<Config> getConfig();
}
