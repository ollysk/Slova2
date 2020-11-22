package today.learnslovak.first.presentation.ui.words;

import java.util.stream.Stream;
import today.learnslovak.first.domain.model.Preferences;

public enum Mode implements Preferences {

  SEMIAUTO(0), MANUAL(1), AUTO(2), SILENT(3);

  private final int id;

  Mode(int id) {
    this.id = id;
  }

  public static Mode get(int id) {
    return Stream.of(Mode.values()).filter(mode -> mode.id == id).findFirst().orElse(Mode.SILENT);
  }

  public int id() {
    return id;
  }
}
