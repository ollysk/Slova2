package today.learnslovak.first.presentation.ui.start;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;
import today.learnslovak.first.R;

@AndroidEntryPoint
public class SplashActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppThemeSplashScreen);
    super.onCreate(savedInstanceState);
    startActivity(new Intent(SplashActivity.this, StartActivity.class));
    finish();
  }
}
