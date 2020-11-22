package today.learnslovak.first.presentation.di;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import today.learnslovak.first.data.repo.PrefDataRepo;
import today.learnslovak.first.data.repo.QuizDataRepo;
import today.learnslovak.first.data.repo.SnippetDataRepo;
import today.learnslovak.first.data.repo.WordDataRepo;
import today.learnslovak.first.data.store.pref.LocalPrefDataStore;
import today.learnslovak.first.data.store.pref.PrefDataStore;
import today.learnslovak.first.data.store.skip.LocalDbSkipDataStore;
import today.learnslovak.first.data.store.skip.SkipDataStore;
import today.learnslovak.first.domain.repo.PrefRepo;
import today.learnslovak.first.domain.repo.QuizRepo;
import today.learnslovak.first.domain.repo.SnippetRepo;
import today.learnslovak.first.domain.repo.WordRepo;

@InstallIn(ActivityComponent.class)
@Module
public abstract class CoreModule {

  @Binds public abstract WordRepo bindWordRepo(WordDataRepo wordDataRepo);

  @Binds public abstract SnippetRepo bindSnippetRepo(SnippetDataRepo snippetDataRepo);

  @Binds public abstract PrefRepo bindPrefRepo(PrefDataRepo prefDataRepo);

  @Binds public abstract QuizRepo bindQuizRepo(QuizDataRepo quizDataRepo);

  @Binds public abstract PrefDataStore bindPrefDataStore(LocalPrefDataStore localPrefDataStore);

  /* @Binds
   public abstract SkipDataStore bindSkipDataStore(LocalSpSkipDataStore localSpSkipDataStore);
*/
  @Binds public abstract SkipDataStore bindSkipDataStore(LocalDbSkipDataStore localDbSkipDataStore);
}
