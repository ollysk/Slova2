package today.learnslovak.first.domain.usecase;

import androidx.lifecycle.LiveData;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.repo.PrefRepo;
import today.learnslovak.first.domain.repo.WordRepo;

public class GetWords {

  private final WordRepo repo;
  private final PrefRepo pref;

  @Inject public GetWords(WordRepo repo, PrefRepo pref) {
    this.repo = repo;
    this.pref = pref;
  }

  public LiveData<Word> findOneFrom(final int id) {
    return repo.findOneFrom(id);
  }

  public LiveData<Word> findOneByExact(final int id) {
    return repo.findOneByExact(id);
  }

  public LiveData<List<Word>> findByFuzzy(String query) {
    return repo.findByFuzzy(query, pref.get(Pref.LANG_SEARCH, Lang.SK));
  }

  public LiveData<Word> findOneByExact(String query) {

    return repo.findOneByExact(query, pref.get(Pref.LANG_SEARCH, Lang.SK));
  }

  public LiveData<List<Word>> findFrom(int id) {

    return repo.findFrom(id);
  }

  public int getWordsLastId() {
    return pref.get(Pref.WORDS_LAST_ID, 0);
  }

  private void pause(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Timber.e(e);
    }
  }
}
