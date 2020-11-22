package today.learnslovak.first.presentation.common.tts;

import android.content.Context;
import today.learnslovak.first.R;
import today.learnslovak.first.domain.model.Lang;

public interface TtsCallback {
  default void onTtsLangMissingData(Lang lang, Context context) {
    String alert = context.getString(R.string.tts_lang_missing, lang.name());
    ///Toast.makeText(context, alert, Toast.LENGTH_SHORT).show();
  }

  default void onTtsLangNotSupported(Lang lang, Context context) {
  }
}
