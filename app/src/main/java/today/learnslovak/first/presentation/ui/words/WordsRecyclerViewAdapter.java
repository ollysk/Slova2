package today.learnslovak.first.presentation.ui.words;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import today.learnslovak.first.R;
import today.learnslovak.first.databinding.WordsRecyclerviewRowBinding;
import today.learnslovak.first.domain.model.Word;

public class WordsRecyclerViewAdapter
    extends RecyclerView.Adapter<WordsRecyclerViewAdapter.ViewHolder> {

  private final List<Word> words;
  private final RecyclerViewClickListener clickListener;

  public WordsRecyclerViewAdapter(List<Word> words, RecyclerViewClickListener clickListener) {
    this.words = words;
    this.clickListener = clickListener;
  }

  @NonNull
  @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    WordsRecyclerviewRowBinding binding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.words_recyclerview_row, parent, false);

    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    Word word = words.get(position);
    holder.bind(word);
    holder.binding.setItemClickListener(clickListener);
    holder.itemView.setTag(word.getId());
  }

  @Override public int getItemCount() {
    return words.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    WordsRecyclerviewRowBinding binding;

    ViewHolder(WordsRecyclerviewRowBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Word word) {
      binding.setWord(word);
      binding.setPosition(getBindingAdapterPosition());
      //binding.setWord(word);
      binding.executePendingBindings();
    }

    @Override public void onClick(View view) {

    }
  }
}
