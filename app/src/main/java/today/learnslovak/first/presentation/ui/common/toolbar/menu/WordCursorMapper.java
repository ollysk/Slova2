package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import android.database.Cursor;
import android.database.MatrixCursor;
import com.google.gson.Gson;
import java.util.List;
import lombok.experimental.UtilityClass;
import today.learnslovak.first.domain.model.Word;

@UtilityClass
public class WordCursorMapper {
  private final Gson gson = new Gson();
  //_id column should be present for the CursorAdapter compatibility

  private final String[] columnNames = { "_id", "word" };

  public Cursor toCursor(List<Word> words) {

    //Used MatrixCursor adapter because searchView accepts CursorAdapter only.
    MatrixCursor cursor = new MatrixCursor(columnNames);
    words.forEach(word -> cursor.newRow().add("_id", word.getId()).add("word", gson.toJson(word)));
    return cursor;
  }

  public Word toWord(Cursor cursor) {

    //gson.toJsonTree(list.get(0)).getAsJsonObject().keySet().toArray(new String[0]);
    String json = cursorColumnToString(cursor, "word");
    return gson.fromJson(json, Word.class);
  }

  private String cursorColumnToString(Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndex(columnName));
  }
}
