package today.learnslovak.first.domain.model;

import java.util.List;

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
  private boolean isExtendedInfoVisible;

  private final String partOfSpeech;
  private final List<DeclensionItem> declensions;
  private final List<ConjugationItem> conjugations;

  @Data @Builder
  public static class DeclensionItem {
    private final String number;
    private final String caseName;
    private final String gender;
    private final String form;
  }

  @Data @Builder
  public static class ConjugationItem {
    private final String tense;
    private final String person;
    private final String number;
    private final String form;
  }
}
