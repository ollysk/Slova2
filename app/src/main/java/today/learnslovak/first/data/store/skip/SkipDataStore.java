package today.learnslovak.first.data.store.skip;

import java.util.Set;
import today.learnslovak.first.domain.model.Lang;

public interface SkipDataStore {

  Set<Integer> getSkipIds(Lang lang);

  void addSkipId(Lang lang, int id);
}
