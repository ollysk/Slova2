package today.learnslovak.first.presentation.ui.quiz;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import today.learnslovak.first.presentation.html.HtmlProviderFactory;
import today.learnslovak.first.presentation.ui.wordext.WordExtActivity;

public class QuizActivity extends WordExtActivity {

  QuizViewModel quizViewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void setUpViewModel() {
    super.setUpViewModel();
    super.viewModel.setHtmlPresenter(HtmlProviderFactory.HtmlType.QUIZ, getCss());
    quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
  }
}