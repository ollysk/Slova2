package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import androidx.lifecycle.LiveData;
import java.util.List;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Word;

public interface MenuActions {

  LiveData<List<Word>> suggestions();

  void setSuggestionsQuery(String query);

  Lang getSearchLang();

  void setSearchLang(Lang lang);
}
