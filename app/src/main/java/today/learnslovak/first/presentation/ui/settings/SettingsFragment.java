package today.learnslovak.first.presentation.ui.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import today.learnslovak.first.R;

public class SettingsFragment extends PreferenceFragmentCompat {
  @Override public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey);
  }
}
