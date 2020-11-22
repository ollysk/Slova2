package today.learnslovak.first.presentation.common;

import android.content.Context;
import android.content.res.Configuration;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.Locale;
import javax.inject.Inject;

public class AppLocale {

  private final Context context;

  @Inject public AppLocale(@ApplicationContext Context context) {
    this.context = context;
  }

  public void setLocale() {
    setLocale(context);
  }

  public void setLocale(Context context) {
    Locale locale = new Locale("ru");
    Configuration configuration = context.getResources().getConfiguration();
    configuration.setLocale(locale);
    Locale.setDefault(locale);

    context.getResources()
        .updateConfiguration(configuration, context.getResources().getDisplayMetrics());
  }
}
