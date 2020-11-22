package today.learnslovak.first.data.db.mapper;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Snippet;

import static com.google.common.truth.Truth.assertThat;

class SnippetMapperTest {

  @Test
  void toSnippetDb() {
  }

  @Test
  void toSnippet() {
    List<Lang> availableLangs = Arrays.asList(Lang.EN, Lang.SK, Lang.RU, Lang.UK);
    SnippetMapper snippetMapper = new SnippetMapper();
    SnippetDb snippetDb = SnippetDb.builder().id(1).sk("sk").en("en").ru("ru").uk("uk").build();
    Snippet snippet = Snippet.builder()
        .id(1).source("en").sourceLang(Lang.EN)
        .trans("sk").transLang(Lang.SK)
        .transSecond("ru").transSecondLang(Lang.RU)
        .transThird("uk").build();
    Snippet snippet2 = snippetMapper.toSnippet(snippetDb, availableLangs);
    assertThat(snippet).isEqualTo(snippet2);
  }
}