package today.learnslovak.first.presentation.ui.quiz;

import java.util.regex.Matcher;
import javax.inject.Inject;
import javax.inject.Singleton;
import today.learnslovak.first.domain.model.Snippet;
import today.learnslovak.first.presentation.common.tts.TtsService;
import today.learnslovak.first.presentation.common.tts.TtsServiceUtil;
import today.learnslovak.first.presentation.html.Highlighter;
import today.learnslovak.first.presentation.ui.wordext.HtmlTtsProvider;

@Singleton public class HtmlQuizProvider extends HtmlTtsProvider {
  private final TextQuiz textQuiz;

  @Inject
  public HtmlQuizProvider(TextQuiz textQuiz, TtsService ttsService, TtsServiceUtil ttsServiceUtil) {
    super(ttsService, ttsServiceUtil);
    this.textQuiz = textQuiz;
  }

  @Override public String snippetSourceDecorate(Snippet snippet, Highlighter highlighter) {
    return highlighter.apply(textQuiz.wrap(escapeOne(snippet.getSource())),
        snippet.getSourceLang());
  }

  @Override public String getFooter() {
    return textQuiz.consumeJsBody() + super.getFooter();
  }

  private String escapeOne(String text) {
    return text.
        replaceAll("\n", " ").
        replaceAll(Matcher.quoteReplacement("\\'"), "&apos;").
        replaceAll(Matcher.quoteReplacement("'"), "&apos;");
  }
}
