package today.learnslovak.first.presentation.ui.quiz;

import java.util.regex.Matcher;
import javax.inject.Inject;
import timber.log.Timber;
import today.learnslovak.first.data.common.util.AssetsUtil;

import static today.learnslovak.first.presentation.ui.quiz.StringAlphabetUtil.DiacriticsMode;
import static today.learnslovak.first.presentation.ui.quiz.StringAlphabetUtil.hasAlphabetic;
import static today.learnslovak.first.presentation.ui.quiz.StringAlphabetUtil.stripDiacritics;
import static today.learnslovak.first.presentation.ui.quiz.StringAlphabetUtil.stripNonAlphabetic;

public class TextQuiz {

  private final AssetsUtil assetsUtil;
  private StringBuilder jsDataElement = new StringBuilder();
  private StringBuilder jsData = new StringBuilder();
  private int cnt = 0;

  @Inject public TextQuiz(AssetsUtil assetsUtil) {
    this.assetsUtil = assetsUtil;
  }

  public int getVariant() {
    return 2;
  }

  //Heads up! On android API 19 JS single line comments "//" and absence of ";" breaks WebView.
  //In API 25,29 everything is ok.

  public String wrap(String text) {
    //wrapOne, wrapTwo, wrapN - for future Quizes
    String res = "";
    switch (getVariant()) {
      case 1: {
        res = wrapOne(text, DiacriticsMode.STRIP_ALL);
        break;
      }
      case 2: {
        res = wrapOne(text, DiacriticsMode.STRIP_VOWELS_ONLY);
        break;
      }
    }
    return res;
  }

  public String consumeJsBody() {
    String jsCode = assetsUtil.getFromAssetsEscaped("html/score.html");
    String res = getJsArray() + jsCode;
    clean();
    return res;
  }

  public void clean() {
    cnt = 0;
    jsData = new StringBuilder();
  }

  public void addToJsData(String s, String asciiStr) {
    String strippedStr = stripNonAlphabetic(s);

    jsDataElement.append(cnt)
        .append(",'")
        .append(decorateMistakes(asciiStr, s))
        .append("',")
        .append("'")
        .append(strippedStr)
        .append("',")
        .append(s.equals(asciiStr))
        .append("],[");
  }

  public String wrapOne(String text, DiacriticsMode diacriticsMode) {
    jsDataElement = new StringBuilder();

    boolean isTrue = false;
    String asciiStr;
    String escaped;
    //int cnt = 0;
    String res;
    //  <span id="id1" name="true" onclick="myFunction(1,'ClicK','true')">Click</span>
    //"[^\\p{ASCII}]"
    //pattern compile gains 3X better performance on 10M iterations

    Matcher matcher;
    StringBuilder sb = new StringBuilder();

    for (String s : text.split(" ")) {

      String s2 = stripNonAlphabetic(s);

      // & evaluates all conditions in if, but && short circuits once the result of the condition is determined. Same for | and ||
      if (s.length() > 2 && hasAlphabetic(s)) {
                /*
                if (!s.equals(s2)) {
                    System.out.println("MTCHER2:" + s + "/" + s2);
                }
                */
        cnt++;
        // escaped = s.replaceAll("'","\'");
        //s = s.replaceAll(Matcher.quoteReplacement("\\'"), "'").trim();
        // s = s.replaceAll("'", "**").trim();

        //s = s.replaceAll("'", "&apos;").trim();
        asciiStr = stripDiacritics(s, "", diacriticsMode);

        sb.append("<span id=")
            .append(cnt)
            .append(" onClick=\"onWordClick(")
            .append(cnt)
            .append(")\">")
            .append(asciiStr)
            .append("</span>");
        //.append("");

        addToJsData(s, asciiStr);
      } else {
        sb.append("<span class='pale'>").append(s).append("</span>");
      }
      sb.append(" ");
    }
    jsData.append(jsDataElement);
    return sb.toString();
  }

  private String getJsArray() {
    return "<script>var words = [\n[], [" + jsData + "]];</script>";
  }

  String decorateMistakes(String input, String reference) {
    if (input.equals(reference)) {
      return input;
    }
    int i = 0;
    StringBuilder res = new StringBuilder();
    //Heads up! str.split("") to split by characters produces one empty element at start (in pure Java everything was ok), toCharArray works fine
    for (Character c : input.toCharArray()) {
      try {
        if (!c.equals(reference.charAt(i))) {
          res.append("<u0><b>").append(reference.charAt(i)).append("</b></u0>");
        } else {
          res.append(reference.charAt(i));
        }
        ++i;
      } catch (Exception e) {
        Timber.e(e);
      }
    }
    return res.toString();
  }
}
