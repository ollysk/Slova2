package today.learnslovak.first.data.store.pref;

import java.util.Map;
import java.util.Set;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Preferences;

public class RemotePrefDataStore implements PrefDataStore {
  @Override public Map<String, ?> getAll() {
    return null;
  }

  @Override public <T> T get(Preferences key, T defaultValue) {
    return null;
  }

  @Override public <T> T getFromString(Preferences key, T defaultValue) {
    return null;
  }

  @Override public Set<Integer> getSkipIds(Lang lang) {
    return null;
  }

  @Override public void addSkipId(Lang lang, int id) {

  }

  @Override public void put(Preferences key, Object value) {

  }

  @Override public boolean contains(String key) {
    return false;
  }

  @Override public void remove(Preferences... keys) {

  }

  @Override public void clear() {

  }
}
