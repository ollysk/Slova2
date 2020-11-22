package today.learnslovak.first.data.store.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;

public class RemoteDataStore implements DataStore {

  private final Retrofit retrofit = new Retrofit.Builder().baseUrl(DataConfig.SERVER_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
  private final RemoteDataService service = retrofit.create(RemoteDataService.class);

  @Inject public RemoteDataStore() {
  }

  @Override public List<WordDb> getWordDbs() {
    Call<List<WordDb>> call = service.getWordDbs(WORD_JSON_FILENAME);
    return execute(call);
  }

  @Override public List<SnippetDb> getSnippetDbs() {
    Call<List<SnippetDb>> call = service.getSnippetDbs(SNIPPET_JSON_FILENAME);
    return execute(call);
  }

  private <T> List<T> execute(Call<List<T>> call) {
    List<T> result = new ArrayList<>();
    try {
      result = call.execute().body();
    } catch (IOException e) {
      Timber.e(e);
    }
    return result;
  }
}
