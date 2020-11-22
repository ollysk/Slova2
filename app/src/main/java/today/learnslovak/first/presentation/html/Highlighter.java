package today.learnslovak.first.presentation.html;

import java.util.regex.Pattern;
import lombok.Builder;
import today.learnslovak.first.domain.model.Lang;

@Builder
public class Highlighter {
  private final String searchQuery;
  private final Lang searchLang;

  private boolean shouldApply(Lang searchLang, Lang textLang) {
    return !searchQuery.isEmpty() && searchLang.equals(textLang);
  }

  /*
   low priority refactor later: replaceAll (?i) searches case insensitive,
   So, if searchQuery equals "WORD" all results will be in uppercase also, despite source
   */
  public String apply(String text, Lang textLang) {
    return shouldApply(searchLang, textLang) ? text.replaceAll("(?i)" + Pattern.quote(searchQuery),
        "<b0><span style='background-color: %23FFFF00'>" + searchQuery + "</span></b0>") : text;
  }
}
