package today.learnslovak.first.presentation.ui.quiz;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class StringAlphabetUtil {

  //at least one alphabetic character
  final static Pattern pAlpha = Pattern.compile(".*[\\p{Alpha}]+.*");
  final static Pattern pAlphaExt = Pattern.compile("[^-\\s\\p{Alnum}]+");

  private StringAlphabetUtil() {
  }

  public static boolean hasAlphabetic(String text) {
    return pAlpha.matcher(text).matches();
  }

  private static String stripDiacritics(String text) {
    return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
  }

  public static String stripDiacritics(String text, String replaceWith, DiacriticsMode mode) {
    String res;
    switch (mode) {
      case STRIP_ALL:
        res = stripDiacritics(text, replaceWith);
        break;
      case STRIP_VOWELS_ONLY:
        res = stripDiacriticsFromVowels(text, replaceWith);
        break;
      case NO_STRIP:
        res = text;
        break;
      default:
        res = text;
    }
    return res;
  }

  private static String stripDiacritics(String text, String replaceWith) {

    String regex = "[^\\p{ASCII}]";
    return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll(regex, replaceWith);
  }

  private static String stripDiacriticsFromVowels(String text, String replaceWith) {

  /*      String regex = regex = "(?i)[äáéíóôúý]"; //(?i) - case insensitive
        //res = text.replaceAll(regex, replaceWith);*/
    return text.replaceAll("[äá]", "a").
        replaceAll("[é]", "e").
        replaceAll("[í]", "i").
        replaceAll("[óô]", "o").
        replaceAll("[ú]", "u").
        replaceAll("[ý]", "y").
        replaceAll("[ÁÄ]", "A").
        replaceAll("[É]", "E").
        replaceAll("[Í]", "I").
        replaceAll("[ÓÔ]", "O").
        replaceAll("[Ú]", "U").
        replaceAll("[Ý]", "Y");
  }

  public static String stripNonAlphabetic(String text) {
    return pAlphaExt.matcher(text).replaceAll("");
  }

  public static boolean hasDiacritics(String text) {
    return !Normalizer.isNormalized(text, Normalizer.Form.NFD); //inverted boolean
  }

  public static boolean hasCyrillic(String text) {
    return Pattern.matches(".*\\p{InCyrillic}.*", text);
  }

  public enum DiacriticsMode {
    STRIP_ALL, STRIP_VOWELS_ONLY, NO_STRIP
  }
}
