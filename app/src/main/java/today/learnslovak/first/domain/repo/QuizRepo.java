package today.learnslovak.first.domain.repo;

public interface QuizRepo {

  int getScore();

  void scoreAdd(int score, String word);
}
