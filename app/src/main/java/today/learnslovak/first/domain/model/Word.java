package today.learnslovak.first.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Word {

  private final int id;
  private final String source;
  private final Lang sourceLang;
  private final String trans;
  private final Lang transLang;
  private final String transSecond;
  private final Lang transSecondLang;
  private final String transThird;
  private final Lang transThirdLang;
  private final String snippetIds;
  private boolean isTransVisible;
  private boolean isTransSecondVisible;
}
