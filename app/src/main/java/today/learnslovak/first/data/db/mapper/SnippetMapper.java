package today.learnslovak.first.data.db.mapper;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Snippet;

public class SnippetMapper {

  @Inject public SnippetMapper() {
  }

  private Map<Lang, String> toSnippetDbLangMap(SnippetDb SnippetDb) {
    return new HashMap<Lang, String>() {{
      put(Lang.SK, SnippetDb.getSk());
      put(Lang.EN, SnippetDb.getEn());
      put(Lang.RU, SnippetDb.getRu());
      put(Lang.UK, SnippetDb.getUk());
    }};
  }

  private Map<Lang, String> toSnippetLangMap(Snippet snippet, LinkedHashSet<Lang> langs) {
    Map<Lang, String> langMap = new HashMap<>();
    int i = 0;

    for (Lang lang : langs) {
      if (i == 0) {
        langMap.put(lang, snippet.getSource());
      } else if (i == 1) {
        langMap.put(lang, snippet.getTrans());
      } else if (i == 2) {
        langMap.put(lang, snippet.getTransSecond());
      } else if (i == 3) {
        langMap.put(lang, snippet.getTransThird());
      }
      i++;
    }

    return langMap;
  }

  public SnippetDb toSnippetDb(Snippet snippet, LinkedHashSet<Lang> langs) {
    Map<Lang, String> langMap = toSnippetLangMap(snippet, langs);

    return SnippetDb.builder()
        .id(snippet.getId())
        .sk(langMap.get(Lang.SK))
        .en(langMap.get(Lang.EN))
        .ru(langMap.get(Lang.RU))
        .uk(langMap.get(Lang.UK))
        .build();
  }

  public Snippet toSnippet(SnippetDb SnippetDb, List<Lang> langs) {
    Map<Lang, String> langMap = toSnippetDbLangMap(SnippetDb);
    int i = 0;
    Snippet.SnippetBuilder snippet = Snippet.builder().id(SnippetDb.getId());
    for (Lang lang : langs) {
      if (i == 0) {
        snippet.source(langMap.get(lang));
        snippet.sourceLang(lang);
      } else if (i == 1) {
        snippet.trans(langMap.get(lang));
        snippet.transLang(lang);
      } else if (i == 2) {
        snippet.transSecond(langMap.get(lang));
        snippet.transSecondLang(lang);
      } else if (i == 3) {
        snippet.transThird(langMap.get(lang));
      }
      i++;
    }
    return snippet.build();
  }
}
