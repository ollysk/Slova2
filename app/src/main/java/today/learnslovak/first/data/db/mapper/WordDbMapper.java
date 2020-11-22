package today.learnslovak.first.data.db.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

  private final Gson gson;

  @Inject public WordDbMapper(Gson gson) {
    this.gson = gson;
  }

  public List<WordDb> toWordDb(String json) {
    List<WordDb> wordDbs = new ArrayList<>();
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

  private List<WordDb> fromJson(String json) throws IllegalStateException {
    Type typeOfT = TypeToken.getParameterized(List.class, WordDb.class).getType();
    return gson.fromJson(json, typeOfT);
  }
}
