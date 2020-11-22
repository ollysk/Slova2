package today.learnslovak.first.presentation.ui.words;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import today.learnslovak.first.R;
import today.learnslovak.first.databinding.ActivityWordsBinding;
import today.learnslovak.first.presentation.ui.common.BaseActivity;

@AndroidEntryPoint
public class WordsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

  private ActivityWordsBinding binding;
  private WordsViewModel viewModel;
  private Spinner wordsMode;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    wordsMode = binding.wordsMode;
    wordsMode.setOnItemSelectedListener(this);
    wordsMode.setSelection(viewModel.getPrefWordsMode().id());
  }

  @Override public void setUpDataBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_words);
  }

  @Override public void setUpViewModel() {
    super.setUpViewModel();
    viewModel = new ViewModelProvider(this).get(WordsViewModel.class);
  }

  @Override public void setUpDataBindingVariables() {
    binding.setViewModel(viewModel);
    //without this line LiveData will not be updated
    binding.setLifecycleOwner(this);
    setCustomFabSize(binding.fab1, viewModel.getPrefFabSize());
  }

  @Override protected void onStart() {
    super.onStart();
  }

  @Override public void setSkipWordId() {
    viewModel.setSkipFromWords();
  }

  private Mode getWordsMode() {
    return Mode.get((int) wordsMode.getSelectedItemId());
  }

  @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    viewModel.setClickMode(getWordsMode());
    viewModel.setPrefWordsMode(getWordsMode());
  }

  @Override public void onNothingSelected(AdapterView<?> parent) {

  }

  //menu
  @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.menu_start_again) {
      viewModel.gotoStart();
    }
    return super.onOptionsItemSelected(item);
  }
}