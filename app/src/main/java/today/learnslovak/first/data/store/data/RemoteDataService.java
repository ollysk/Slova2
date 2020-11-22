package today.learnslovak.first.data.store.data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;

public interface RemoteDataService {

  @GET("/{fileName}") Call<List<WordDb>> getWordDbs(@Path("fileName") String fileName);

  @GET("/{fileName}") Call<List<SnippetDb>> getSnippetDbs(@Path("fileName") String fileName);
}
