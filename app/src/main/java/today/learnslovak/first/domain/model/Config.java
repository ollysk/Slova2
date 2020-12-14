package today.learnslovak.first.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Config {

  private final String dataServerUrl;
  private final String secondaryRootServerUrl;
  private final String wordJsonFilename;
  private final String snippetJsonFilename;
  private final int wordPatchLevel;
  private final int snippetPatchLevel;
}
