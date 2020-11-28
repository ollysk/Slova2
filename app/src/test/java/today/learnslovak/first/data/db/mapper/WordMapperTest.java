package today.learnslovak.first.data.db.mapper;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Pref;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.repo.PrefRepo;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordMapperTest {

  @Mock PrefRepo prefRepo;
  LinkedHashSet<Lang> availableLangs;
  Map<Lang, String> map;
  Word word;
  WordMapper wordMapper;

  @BeforeEach
  void setUp() {
    availableLangs = Stream.of(Lang.SK, Lang.EN, Lang.RU, Lang.UK)
        .collect(Collectors.toCollection(LinkedHashSet::new));
    map = availableLangs.stream().collect(Collectors.toMap(lang -> lang, Enum::name));
    when(prefRepo.get(Pref.SHOW_LANG2, false)).thenReturn(false);
    when(prefRepo.getAvailableLangs()).thenReturn(availableLangs);
    Iterator<Lang> langIterator = availableLangs.iterator();
    word = Word.builder().id(1)
        .source(map.get(Lang.SK))
        .sourceLang(langIterator.next())
        .trans(map.get(Lang.EN))
        .transLang(langIterator.next())
        .transSecond(map.get(Lang.RU))
        .transSecondLang(langIterator.next())
        .transThird(map.get(Lang.UK))
        .transThirdLang(langIterator.next())
        .build();

    wordMapper = new WordMapper(prefRepo);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void toWord() {
    WordDb wordDb = WordDb.builder().id(1)
        .sk(map.get(Lang.SK))
        .en(map.get(Lang.EN))
        .ru(map.get(Lang.RU))
        .uk(map.get(Lang.UK))
        .build();
    Word word2 = wordMapper.toWord(wordDb, false);

    assertThat(word2).isEqualTo(word);
  }
}