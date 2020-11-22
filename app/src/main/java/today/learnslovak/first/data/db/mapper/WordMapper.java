package today.learnslovak.first.data.db.mapper;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.inject.Inject;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.repo.PrefRepo;

public class WordMapper {

  private final PrefRepo prefRepo;

  @Inject public WordMapper(PrefRepo prefRepo) {
    this.prefRepo = prefRepo;
  }

  private Map<Lang, String> toWordDbLangMap(WordDb wordDb) {
    return new HashMap<Lang, String>() {{
      put(Lang.SK, wordDb.getSk());
      put(Lang.EN, wordDb.getEn());
      put(Lang.RU, wordDb.getRu());
      put(Lang.UK, wordDb.getUk());
    }};
  }

  private Map<Lang, String> toWordLangMap(Word word, LinkedHashSet<Lang> langs) {
    Map<Lang, String> langMap = new HashMap<>();
    int i = 0;

    for (Lang lang : langs) {
      if (i == 0) {
        langMap.put(lang, word.getSource());
      } else if (i == 1) {
        langMap.put(lang, word.getTrans());
      } else if (i == 2) {
        langMap.put(lang, word.getTransSecond());
      } else if (i == 3) {
        langMap.put(lang, word.getTransThird());
      }
      i++;
    }

    return langMap;
  }

  public WordDb toWordDb(Word word, LinkedHashSet<Lang> langs) {
    Map<Lang, String> langMap = toWordLangMap(word, langs);

    return WordDb.builder()
        .id(word.getId())
        .snippetIds(word.getSnippetIds())
        .sk(langMap.get(Lang.SK))
        .en(langMap.get(Lang.EN))
        .ru(langMap.get(Lang.RU))
        .uk(langMap.get(Lang.UK))
        .build();
  }

  public Word toWord(WordDb wordDb, boolean isTransVisible) {
    Map<Lang, String> langMap = toWordDbLangMap(wordDb);
    int i = 0;

    Word.WordBuilder word = Word.builder()
        .id(wordDb.getId())
        .snippetIds(wordDb.getSnippetIds())
        .isTransVisible(isTransVisible)
        .isTransSecondVisible(prefRepo.get(Pref.SHOW_LANG2, false));

    for (Lang lang : prefRepo.getAvailableLangs()) {
      if (i == 0) {
        word.source(langMap.get(lang));
        word.sourceLang(lang);
      } else if (i == 1) {
        word.trans(langMap.get(lang));
        word.transLang(lang);
      } else if (i == 2) {
        word.transSecond(langMap.get(lang));
        word.transSecondLang(lang);
      } else if (i == 3) {
        word.transThird(langMap.get(lang));
        word.transThirdLang(lang);
      }
      i++;
    }
    return word.build();
  }
}
