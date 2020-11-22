package today.learnslovak.first.domain.repo;

import androidx.lifecycle.LiveData;
import java.util.List;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Snippet;

public interface SnippetRepo {

  Snippet findById(String id);

  LiveData<List<Snippet>> find(List<Integer> ids);

  LiveData<List<Snippet>> searchByLang(String query, Lang lang);
}
