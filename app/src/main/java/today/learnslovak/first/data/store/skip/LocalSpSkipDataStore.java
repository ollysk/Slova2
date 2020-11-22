package today.learnslovak.first.data.store.skip;

import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import today.learnslovak.first.domain.model.Lang;

public class LocalSpSkipDataStore implements SkipDataStore {
  private final SharedPreferences prefs;
  private SharedPreferences.Editor editor;
  private boolean APPLY_MODE = true;

  @Inject public LocalSpSkipDataStore(SharedPreferences prefs) {
    this.prefs = prefs;
  }

  private void edit() {

    editor = prefs.edit();
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

  private void saveChanges() {
    if (APPLY_MODE) {
      editor.apply();
    } else {
      editor.commit();
    }
  }

  public void setCommitMode(Boolean setModeToCommit) {
    APPLY_MODE = !setModeToCommit;
  }
}
