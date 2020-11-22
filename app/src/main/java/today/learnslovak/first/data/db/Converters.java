package today.learnslovak.first.data.db;

import androidx.room.TypeConverter;
import today.learnslovak.first.domain.model.Lang;

public class Converters {
  @TypeConverter public static Lang fromPrefLang(String value) {
    return value == null ? null : Lang.valueOf(value);
  }

  @TypeConverter public static String PrefLangToString(Lang lang) {
    return lang == null ? "" : lang.toString();
  }
}
