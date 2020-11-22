package today.learnslovak.first.presentation.common.tts;

import android.text.TextUtils;
import javax.inject.Inject;
import today.learnslovak.first.domain.model.Lang;

public class TtsServiceUtil {

  @Inject public TtsServiceUtil() {
  }

  public String prepareTextForJS(String text) {
    return text.replace("\"", " ")
        .replace("'", " ")
        .replace("(", " ")
        .replace(")", " ")
        .replace("\n", " ")
        .replaceAll("<(.*?)>", " ");
  }

  public String jsTts(String text, Lang lang) {

    if (TextUtils.isEmpty(text)) {
      return "";
    }
    text = prepareTextForJS(text);
    //<input type="button" value="&#128265;"
    return "<span onClick=\"jsSpeak('"
        + lang.name()
        + "','"
        + text
        + "')\" />&%23128265;</span><script type='text/javascript'>function jsSpeak(lang,text) {Android.speak(lang,text,0); }</script>";
  }
}
