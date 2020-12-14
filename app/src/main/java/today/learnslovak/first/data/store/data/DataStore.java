package today.learnslovak.first.data.store.data;

import java.util.List;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;

public interface DataStore {

  String WORD_JSON_FILENAME = "words.json";
  String SNIPPET_JSON_FILENAME = "snippets.json";

  List<WordDb> getWordDbs();

  List<SnippetDb> getSnippetDbs();

  List<WordDb> getWordDbsPatch(int patchLevel);

  List<SnippetDb> getSnippetDbsPatch(int patchLevel);
}
