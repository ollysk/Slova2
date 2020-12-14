package today.learnslovak.first.data.store.config;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.store.data.RemoteDataService;
import today.learnslovak.first.domain.model.Config;

@Singleton
public class RemoteConfigDataStore {

  final RemoteDataService remoteDataService = new Retrofit.Builder().baseUrl(DataConfig.SERVER_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build().create(RemoteDataService.class);

  @Inject public RemoteConfigDataStore() {
  }

  public Config get() {
    Call<Config> call = getRemoteDataService().getConfig(DataConfig.SERVER_ANNOUNCE_FILENAME);
    Config config = null;
    try {
      config = call.execute().body();
    } catch (IOException e) {
      Timber.e(e);
    }
    return config != null ? config : Config.builder().build();
  }

  public RemoteDataService getRemoteDataService() {
    return remoteDataService;
  }
}
