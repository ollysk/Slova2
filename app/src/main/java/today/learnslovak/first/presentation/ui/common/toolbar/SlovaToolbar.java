package today.learnslovak.first.presentation.ui.common.toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import lombok.Builder;

@Builder
public class SlovaToolbar {

  private final Toolbar toolbar;
  private final AppCompatActivity baseActivity;

  public void init() {
    baseActivity.setSupportActionBar(toolbar);
  }

  public void initWithHomeAsUp() {
    init();
    ActionBar actionBar = baseActivity.getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowHomeEnabled(true);
    }
  }
}
