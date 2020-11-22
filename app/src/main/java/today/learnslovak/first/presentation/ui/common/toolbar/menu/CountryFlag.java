package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import today.learnslovak.first.domain.model.Lang;

public class CountryFlag {
  private final List<Lang> langs = Arrays.asList(Lang.values());

  private final Map<Lang, String> flags = new HashMap<Lang, String>() {{
    put(Lang.SK, "\uD83C\uDDF8\uD83C\uDDF0");
    put(Lang.EN, "\uD83C\uDDEC\uD83C\uDDE7");
    put(Lang.RU, "\uD83C\uDDF7\uD83C\uDDFA");
    put(Lang.UK, "\uD83C\uDDFA\uD83C\uDDE6");
  }};

  @Inject public CountryFlag() {

  }

  public String get(Lang lang) {
    return flags.getOrDefault(lang, "\uD83C\uDDF8\uD83C\uDDF0");
  }

  //int iterator1=-1;
  public Map<Lang, String> getNext(Lang currentSearchLang) {

    int iterator1 = langs.indexOf(currentSearchLang);
    iterator1++;

    if (iterator1 >= langs.size()) {
      iterator1 = 0;
    }
    return Collections.singletonMap(langs.get(iterator1), flags.get(langs.get(iterator1)));
    //return flags.get(prefLangs.get(iterator1));
  }
}
