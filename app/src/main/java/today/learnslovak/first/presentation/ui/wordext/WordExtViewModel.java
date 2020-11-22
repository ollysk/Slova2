package today.learnslovak.first.presentation.ui.wordext;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import java.util.List;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Snippet;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.domain.usecase.GetPrefs;
import today.learnslovak.first.domain.usecase.GetSnippets;
import today.learnslovak.first.domain.usecase.GetWords;
import today.learnslovak.first.presentation.common.tts.TtsService;
import today.learnslovak.first.presentation.html.HtmlProviderFactory;
import today.learnslovak.first.presentation.html.HtmlSnippetProvider;
import today.learnslovak.first.presentation.ui.common.toolbar.menu.MenuSearch;

import static today.learnslovak.first.presentation.html.HtmlProviderFactory.HtmlType.TTS;

public class WordExtViewModel extends ViewModel implements MenuSearch {

  private final GetWords getWords;
  private final GetSnippets getSnippets;
  private final HtmlProviderFactory htmlProviderFactory;
  private final GetPrefs getPrefs;
  private final TtsService ttsService;
  //private MutableLiveData<Integer> idWords = new MutableLiveData<>(1);
  private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
  private final MutableLiveData<Integer> wordId = new MutableLiveData<>(0);
  private int retainedWordId;
  private final LiveData<Word> wordLive =
      Transformations.switchMap(wordId, this::getSuitableWord);
  private HtmlSnippetProvider htmlSnippetProvider;
  //private MutableLiveData<String> suggestionsQuery = new MutableLiveData<>();
  private final LiveData<String> htmlLiveData = Transformations.switchMap(wordLive,
      newWord -> hasSnippetIds(newWord) || searchQuery.getValue() == null ? htmlFromWordSnippets(
          newWord) : htmlFromSearchSnippets(searchQuery.getValue()));
  private String css;

  @ViewModelInject public WordExtViewModel(GetWords getWords, GetSnippets getSnippets,
      HtmlProviderFactory htmlProviderFactory, GetPrefs getPrefs, TtsService ttsService) {

    this.getWords = getWords;
    this.getSnippets = getSnippets;
    this.htmlProviderFactory = htmlProviderFactory;
    this.getPrefs = getPrefs;
    this.ttsService = ttsService;
  }

  HtmlSnippetProvider getHtmlProvider() {
    return htmlSnippetProvider
        == null ? htmlProviderFactory.getHtmlProvider(TTS) : htmlSnippetProvider;
  }

  public void setHtmlPresenter(HtmlProviderFactory.HtmlType type, String css) {
    htmlSnippetProvider = htmlProviderFactory.getHtmlProvider(type);
    htmlSnippetProvider.setCss(css);
  }

  LiveData<Word> getSuitableWord(int newId) {

    LiveData<Word> word;
    if (newId >= 0) {
      if (retainedWordId != 0) {
        word = getWords.findOneByExact(newId);
      } else {
        word = getWords.findOneFrom(newId);
      }
    } else {
      word = getWords.findOneByExact(searchQuery.getValue());
    }
    return word;
  }

  public LiveData<Word> getWordLive() {
    return wordLive;
  }

  public Word getWord() {
    return wordLive.getValue() != null ? wordLive.getValue() : createWord("");
  }

  boolean hasSnippetIds(Word word) {
    return word.getSnippetIds() != null;
  }

  public LiveData<String> getWebViewData() {
    return htmlLiveData;
  }

  ////////////////////////////////

  private String toWebViewString(List<Snippet> snippets, Word word) {
    return getHtmlProvider().getHtml(word, snippets);
  }

  private String toWebViewString(List<Snippet> snippets, Word word, String searchQuery) {

    return getHtmlProvider().getHtml(word, snippets, searchQuery, word.getSourceLang());
  }

  private LiveData<String> htmlFromWordSnippets(Word word) {
    return word.getId() == 0 ? new MutableLiveData<>("Loading...")
        : Transformations.map(getSnippets.find(word), s -> toWebViewString(s, word));
  }

  private LiveData<String> htmlFromSearchSnippets(String query) {
    Word word = createWord(query);
    return Transformations.map(getSnippets.searchByLang(query),
        s -> toWebViewString(s, word, query));
  }

  //////////////////////////////

  private Word createWord(String searchQuery) {
    return Word.builder()
        .id(0)
        .source(searchQuery)
        .sourceLang(getPrefs.getSearchLang())
        .trans("")
        .transLang(Lang.SK)
        .transSecond("")
        .transSecondLang(Lang.EN)
        .isTransVisible(true)
        .isTransSecondVisible(false)
        .snippetIds("")
        .transThird("")
        .build();
  }

  public void init() {
    wordId.postValue(getStartId());
  }

  public int getStartId() {
    return getPrefs.getWordExtStartId();
    //return 1;
  }

  public void onFabClick() {
    int nextId = retainedWordId != 0 ? getRetainedNextId() : getNextId();
    retainedWordId = 0;
    ttsService.stop();
    setWordId(nextId);
  }

  private int getRetainedNextId() {
    return retainedWordId + 1;
  }

  private int getNextId() {
    return getWord().getId() >= 0 ? getWord().getId() + 1 : getStartId();
  }

  /**
   * The purpose of this method is to preserve WordId.
   * When user decides to search some word, clicks on suggestions and gets word with snippets,
   * WordId is changes.
   * Without this method next WordId (after Fab click) would be id next to the id of the Word that
   * is found,
   * not the id on which user really stopped.
   */
  @Override public void setTempWordId(int id) {
    retainWordId();
    setWordId(id);
  }

  private void retainWordId() {
    if (retainedWordId == 0) {
      retainedWordId = getWordId();
    }
  }

  private int getWordId() {
    return wordId.getValue() != null ? wordId.getValue() : 0;
  }

  public void setWordId(int id) {
    wordId.setValue(id);
  }

  public void saveWordExtId() {

    if (getWord().getId() > 0 && retainedWordId == 0) {
      getPrefs.saveWordExtId(getWord().getId());
    }
  }

  public void setSkipFromWordExt() {
    getPrefs.setSkipFromWordExt();
  }

  ////////menu

  public LiveData<List<Word>> findByLang(String query) {
    return getWords.findByFuzzy(query);
  }

  @Override public void setSearchQuery(String query) {
    retainedWordId = getWordId();
    //realWordId = realWordId == 0 ? wordId.getValue() : realWordId;
    searchQuery.setValue(query);
    wordId.setValue(-1);
  }

  ////end menu

  public void gotoStart() {
    wordId.setValue(0);
  }
}
