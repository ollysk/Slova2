package today.learnslovak.first.domain.usecase;

import javax.inject.Inject;
import today.learnslovak.first.domain.repo.QuizRepo;

public class GetQuiz {

  private final QuizRepo quizRepo;

  @Inject public GetQuiz(QuizRepo quizRepo) {
    this.quizRepo = quizRepo;
  }

  public int getScore() {
    return quizRepo.getScore();
  }

  public void scoreAdd(int score, String word) {
    quizRepo.scoreAdd(score, word);
  }
}
