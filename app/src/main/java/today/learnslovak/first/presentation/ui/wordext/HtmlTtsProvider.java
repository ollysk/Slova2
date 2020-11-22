package today.learnslovak.first.presentation.ui.wordext;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Snippet;
import today.learnslovak.first.domain.model.Word;
import today.learnslovak.first.presentation.common.tts.TtsService;
import today.learnslovak.first.presentation.common.tts.TtsServiceUtil;
import today.learnslovak.first.presentation.html.Highlighter;
import today.learnslovak.first.presentation.html.HtmlSnippetProvider;

@Singleton
public class HtmlTtsProvider implements HtmlSnippetProvider {

  private final TtsService ttsService;
  private final TtsServiceUtil ttsServiceUtil;
  private String css;
  private StringBuilder res;

  @Inject public HtmlTtsProvider(TtsService ttsService, TtsServiceUtil ttsServiceUtil) {
    this.ttsService = ttsService;
    this.ttsServiceUtil = ttsServiceUtil;
  }

  @Override public String getHtml(Word word, List<Snippet> snippets) {
    return getHtml(word, snippets, "", Lang.SK);
  }

  @Override
  public String getHtml(Word word, List<Snippet> snippets, String searchQuery, Lang searchLang) {
    populateHeader();
    wordDecorate(word);
    snippetsDecorate(snippets, searchQuery, searchLang);
    populateFooter();
    return res.toString();
  }

  private void populateHeader() {
    res = new StringBuilder(getHeader(css));
  }

  private void wordDecorate(Word word) {
    res.append("<h1 class='word-source'>")
        .append(word.getSource())
        .append("<span class='icon-speak'>")
        .append(ttsServiceUtil.jsTts(word.getSource(), word.getSourceLang()))
        .append("</h1></span>")
        .append(
            "<span class='icon-speak-batch' onClick=\"Android.speakBatch();\">\uD83D\uDD08</span>");

    ttsService.clearSpeakBatch();
    ttsService.addToSpeakBatch(word.getSource(), word.getSourceLang());
    ttsService.addToSpeakBatch(word.getTrans(), word.getTransLang());
    if (word.isTransSecondVisible()) {
      ttsService.addToSpeakBatch(word.getTransSecond(), word.getTransSecondLang());
    }
  }

  private void snippetsDecorate(List<Snippet> snippets, String searchQuery, Lang searchLang) {
    Highlighter highlighter =
        Highlighter.builder().searchQuery(searchQuery).searchLang(searchLang).build();
    for (Snippet snippet : snippets) {
      res.append(ttsServiceUtil.jsTts(snippet.getSource(), snippet.getSourceLang()))
          //.append(highlightQueryInText("de",snippet.getSource()))
          .append(snippetSourceDecorate(snippet, highlighter))
          .append("<hr><span class='snippet snippet-trans'>")
          //.append(snippet.getTrans())
          .append(highlighter.apply(snippet.getTrans(), snippet.getTransLang()))
          .append("</span><hr>");
      ttsService.addToSpeakBatch(snippet.getSource(), snippet.getSourceLang());
      ttsService.addToSpeakBatch(snippet.getTrans(), snippet.getTransLang());
    }
  }

  public String snippetSourceDecorate(Snippet snippet, Highlighter highlighter) {
    return highlighter.apply(snippet.getSource(), snippet.getSourceLang());
  }

  private void populateFooter() {
    res.append(getFooter());
  }

  @Override public void setCss(String css) {
    this.css = css;
  }
}
