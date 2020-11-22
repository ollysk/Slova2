package today.learnslovak.first.data.store.pref;

import java.util.Map;
import java.util.Set;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Preferences;

public interface PrefDataStore {
  Map<String, ?> getAll();

  <T> T get(Preferences key, T defaultValue);

  <T> T getFromString(Preferences key, T defaultValue);

  Set<Integer> getSkipIds(Lang lang);

  void addSkipId(Lang lang, int id);

  void put(Preferences key, Object value);

  boolean contains(String key);

  void remove(Preferences... keys);

  void clear();
}
