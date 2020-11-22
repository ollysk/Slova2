package today.learnslovak.first.domain.model;

import javax.inject.Inject;
import lombok.Data;

@Data
//@NoArgsConstructor
//@Builder
public class QuizScore {

  public int correct;
  public int wrong;
  public int total;

  @Inject public QuizScore() {
  }
}
