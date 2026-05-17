package today.learnslovak.first.data.db;

import androidx.room.TypeConverter;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.domain.model.Lang;

public class Converters {

  private static final Moshi moshi = new Moshi.Builder().build();

  @TypeConverter public static Lang fromPrefLang(String value) {
    return value == null ? null : Lang.valueOf(value);
  }

  @TypeConverter public static String PrefLangToString(Lang lang) {
    return lang == null ? "" : lang.toString();
  }

  @TypeConverter
  public static String fromDeclensionItemList(List<WordDb.DeclensionItem> list) {
    if (list == null) return null;
    Type type = Types.newParameterizedType(List.class, WordDb.DeclensionItem.class);
    return moshi.adapter(type).toJson(list);
  }

  @TypeConverter
  public static List<WordDb.DeclensionItem> toDeclensionItemList(String value) {
    if (value == null) return null;
    Type type = Types.newParameterizedType(List.class, WordDb.DeclensionItem.class);
    try {
      return (List<WordDb.DeclensionItem>) moshi.adapter(type).fromJson(value);
    } catch (IOException e) {
      return null;
    }
  }

  @TypeConverter
  public static String fromConjugationItemList(List<WordDb.ConjugationItem> list) {
    if (list == null) return null;
    Type type = Types.newParameterizedType(List.class, WordDb.ConjugationItem.class);
    return moshi.adapter(type).toJson(list);
  }

  @TypeConverter
  public static List<WordDb.ConjugationItem> toConjugationItemList(String value) {
    if (value == null) return null;
    Type type = Types.newParameterizedType(List.class, WordDb.ConjugationItem.class);
    try {
      return (List<WordDb.ConjugationItem>) moshi.adapter(type).fromJson(value);
    } catch (IOException e) {
      return null;
    }
  }
}
