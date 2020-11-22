package today.learnslovak.first.presentation.html;

import javax.inject.Inject;
import today.learnslovak.first.presentation.ui.quiz.HtmlQuizProvider;
import today.learnslovak.first.presentation.ui.wordext.HtmlTtsProvider;

public class HtmlProviderFactory {

  private final HtmlTtsProvider tts;
  private final HtmlQuizProvider quiz;

  @Inject public HtmlProviderFactory(HtmlTtsProvider tts, HtmlQuizProvider quiz) {
    this.tts = tts;
    this.quiz = quiz;
  }

  public HtmlSnippetProvider getHtmlProvider(HtmlType htmlType) {
    switch (htmlType) {
      case TTS:
        return tts;
      case QUIZ:
        return quiz;
    }
    throw new UnsupportedOperationException("Factory class unsupported exception==");
  }

  public enum HtmlType {TTS, QUIZ}
}
