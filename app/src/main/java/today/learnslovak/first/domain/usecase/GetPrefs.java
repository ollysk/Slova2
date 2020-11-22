package today.learnslovak.first.domain.usecase;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Preferences;
import today.learnslovak.first.domain.repo.PrefRepo;
import today.learnslovak.first.domain.repo.WordRepo;

public class GetPrefs {
  private final PrefRepo prefRepo;
  private final WordRepo wordRepo;

  @Inject public GetPrefs(PrefRepo prefRepo, WordRepo wordRepo) {
    this.prefRepo = prefRepo;
    this.wordRepo = wordRepo;
  }

  public void saveWordExtId(int id) {
    prefRepo.put(Pref.WORD_EXT_LAST_ID, id);
  }

  public void saveWordsId(int id) {
    prefRepo.put(Pref.WORDS_LAST_ID, id);
  }

  public int getWordsStartId() {
    return prefRepo.get(Pref.WORDS_LAST_ID, 0);
  }

  public int getWordExtStartId() {
    return prefRepo.get(Pref.WORD_EXT_LAST_ID, 0);
  }

  public void setSkipFromWordExt(int id) {
    wordRepo.addSkip(id, prefRepo.get(Pref.LANG0, Lang.SK));
  }

  public void setSkipFromWordExt() {

    wordRepo.addSkip(getWordExtStartId(), prefRepo.get(Pref.LANG0, Lang.SK));
  }

  public Lang getSearchLang() {
    return prefRepo.get(Pref.LANG_SEARCH, Lang.SK);
  }

  public void setSearchLang(Lang lang) {

    prefRepo.put(Pref.LANG_SEARCH, lang.toString());
  }

  /**
   * Returns a list of Lang that is currently used.
   * Implementation presumes that getAvailableLangs() returns a list ordered from LANG0 to LANGX.
   **/
  public List<Lang> getActiveLangs() {
    int limit = prefRepo.get(Pref.SHOW_LANG2, false) ? 3 : 2;
    Timber.d("activeLangs %s", getActiveLangs(limit));
    return getActiveLangs(limit);
  }

  private List<Lang> getActiveLangs(int limit) {
    return prefRepo.getAvailableLangs().stream().limit(limit).collect(Collectors.toList());
  }

  public void setSkipFromWords() {
    wordRepo.addSkip(getWordsStartId(), prefRepo.get(Pref.LANG0, Lang.SK));
  }

  public <T> T getPrefForUi(Preferences key, T defaultValue) {
    return prefRepo.get(key, defaultValue);
  }

  public void setPrefForUi(Preferences key, Object defaultValue) {
    prefRepo.put(key, defaultValue);
  }
}