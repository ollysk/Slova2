package today.learnslovak.first.data.db.mapper;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.db.model.SnippetDb;

public class SnippetDbMapper {

  private final Moshi moshi;

  @Inject public SnippetDbMapper(Moshi moshi) {
    this.moshi = moshi;
  }

  public List<SnippetDb> toSnippetDb(String json) {
    List<SnippetDb> SnippetDbs = new ArrayList<>(500);
    try {
      SnippetDbs = fromJson(json);
    } catch (Exception e) {
      Timber.e(e);
    }
    return SnippetDbs;
  }

  private List<SnippetDb> fromJson(String json) throws Exception {
    Type typeOfT = Types.newParameterizedType(List.class, SnippetDb.class);
    JsonAdapter<List<SnippetDb>> adapter = moshi.adapter(typeOfT);
    return adapter.fromJson(json);
  }
}
