package today.learnslovak.first.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Snippet {

  private final int id;
  private final String source;
  private final Lang sourceLang;
  private final String trans;
  private final Lang transLang;
  private final String transSecond;
  private final Lang transSecondLang;
  private final String transThird;
}
