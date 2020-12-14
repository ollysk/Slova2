package today.learnslovak.first.data.store.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.domain.model.Config;
import today.learnslovak.first.domain.repo.ConfigRepo;

public class RemoteDataStore implements DataStore {

  private final ConfigRepo configRepo;
  RemoteDataService service;

  @Inject public RemoteDataStore(ConfigRepo configRepo) {
    this.configRepo = configRepo;
  }

  RemoteDataService getServiceInstance() {
    return service == null ? new Retrofit.Builder().baseUrl(getDataServerUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RemoteDataService.class) : service;
  }

  String getDataServerUrl() {
    return getConfig().getDataServerUrl();
  }

  private Config getConfig() {
    try {
      return configRepo.getConfig().get();
    } catch (ExecutionException | InterruptedException e) {
      Timber.e(e);
    }
    return Config.builder().build();
  }

  @Override public List<WordDb> getWordDbs() {
    Call<List<WordDb>> call = getServiceInstance().getWordDbs(getConfig().getWordJsonFilename());
    return execute(call);
  }

  @Override public List<SnippetDb> getSnippetDbs() {
    Call<List<SnippetDb>> call =
        getServiceInstance().getSnippetDbs(getConfig().getSnippetJsonFilename());
    return execute(call);
  }

  @Override public List<WordDb> getWordDbsPatch(int patchLevel) {
    Call<List<WordDb>> call =
        getServiceInstance().getWordDbs(getPath(patchLevel, getConfig().getWordJsonFilename()));
    return execute(call);
  }

  @Override public List<SnippetDb> getSnippetDbsPatch(int patchLevel) {
    Call<List<SnippetDb>> call = getServiceInstance().getSnippetDbs(
        getPath(patchLevel, getConfig().getSnippetJsonFilename()));
    return execute(call);
  }

  private String getPath(int patchLevel, String filePath) {
    return DataConfig.SERVER_PATCH_DIR + patchLevel + "-" + filePath;
  }

  private <T> List<T> execute(Call<List<T>> call) {
    List<T> result = new ArrayList<>();
    try {
      result = call.execute().body();
    } catch (IOException e) {
      Timber.e(e);
    }
    return result != null ? result : Collections.emptyList();
  }
}
