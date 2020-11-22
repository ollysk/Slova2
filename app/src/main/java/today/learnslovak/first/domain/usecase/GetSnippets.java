package today.learnslovak.first.domain.usecase;

import android.text.TextUtils;
import androidx.lifecycle.LiveData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Snippet;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.repo.PrefRepo;
import today.learnslovak.first.domain.repo.SnippetRepo;

public class GetSnippets {

  private final SnippetRepo repo;
  private final PrefRepo pref;
  //todo split domain, data and presentation into separate modules later
  //cons: will need to make one more layer of dto
  //pros: can write different modules on java and kotlin in one project

  @Inject public GetSnippets(SnippetRepo repo, PrefRepo pref) {
    this.repo = repo;
    this.pref = pref;
  }

  private List<Integer> csvToList(String csv) {
    return TextUtils.isEmpty(csv) ? new ArrayList<>()
        : Arrays.stream(csv.split(",")).map(Integer::valueOf).collect(Collectors.toList());
  }

  public LiveData<List<Snippet>> find(Word word) {

    return repo.find(csvToList(word.getSnippetIds()));
  }

  public LiveData<List<Snippet>> searchByLang(String query) {

    return repo.searchByLang(query, pref.get(Pref.LANG_SEARCH, Lang.SK));
  }
}
