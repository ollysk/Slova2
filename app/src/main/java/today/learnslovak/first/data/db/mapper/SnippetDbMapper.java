package today.learnslovak.first.data.db.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.db.model.SnippetDb;

public class SnippetDbMapper {

  private final Gson gson;

  @Inject public SnippetDbMapper(Gson gson) {
    this.gson = gson;
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

  private List<SnippetDb> fromJson(String json) throws IllegalStateException {
    Type typeOfT = TypeToken.getParameterized(List.class, SnippetDb.class).getType();
    return gson.fromJson(json, typeOfT);
  }
}
