package today.learnslovak.first.presentation.ui.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;
import today.learnslovak.first.R;
import today.learnslovak.first.presentation.ui.common.toolbar.SlovaToolbar;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings_activity);
    SlovaToolbar slovaToolbar =
        SlovaToolbar.builder().toolbar(findViewById(R.id.toolbar)).baseActivity(this).build();
    slovaToolbar.initWithHomeAsUp();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.settings_fragment, new SettingsFragment())
        .commit();
  }

  //todo override, if return to the real calling activity instead of StartActivity needed
/*@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            //NavUtils.navigateUpFromSameTask(activity);
            //activity.onBackPressed();
            //startActivity(new Intent(this, MainActivity.class)); //when set this - NPE with same name observer SpeechStatus
            return true;


        //important to return false for menu Home arrow correct work
        default:
            return false;
    }}*/
}