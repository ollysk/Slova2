package today.learnslovak.first.data.db.mapper;

import androidx.test.filters.SmallTest;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import today.learnslovak.first.data.db.model.SnippetDb;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
class SnippetDbMapperTest {

  @Test
  void toSnippetDb() {
    Gson gson = new Gson();
    SnippetDbMapper snippetDbMapper = new SnippetDbMapper(gson);
    List<SnippetDb> snippetDb =
        Collections.singletonList(SnippetDb.builder().id(1).en("en").ru("ru").build());
    String json = gson.toJson(snippetDb);
    List<SnippetDb> snippetDb2 = snippetDbMapper.toSnippetDb(json);
    assertThat(snippetDb).isEqualTo(snippetDb2);
  }
}