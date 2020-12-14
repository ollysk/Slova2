package today.learnslovak.first.domain.repo;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.Set;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Word;

public interface WordRepo {

  LiveData<Word> findOneFrom(int id);

  LiveData<Word> findOneByExact(int id);

  LiveData<Word> findOneByExact(String query, Lang lang);

  LiveData<List<Word>> findFrom(int id);

  LiveData<List<Word>> findByFuzzy(String query, Lang lang);

  Set<Integer> getSkipIds(Lang lang);

  void addSkip(int id, Lang lang);

  LiveData<Integer> findMaxWordId();

}
