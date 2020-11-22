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
    return defaultValue instanceof String ? (T) prefs.getString(key.name(), (String) defaultValue)
        : defaultValue instanceof Enum ? (T) Enum.valueOf(
            ((Enum<?>) defaultValue).getDeclaringClass(),
            prefs.getString(key.name().toUpperCase(), defaultValue.toString()))
            : defaultValue instanceof Set ? (T) prefs.getStringSet(key.name(),
                (Set<String>) (defaultValue))
                : defaultValue instanceof Integer ? (T) (Integer) prefs.getInt(key.name(),
                    (Integer) defaultValue)
                    : defaultValue instanceof Boolean ? (T) (Boolean) prefs.getBoolean(key.name(),
                        (Boolean) defaultValue)
                        : defaultValue instanceof Long ? (T) (Long) prefs.getLong(key.name(),
                            (Long) defaultValue)
                            : defaultValue instanceof Float ? (T) (Float) prefs.getFloat(key.name(),
                                (Float) defaultValue) : defaultValue instanceof Double
                                ? (T) (Double) Double.longBitsToDouble(prefs.getLong(key.name(),
                                Double.doubleToLongBits((Double) defaultValue))) : defaultValue;
  }

  @SuppressWarnings("unchecked")
  @Override public <T> T getFromString(Preferences key, T defaultValue) {
    return defaultValue instanceof String ? (T) prefs.getString(key.name(), (String) defaultValue)
        : defaultValue instanceof Enum<?> ? (T) Enum.valueOf(
            ((Enum<?>) defaultValue).getDeclaringClass(),
            prefs.getString(key.name().toUpperCase(), defaultValue.toString()))
            : defaultValue instanceof Integer ? (T) Integer.valueOf(
                getString(key, defaultValue))
                : defaultValue instanceof Boolean ? (T) Boolean.valueOf(
                    getString(key, defaultValue))
                    : defaultValue instanceof Long ? (T) Long.valueOf(
                        getString(key, defaultValue))
                        : defaultValue instanceof Float ? (T) Float.valueOf(
                            getString(key, defaultValue)) : defaultValue;
  }

  private <T> String getString(Preferences key, T defaultValue) {
    return prefs.getString(key.name(), defaultValue.toString());
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

  @Override public void put(Preferences key, Object value) {

    edit();
    if (value instanceof String) {
      editor.putString(key.name(), (String) value);
    }
    if (value instanceof Enum) {
      editor.putString(key.name(), ((Enum<?>) value).name());
    } else if (value instanceof Set) {
      editor.putStringSet(key.name(), (Set<String>) value);
    } else if (value instanceof Integer) {
      editor.putInt(key.name(), (Integer) value);
    } else if (value instanceof Boolean) {
      editor.putBoolean(key.name(), (Boolean) value);
    } else if (value instanceof Long) {
      editor.putLong(key.name(), (Long) value);
    } else if (value instanceof Float) {
      editor.putFloat(key.name(), (Float) value);
    } else if (value instanceof Double) {
      editor.putLong(key.name(), Double.doubleToRawLongBits((double) value));
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
