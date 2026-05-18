package today.learnslovak.first.data.store.pref;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Preferences;

public class LocalPrefDataStore implements PrefDataStore {
  private final SharedPreferences prefs;
  private SharedPreferences.Editor editor;
  private boolean APPLY_MODE = true;

  @Inject public LocalPrefDataStore(SharedPreferences prefs) {
    this.prefs = prefs;
  }

  private void edit() {

    editor = prefs.edit();
  }

  @Override public Map<String, ?> getAll() {
    return prefs.getAll();
  }

/*    @Override
    public Object get(Key key, Object defaultValue) {

        try {
            if (defaultValue instanceof String) {
                return prefs.getString(key.name(), (String) defaultValue);
            } else if (defaultValue instanceof Set) {
                return prefs.getStringSet(key.name(), (Set<String>)(defaultValue));
            }else if (defaultValue instanceof Integer) {
                return prefs.getInt(key.name(), (Integer) defaultValue);
            } else if (defaultValue instanceof Boolean) {
                return prefs.getBoolean(key.name(), (Boolean) defaultValue);
            } else if (defaultValue instanceof Long) {
                return prefs.getLong(key.name(), (Long) defaultValue);
            } else if (defaultValue instanceof Float) {
                return prefs.getFloat(key.name(), (Float) defaultValue);
            } else if (defaultValue instanceof Double) {
                return Double.longBitsToDouble(prefs.getLong(key.name(), Double.doubleToLongBits((double) defaultValue)));
            }
        } catch (Exception e) {
            return defaultValue;
        }

        return defaultValue;
    }*/

  @SuppressWarnings("unchecked")
  @Override public <T> T get(Preferences key, T defaultValue) {
    if (defaultValue == null) return null;

    String keyName = key.name();
    try {
      return switch (defaultValue) {
        case String s -> (T) prefs.getString(keyName, s);
        case Integer i -> (T) (Integer) prefs.getInt(keyName, i);
        case Boolean b -> (T) (Boolean) prefs.getBoolean(keyName, b);
        case Long l -> (T) (Long) prefs.getLong(keyName, l);
        case Float f -> (T) (Float) prefs.getFloat(keyName, f);
        case Double d -> (T) (Double) Double.longBitsToDouble(
            prefs.getLong(keyName, Double.doubleToLongBits(d)));
        case Enum<?> e -> (T) parseEnum(e, prefs.getString(keyName, e.name()));
        case Set<?> s -> (T) prefs.getStringSet(keyName, (Set<String>) s);
        default -> defaultValue;
      };
    } catch (Exception e) {
      return defaultValue;
    }
  }

  @SuppressWarnings("unchecked")
  private static <E extends Enum<E>> E parseEnum(Enum<?> fallback, String stored) {
    try {
      return Enum.valueOf((Class<E>) fallback.getDeclaringClass(), stored);
    } catch (IllegalArgumentException e) {
      return (E) fallback;
    }
  }

  @SuppressWarnings("unchecked")
  @Override public <T> T getFromString(Preferences key, T defaultValue) {
    if (defaultValue == null) return null;

    String keyName = key.name();
    try {
      return switch (defaultValue) {
        case String s -> (T) prefs.getString(keyName, s);
        case Enum<?> e -> (T) parseEnum(e, prefs.getString(keyName, e.name()));
        case Integer i -> (T) Integer.valueOf(prefs.getString(keyName, String.valueOf(i)));
        case Boolean b -> (T) Boolean.valueOf(prefs.getString(keyName, String.valueOf(b)));
        case Long l -> (T) Long.valueOf(prefs.getString(keyName, String.valueOf(l)));
        case Float f -> (T) Float.valueOf(prefs.getString(keyName, String.valueOf(f)));
        case Double d -> (T) Double.valueOf(prefs.getString(keyName, String.valueOf(d)));
        default -> defaultValue;
      };
    } catch (Exception e) {
      return defaultValue;
    }
  }

  @Override public Set<Integer> getSkipIds(Lang lang) {
    String skipLang = prefs.getString(lang.toString(), "SK");//+"_SKIP_IDS";
    Set<String> skipIds = prefs.getStringSet(getLangSkipIdName(lang), new HashSet<>());
    return skipIds != null ? skipIds.stream().map(Integer::valueOf).collect(Collectors.toSet())
        : new HashSet<>();
  }

  private String getLangSkipIdName(Lang lang) {
    return prefs.getString(lang.toString(), "SK") + "_SKIP_IDS";
  }

  @Override public void addSkipId(Lang lang, int id) {

    Set<Integer> skipIds = getSkipIds(lang);
    skipIds.add(id);

    edit();
    //if(skipIds!=null)
    {
      editor.putStringSet(getLangSkipIdName(lang),
          skipIds.stream().map(String::valueOf).collect(Collectors.toSet()));
    }
    saveChanges();
  }

  @SuppressWarnings("unchecked")
  @Override public void put(Preferences key, Object value) {
    edit();
    switch (value) {
      case String s -> editor.putString(key.name(), s);
      case Enum<?> e -> editor.putString(key.name(), e.name());
      case Set<?> s -> editor.putStringSet(key.name(), (Set<String>) s);
      case Integer i -> editor.putInt(key.name(), i);
      case Boolean b -> editor.putBoolean(key.name(), b);
      case Long l -> editor.putLong(key.name(), l);
      case Float f -> editor.putFloat(key.name(), f);
      case Double d -> editor.putLong(key.name(), Double.doubleToRawLongBits(d));
      case null, default -> {}
    }
    saveChanges();
  }

  private void saveChanges() {
    if (APPLY_MODE) {
      editor.apply();
    } else {
      editor.commit();
    }
  }

  @Override public boolean contains(String key) {
    return prefs.contains(key);
  }

  @Override public void remove(Preferences... keys) {
    edit();
    for (Preferences key : keys) {
      editor.remove(key.name());
    }
    saveChanges();
  }

  public void clear() {
    edit();
    editor.clear();
    saveChanges();
  }

  public void setCommitMode(Boolean setModeToCommit) {
    APPLY_MODE = !setModeToCommit;
  }
}
