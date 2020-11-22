package today.learnslovak.first.data.repo;

import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import today.learnslovak.first.data.store.pref.PrefDataStore;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Preferences;
import today.learnslovak.first.domain.repo.PrefRepo;

public class PrefDataRepo implements PrefRepo {

  private final PrefDataStore pref;

  @Inject public PrefDataRepo(PrefDataStore pref) {
    this.pref = pref;
  }

  @Override public Map<String, ?> getAll() {
    return pref.getAll();
  }

  @Override public <T> T get(Preferences key, T defaultValue) {
    return pref.get(key, defaultValue);
  }

  @Override public <T> T getFromString(Preferences key, T defaultValue) {
    return pref.getFromString(key, defaultValue);
  }

  @Override public void put(Preferences key, Object value) {
    pref.put(key, value);
  }

  @Override public boolean contains(String key) {
    return pref.contains(key);
  }

  @Override public void remove(Preferences... keys) {
    pref.remove(keys);
  }

  @Override public Set<Integer> getSkipIds(Lang lang) {
    return pref.getSkipIds(lang);
  }

  @Override public void addSkipId(Lang lang, int id) {
    pref.addSkipId(lang, id);
  }

  public void clear() {
    pref.clear();
  }
}
