package today.learnslovak.first.data.db.mapper;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.db.model.WordDbAscii;

public class WordDbMapper {

  private final Moshi moshi;

  @Inject public WordDbMapper(Moshi moshi) {
    this.moshi = moshi;
  }

  public List<WordDb> toWordDb(String json) {
    List<WordDb> wordDbs = new ArrayList<>(500);
    try {
      wordDbs = fromJson(json);
    } catch (Exception e) {
      Timber.e(e);
    }
    return wordDbs;
  }

  public List<WordDbAscii> toWordDbAscii(List<WordDb> wordDbs) {
    return wordDbs.stream()
        .filter(wordDb -> hasDiacritics(wordDb.getSk()))
        .map(wordDb -> WordDbAscii.builder()
            .id(wordDb.getId())
            .sk(stripDiacritics(wordDb.getSk()))
            .build())
        .collect(Collectors.toList());
  }

  private boolean hasDiacritics(String text) {
    return !Normalizer.isNormalized(text, Normalizer.Form.NFD);
  }

  private String stripDiacritics(String text) {
    return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
  }

  private List<WordDb> fromJson(String json) throws Exception {
    Type typeOfT = Types.newParameterizedType(List.class, WordDb.class);
    JsonAdapter<List<WordDb>> adapter = moshi.adapter(typeOfT);
    return adapter.fromJson(json);
  }
}
