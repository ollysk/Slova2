package today.learnslovak.first.data.db.mapper;

import androidx.test.filters.SmallTest;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import today.learnslovak.first.data.db.model.SnippetDb;

import static com.google.common.truth.Truth.assertThat;

@SmallTest
class SnippetDbMapperTest {

  @Test
  void toSnippetDb() throws Exception {
    Moshi moshi = new Moshi.Builder().build();
    SnippetDbMapper snippetDbMapper = new SnippetDbMapper(moshi);
    List<SnippetDb> snippetDb =
        Collections.singletonList(SnippetDb.builder().id(1).en("en").ru("ru").build());

    Type typeOfT = Types.newParameterizedType(List.class, SnippetDb.class);
    JsonAdapter<List<SnippetDb>> adapter = moshi.adapter(typeOfT);
    String json = adapter.toJson(snippetDb);

    List<SnippetDb> snippetDb2 = snippetDbMapper.toSnippetDb(json);
    assertThat(snippetDb).isEqualTo(snippetDb2);
  }
}