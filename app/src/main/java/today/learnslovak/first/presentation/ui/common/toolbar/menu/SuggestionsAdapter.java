package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.databinding.DataBindingUtil;
import today.learnslovak.first.R;
import today.learnslovak.first.databinding.SuggestionsRowBinding;
import today.learnslovak.first.domain.model.Word;

public class SuggestionsAdapter extends CursorAdapter {
  private Cursor cursor;

  public SuggestionsAdapter(Context context, Cursor cursor) {
    super(context, cursor, 0);
  }

  @Override public View newView(Context context, Cursor cursor, ViewGroup parent) {
    SuggestionsRowBinding binding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.suggestions_row,
            parent, false);

    View view = binding.getRoot();
    ViewHolder viewHolder = new ViewHolder(binding);
    view.setTag(viewHolder);

    return view;
  }

  @Override public void bindView(View view, Context context, Cursor cursor) {
    ViewHolder viewHolder = (ViewHolder) view.getTag();
    viewHolder.bind(WordCursorMapper.toWord(cursor));
  }

  @Override public long getItemId(int position) {
    Cursor cursor = getCursor();
    cursor.moveToPosition(position);
    return cursor.getInt(cursor.getColumnIndex("_id"));
  }

  static class ViewHolder {
    SuggestionsRowBinding binding;

    ViewHolder(SuggestionsRowBinding binding) {
      this.binding = binding;
    }

    public void bind(Word word) {
      binding.setWord(word);
    }
  }
}
