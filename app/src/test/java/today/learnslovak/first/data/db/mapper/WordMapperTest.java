package today.learnslovak.first.data.db.mapper;

import java.util.Collections;
import java.util.List;
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
  List<Lang> availableLangs;
  Map<Lang, String> map;
  Word word;
  WordMapper wordMapper;

  @BeforeEach
  void setUp() {
    availableLangs = Stream.of(Lang.SK, Lang.EN, Lang.RU, Lang.UK)
        .collect(Collectors.toList());

    map = Stream.of(new Object[][] {
        { availableLangs.get(0), "0" },
        { availableLangs.get(1), "1" },
        { availableLangs.get(2), "2" },
        { availableLangs.get(3), "3" },
    }).collect(Collectors.toMap(data -> (Lang) data[0], data -> (String) data[1]));

    when(prefRepo.get(Pref.SHOW_LANG2, false)).thenReturn(false);
    when(prefRepo.getAvailableLangs()).thenReturn(availableLangs);

    word = Word.builder().id(1)
        .source(map.get(Lang.SK))
        .sourceLang(availableLangs.get(0))
        .trans(map.get(Lang.EN))
        .transLang(availableLangs.get(1))
        .transSecond(map.get(Lang.RU))
        .transSecondLang(availableLangs.get(2))
        .transThird(map.get(Lang.UK))
        .transThirdLang(availableLangs.get(3))
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

  @Test
  void toWord2() {
    Collections.shuffle(availableLangs);
    WordDb wordDb = WordDb.builder().id(1)
        .sk(map.get(Lang.SK))
        .en(map.get(Lang.EN))
        .ru(map.get(Lang.RU))
        .uk(map.get(Lang.UK))
        .build();
    Word word2 = wordMapper.toWord(wordDb, false);

    assertThat(word2).isNotEqualTo(word);
  }
}