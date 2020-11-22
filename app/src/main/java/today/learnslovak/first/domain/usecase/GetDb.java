package today.learnslovak.first.domain.usecase;

import androidx.lifecycle.LiveData;
import javax.inject.Inject;
import today.learnslovak.first.domain.repo.SnippetRepo;
import today.learnslovak.first.domain.repo.WordRepo;

public class GetDb {

  private final WordRepo wordRepo;
  private final SnippetRepo snippetRepo;

  @Inject public GetDb(WordRepo wordRepo, SnippetRepo snippetRepo) {
    this.wordRepo = wordRepo;
    this.snippetRepo = snippetRepo;
  }

  public LiveData<Integer> findMaxWordId() {
    return wordRepo.findMaxWordId();
  }
}
