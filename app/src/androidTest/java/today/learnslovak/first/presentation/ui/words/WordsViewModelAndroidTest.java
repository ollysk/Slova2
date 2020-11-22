package today.learnslovak.first.presentation.ui.words;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import today.learnslovak.first.InstantExecutorExtension;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.usecase.GetPrefs;
import today.learnslovak.first.domain.usecase.GetWords;

import static com.google.common.truth.Truth.assertThat;
import static today.learnslovak.first.LiveDataTestUtil.getLiveDataValue;

// for assertions on Java 8 types (Streams and java.util.Optional)

/**
 * JUnit 5 requires an environment built on top of Java 8. For Android, this means that devices
 * running Android 8.0/API 26/Oreo are supported, and can execute JUnit 5 instrumentation tests just
 * fine - older devices can not.
 */
//fixme
@ExtendWith(InstantExecutorExtension.class)
@ExtendWith(MockitoExtension.class)
    //@RunWith(JUnitPlatform.class)
class WordsViewModelAndroidTest {



/*    @Rule
    public MockitoRule rule = MockitoJUnit.rule();*/

  //@Mock
  WordsViewModel viewModel;
  List<Word> words = new ArrayList<>();
  MutableLiveData<List<Word>> wordsLive = new MutableLiveData<>();
  @Mock
  GetWords getWords;// = Mockito.mock(FindWord.class);
  @Mock
  RecyclerViewService recyclerViewService;

  @Mock GetPrefs getPrefs;

  @BeforeEach
  public void setUp() throws Exception {

    viewModel = new WordsViewModel(getWords, recyclerViewService, getPrefs);
    for (int i = 10; i < 200; i++) {
      Word word = Word.builder().id(i).source("source-" + i).trans("trans-" + i).build();
      words.add(word);
    }

    //viewModel.addIntoRecyclerView(words);

    //        when(viewModel.adapterWords).thenReturn(words);

  }

  @Test
  void words() {
  }

  @Test
  void getAdapterWords() {
  }

/*    @Test
    @DisplayName("Given id = 0, When loadWordsIfNeeded, LiveData words() size == all words ")
    void initPreloadMoreItemsIfNeeded() throws InterruptedException {
        int id = 0;
        viewModel.loadWordsIfNeeded();
        when(findWord.findAll(anyInt())).thenReturn(findAllStub(id));
        assertThat(getOrAwaitValue(viewModel.words())).hasSize(20);
    }*/

  @Test
  @DisplayName("Given valid click position, return correct wordId ")
  void initPreloadMoreItemsIfNeeded2() throws InterruptedException {
    int id = 11;
    viewModel.addWords(findAllStub0(0));
    viewModel.setClickPosition(id);
    ///when(findWord.findAll(anyInt())).thenReturn(findAllStub(viewModel.getClickedWordId()));
    assertThat(getLiveDataValue(viewModel.words()).get(0).getId()).isEqualTo(id + 20);
  }

  @Test
  @DisplayName(
      "Given invalid click position, load and return data starting from 0, not from invalid position ")
  void initPreloadMoreItemsIfNeeded3() throws InterruptedException {
    viewModel.addWords(findAllStub0(0));
    viewModel.setClickPosition(50000);
    ///when(findWord.findAll(anyInt())).thenReturn(findAllStub(viewModel.getClickedWordId()));
    assertThat(getLiveDataValue(viewModel.words()).get(0).getId()).isEqualTo(10);
  }
/*
    @Test
    void initLoadWords() throws InterruptedException {
        int id = 0;
        viewModel.getAdapterWords();
        when(findWord.findAll(anyInt())).thenReturn(findAllStub(id));
        assertThat(getOrAwaitValue(viewModel.words()).size()).isEqualTo(20);
    }*/

  List<Word> findAllStub0(int id) {
    int PRELOAD_CHUNK_SIZE = 20;
    List<Word> subWords = new ArrayList<>();
    if (words.size() > id) {
      subWords = words.subList(id, id + PRELOAD_CHUNK_SIZE);
      //wordsLive.setValue(subWords);

    }
    return subWords;
  }

  LiveData<List<Word>> findAllStub(int id) {
    int PRELOAD_CHUNK_SIZE = 20;
    List<Word> subWords = new ArrayList<>();
    if (words.size() > id) {
      subWords = words.subList(id, id + PRELOAD_CHUNK_SIZE);
      wordsLive.setValue(subWords);
    }
    return wordsLive;
  }

   /* //todo how to verify that there is no duplicates, found and asked for
    @Test
    public void getLastLoadedWordId() {
        viewModel.addIntoRecyclerView(words);
        int id = 0;
        assertThat(viewModel.getLastLoadedWordId()).isEqualTo(words.get(words.size()-1).getId());
    }
*/

  @Test
  public void setClickPosition() {
    int position = 15;
    viewModel.setClickPosition(position);
    assertThat(getLiveDataValue(viewModel.clickPosition())).isEqualTo(position);
  }

  @Test
  void onFabClicked() {
  }

  @Test
  void saveLastId() {
  }
}