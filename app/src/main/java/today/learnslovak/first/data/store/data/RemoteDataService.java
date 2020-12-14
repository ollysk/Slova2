package today.learnslovak.first.data.store.data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.domain.model.Config;

public interface RemoteDataService {

  @GET("/{filePath}") Call<List<WordDb>> getWordDbs(
      @Path(value = "filePath", encoded = true) String filePath);

  @GET("/{filePath}") Call<List<SnippetDb>> getSnippetDbs(
      @Path(value = "filePath", encoded = true) String filePath);

  @GET("/{filePath}") Call<Config> getConfig(
      @Path(value = "filePath", encoded = true) String filePath);
}
