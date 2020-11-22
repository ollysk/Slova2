package today.learnslovak.first.data.store.data;

import java.util.List;
import javax.inject.Inject;
import today.learnslovak.first.data.common.util.AssetsUtil;
import today.learnslovak.first.data.db.mapper.SnippetDbMapper;
import today.learnslovak.first.data.db.mapper.WordDbMapper;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;

public class LocalDataStore implements DataStore {

  private final String ASSETS_JSON_PATH = "json/";

  private final WordDbMapper wordDbMapper;
  private final SnippetDbMapper snippetDbMapper;
  private final AssetsUtil assetsUtil;

  @Inject public LocalDataStore(WordDbMapper wordDbMapper, SnippetDbMapper snippetDbMapper,
      AssetsUtil assetsUtil) {
    this.wordDbMapper = wordDbMapper;
    this.snippetDbMapper = snippetDbMapper;
    this.assetsUtil = assetsUtil;
  }

  @Override public List<WordDb> getWordDbs() {
    return wordDbMapper.toWordDb(
        assetsUtil.getFromAssets(ASSETS_JSON_PATH + WORD_JSON_FILENAME));
  }

  @Override public List<SnippetDb> getSnippetDbs() {
    return snippetDbMapper.toSnippetDb(
        assetsUtil.getFromAssets(ASSETS_JSON_PATH + SNIPPET_JSON_FILENAME));
  }
}
