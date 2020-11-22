package today.learnslovak.first.presentation.ui.words;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;
import today.learnslovak.first.databinding.FragmentItemListBinding;

@AndroidEntryPoint
public class RecyclerViewFragment extends Fragment implements RecyclerViewClickListener {

  private WordsViewModel viewModel;
  private LinearLayoutManager layoutManager;
  private RecyclerView recyclerView;
  private WordsRecyclerViewAdapter adapter;
  private FragmentItemListBinding binding;

  public RecyclerViewFragment() {
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    //important requireActivity(), not "this", to share viewmodel with parent activity
    viewModel = new ViewModelProvider(requireActivity()).get(WordsViewModel.class);
    binding = FragmentItemListBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    //View view = inflater.inflate(R.layout.fragment_item_list, container, false);

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      recyclerView = (RecyclerView) view;
      recyclerView.setHasFixedSize(true);
      recyclerView.addItemDecoration(
          new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
      layoutManager = new LinearLayoutManager(context);
      recyclerView.setLayoutManager(layoutManager);
    }
    return view;
  }

  public void setUpAdapter() {
    adapter = new WordsRecyclerViewAdapter(viewModel.getWords(), this);
    recyclerView.setAdapter(adapter);
  }

  /*
    I believe this method is obsolete. Instead of fixed postDelayed and artificial hacky ScrollToPosition
    this method is replaced by method that sit in observable words() and fires  adapter.notifyDataSetChanged() when getWords is empty
    Fixes an issue where WordsActivity is started right away after app start and RecyclerView is empty before first click (happened in ~20% cases).
    Cause is probably app init is not fully completed before RecyclerView starts (database, etc)
   */

 /*
    private void runWorkaroundForRecyclerViewInitialStateIsBlank() {
        recyclerView.postDelayed(() -> layoutManager.scrollToPositionWithOffset(0,0),200);
    }
 */

  /**
   * Needed for first load, to notify that initial data is ready
   * in ~90% situations adapter inited with empty data
   * Think how to refactor this situation to set adapter After initial data is ready, without
   * breaking existing code (showOnTop(), for example)
   **/

  private void adapterInitIfEmpty() {
    if (adapter.getItemCount() == 0) {
      adapter.notifyDataSetChanged();
      //scrollToPosition is needed for correct gotoStart() positioning
      layoutManager.scrollToPosition(0);
    }
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel.init();
    setUpAdapter();
    viewModel.words().observe(getViewLifecycleOwner(), (words) -> {
          adapterInitIfEmpty();
          viewModel.addWords(words);
        }

    );
    viewModel.clickPosition().observe(getViewLifecycleOwner(), this::showOnTop);

/*        viewModel.refreshAdapter.observe(getViewLifecycleOwner(), str -> {
            activateAdapter();
        });*/
  }

  void showOnTop(int currentPosition) {
    layoutManager.scrollToPositionWithOffset(currentPosition, 0);
    makeItemVisible(currentPosition);
  }

  public void makeItemVisible(int position) {
    viewModel.getItem(position).setTransVisible(true);
    adapter.notifyItemChanged(position);
  }

  @Override public void onPause() {
    super.onPause();
    //binding = null;

    //viewModel.saveWordsId();
    //viewModel.setPosition(0);
  }

  @Override public void onRecyclerViewItemClick(int position) {
    //viewModel.setClickPosition(position);
    makeItemVisible(position);
    viewModel.nullifyClickOnItemCount();
    viewModel.onItemClick(position);
  }
}