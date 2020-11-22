package today.learnslovak.first.domain.repo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Preferences;

public interface PrefRepo {

  Map<String, ?> getAll();

  <T> T get(Preferences key, T defaultValue);

  /**
   * PreferenceFragment doesn't have an option to save ListPreference entryValues as integer-array.
   * This method is a workaround to this problem.
   * Use instead  of get() in situations when preference saved as a string instead of its real type
   * Use cautiously, only for values that definitely saved as strings
   **/
  <T> T getFromString(Preferences key, T defaultValue);

  void put(Preferences key, Object value);

  boolean contains(String key);

  void remove(Preferences... keys);

  Set<Integer> getSkipIds(Lang lang);

  void addSkipId(Lang lang, int id);

  /** Contract is to return list ordered from LANG0 to LANGX **/
  default List<Lang> getAvailableLangs() {
    return Stream.of(get(Pref.LANG0, Lang.SK), get(Pref.LANG1, Lang.EN), get(Pref.LANG2, Lang.RU),
        get(Pref.LANG3, Lang.UK)).collect(Collectors.toList());
  }
}
