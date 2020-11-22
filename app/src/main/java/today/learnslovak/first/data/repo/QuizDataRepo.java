package today.learnslovak.first.data.repo;

import javax.inject.Inject;
import javax.inject.Singleton;
import today.learnslovak.first.domain.model.QuizScore;
import today.learnslovak.first.domain.repo.QuizRepo;

@Singleton
public class QuizDataRepo implements QuizRepo {

  private final QuizScore quizScore;

  @Inject public QuizDataRepo(QuizScore quizScore) {

    this.quizScore = quizScore;
  }

  @Override public int getScore() {
    int perc = 0;
    if (quizScore.getCorrect() > 0) {
      perc = quizScore.getCorrect() * 100 / (quizScore.getCorrect() + quizScore.getWrong());
      perc = Math.round(perc);
    }
    return perc;
  }

  @Override public void scoreAdd(int score, String word) {

    if (score > 0) {
      quizScore.setCorrect(quizScore.getCorrect() + 1);
    }
    if (score < 0) {
      quizScore.setWrong(quizScore.getWrong() + 1);
    }
  }
}
