package today.learnslovak.first.data.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.sqlite.db.SimpleSQLiteQuery;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.data.db.Db;
import today.learnslovak.first.data.db.mapper.SnippetMapper;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Snippet;
import today.learnslovak.first.domain.repo.PrefRepo;
import today.learnslovak.first.domain.repo.SnippetRepo;

import static java.util.stream.Collectors.toList;

public class SnippetDataRepo implements SnippetRepo {

  private final Db db;
  private final SnippetMapper snippetMapper;
  private final PrefRepo prefRepo;

  @Inject public SnippetDataRepo(Db db, SnippetMapper snippetMapper, PrefRepo prefRepo) {
    this.db = db;
    this.snippetMapper = snippetMapper;
    this.prefRepo = prefRepo;
  }

  @Override public LiveData<List<Snippet>> find(List<Integer> ids) {

    int snippetsLimit = prefRepo.getFromString(Pref.SNIPPETS_LIMIT, 25);

    return Transformations.map(db.snippetDao().findById(ids, snippetsLimit),
        snippetDb -> snippetDb.stream()
            .map(snippetEntity1 -> snippetMapper.toSnippet(snippetEntity1,
                prefRepo.getAvailableLangs()))
            .collect(toList()));
  }

  @Override public Snippet findById(String id) {
    return null;
  }

  @Override public LiveData<List<Snippet>> searchByLang(String query, Lang lang) {
    int snippetsLimit = prefRepo.getFromString(Pref.SNIPPETS_LIMIT, 25);

    SimpleSQLiteQuery q = new SimpleSQLiteQuery(
        String.format(Locale.ENGLISH, "select * from %s where %s like '%%%s%%' limit %d",
            DataConfig.TABLE_NAME_SNIPPET, lang, query, snippetsLimit));

    Timber.d(q.getSql());

    return Transformations.map(db.snippetDao().findByLang(q), snippetDb -> snippetDb.stream()
        .map(snippetEntity1 -> snippetMapper.toSnippet(snippetEntity1,
            prefRepo.getAvailableLangs()))
        .collect(toList()));
  }
}
