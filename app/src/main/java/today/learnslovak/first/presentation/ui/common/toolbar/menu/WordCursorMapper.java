package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import android.database.Cursor;
import android.database.MatrixCursor;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.util.List;
import lombok.experimental.UtilityClass;
import timber.log.Timber;
import today.learnslovak.first.domain.model.Word;

@UtilityClass
public class WordCursorMapper {
  private final Moshi moshi = new Moshi.Builder().build();
  private final JsonAdapter<Word> wordAdapter = moshi.adapter(Word.class);
  //_id column should be present for the CursorAdapter compatibility

  private final String[] columnNames = { "_id", "word" };

  public Cursor toCursor(List<Word> words) {

    //Used MatrixCursor adapter because searchView accepts CursorAdapter only.
    MatrixCursor cursor = new MatrixCursor(columnNames);
    words.forEach(word -> cursor.newRow().add("_id", word.getId()).add("word", wordAdapter.toJson(word)));
    return cursor;
  }

  public Word toWord(Cursor cursor) {

    //gson.toJsonTree(list.get(0)).getAsJsonObject().keySet().toArray(new String[0]);
    String json = cursorColumnToString(cursor, "word");
    try {
      return wordAdapter.fromJson(json);
    } catch (IOException e) {
      Timber.e(e);
      return Word.builder().build();
    }
  }

  private String cursorColumnToString(Cursor cursor, String columnName) {
    int index = cursor.getColumnIndex(columnName);
    return index >= 0 ? cursor.getString(index) : "";
  }
}
