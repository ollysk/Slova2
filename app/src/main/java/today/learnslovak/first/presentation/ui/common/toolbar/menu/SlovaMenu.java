package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import java.util.Map;
import lombok.Builder;
import today.learnslovak.first.R;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.presentation.ui.common.BaseActivity;
import today.learnslovak.first.presentation.ui.settings.SettingsActivity;

@Builder
public class SlovaMenu {

  private final BaseActivity baseActivity;
  //private final MenuActions menuActions;
  private final MenuActions actions;
  private final MenuSearch search;
  private final CountryFlag countryFlag;

/*  public SlovaMenu(BaseActivity activity, MenuActions menuActions, MenuSearch search,
      CountryFlag countryFlag) {
    this.baseActivity = activity;
    this.actions = menuActions;
    this.search = search;
    this.countryFlag = countryFlag;
  }*/

  public boolean onCreateOptionsMenu(Menu menu) {

    baseActivity.getMenuInflater().inflate(R.menu.menu_main, menu);

    MenuItem item =
        menu.add(Menu.FIRST, R.id.menu_flag, 0, countryFlag.get(actions.getSearchLang()));
    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    //item.setIcon(R.drawable.ic_info_black_24dp);
    initSearchView(menu);

    return true;
  }

  private void initSearchView(Menu menu) {
    MenuSearchView.of(menu, actions, search, baseActivity).init();
  }

  public void navigateUp() {
    final Intent upIntent = NavUtils.getParentActivityIntent(baseActivity);

    if (NavUtils.shouldUpRecreateTask(baseActivity, upIntent)) {

      TaskStackBuilder.create(baseActivity)
          .addNextIntentWithParentStack(upIntent)
          .startActivities();
    } else {
      NavUtils.navigateUpTo(baseActivity, upIntent);
    }
  }

  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == R.id.home) {

      goUpIfPossibleOrGoBack();
    } else if (id == R.id.menu_settings) {
      Intent intent = new Intent(baseActivity, SettingsActivity.class);
      // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      baseActivity.startActivity(intent);
    } else if (id == R.id.menu_flag) {
      setSearchLangAndCountryFlag(item);
    } else if (id == R.id.menu_remove_word) {

      baseActivity.setSkipWordId();
    }
    return false;
  }

  private void setSearchLangAndCountryFlag(MenuItem item) {
    getNextCountryFlag().forEach((k, v) -> {
      actions.setSearchLang(k);
      item.setTitle(v);
    });
  }

  private Map<Lang, String> getNextCountryFlag() {
    return countryFlag.getNext(actions.getSearchLang());
  }

  private void goUpIfPossibleOrGoBack() {

    if (hasParentActivityInManifest()) {
      NavUtils.navigateUpFromSameTask(baseActivity);
    } else {
      baseActivity.onBackPressed();
    }
  }

  private boolean hasParentActivityInManifest() {
    return NavUtils.getParentActivityIntent(baseActivity) != null;
  }
}
