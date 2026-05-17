package today.learnslovak.first.presentation.ui.wordext;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    extendedInfoDecorate(word);
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

  private void extendedInfoDecorate(Word word) {
    if (!word.isExtendedInfoVisible()) {
      return;
    }

    if (word.getPartOfSpeech() != null && !word.getPartOfSpeech().isEmpty()) {
      res.append("<div class='part-of-speech'>").append(word.getPartOfSpeech()).append("</div>");
    }

    Set<String> forms = new LinkedHashSet<>();
    if (word.getDeclensions() != null) {
      for (Word.DeclensionItem item : word.getDeclensions()) {
        if (item.getForm() != null && !item.getForm().isEmpty()) {
          forms.add(item.getForm());
        }
      }
    }
    if (word.getConjugations() != null) {
      for (Word.ConjugationItem item : word.getConjugations()) {
        if (item.getForm() != null && !item.getForm().isEmpty()) {
          forms.add(item.getForm());
        }
      }
    }

    if (!forms.isEmpty()) {
      res.append("<div class='forms-summary'>")
          .append(String.join(", ", forms))
          .append("</div>");

      res.append("<div class='toggle-tables' onclick=\"var x = document.getElementById('extended-info-tables'); if (x.style.display === 'none') { x.style.display = 'block'; this.innerHTML = 'Hide details'; x.scrollIntoView(); } else { x.style.display = 'none'; this.innerHTML = 'Show details'; }\">Show details</div>");

      res.append("<div id='extended-info-tables' style='display:none;'>");

      if (word.getDeclensions() != null && !word.getDeclensions().isEmpty()) {
        res.append("<div class='extended-section'>");
        res.append("<table class='declension-table'>");
        res.append("<tr><th>Form</th><th>Case</th><th>Number</th><th>Gender</th></tr>");
        for (Word.DeclensionItem item : word.getDeclensions()) {
          res.append("<tr>")
              .append("<td><b>").append(item.getForm() != null ? item.getForm() : "").append("</b></td>")
              .append("<td>").append(item.getCaseName() != null ? item.getCaseName() : "").append("</td>")
              .append("<td>").append(item.getNumber() != null ? item.getNumber() : "").append("</td>")
              .append("<td>").append(item.getGender() != null ? item.getGender() : "").append("</td>")
              .append("</tr>");
        }
        res.append("</table>");
        res.append("</div>");
      }

      if (word.getConjugations() != null && !word.getConjugations().isEmpty()) {
        res.append("<div class='extended-section'>");
        res.append("<table class='conjugation-table'>");
        res.append("<tr><th>Form</th><th>Tense</th><th>Person</th><th>Number</th></tr>");
        for (Word.ConjugationItem item : word.getConjugations()) {
          res.append("<tr>")
              .append("<td>").append(item.getForm() != null ? item.getForm() : "").append("</td>")
              .append("<td>").append(item.getTense() != null ? item.getTense() : "").append("</td>")
              .append("<td>").append(item.getPerson() != null ? item.getPerson() : "").append("</td>")
              .append("<td>").append(item.getNumber() != null ? item.getNumber() : "").append("</td>")
              .append("</tr>");
        }
        res.append("</table>");
        res.append("</div>");
      }
      res.append("</div>"); // end extended-info-tables
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
