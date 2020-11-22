package today.learnslovak.first.presentation.ui.common.toolbar.menu;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.view.Menu;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import java.util.List;
import lombok.Data;
import today.learnslovak.first.R;
import today.learnslovak.first.domain.model.Word;

@Data(staticConstructor = "of")
public class MenuSearchView {

  private final Menu menu;
  private final MenuActions suggestions;
  private final MenuSearch search;
  private final Context context;

  private SearchView searchView;
  private SuggestionsAdapter adapter;

  public MenuSearchView(Menu menu, MenuActions suggestions, MenuSearch search, Context context) {
    this.menu = menu;
    this.suggestions = suggestions;
    this.search = search;
    this.context = context;
  }

  public void init() {
    setSuggestionsObserver();
    setSearchViewWithAdapter();
    setOnSuggestionListener();
    setOnQueryTextListener();
    setOnQueryTextFocusChangeListener();
  }

  private void setSuggestionsObserver() {
    suggestions.suggestions().observe((LifecycleOwner) context, this::suggestionsUpdate);
  }

  private void setSearchViewWithAdapter() {
    setSearchView();
    setSuggestionsAdapter();
  }

  private void setSearchView() {
    menu.findItem(R.id.app_bar_search).setVisible(true);
    SearchManager searchManager = (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
    searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
    searchView.setSearchableInfo(
        searchManager.getSearchableInfo(((Activity) context).getComponentName()));
    searchView.setMaxWidth(searchView.getMaxWidth() - 200);
  }

  private void setSuggestionsAdapter() {
    if (adapter == null) {
      adapter = new SuggestionsAdapter(context, null);
      searchView.setSuggestionsAdapter(adapter);
    }
  }

  private void setOnSuggestionListener() {
    searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
      @Override public boolean onSuggestionSelect(int position) {
        return true;
      }

      @Override public boolean onSuggestionClick(int position) {
        int id = (int) adapter.getItemId(position);
        search.setTempWordId(id);
        searchView.clearFocus();
        return true;
      }
    });
  }

  private void setOnQueryTextListener() {
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        search.setSearchQuery(query);
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        if (newText.length() > 1) {

          clearOldCursor();
          suggestions.setSuggestionsQuery(newText);
        }
        return true;
      }
    });
  }

  /**
   * To prevent showing old suggestions when query is already updated but queryResult is still in
   * process.
   **/
  void clearOldCursor() {
    if (adapter != null) {
      adapter.changeCursor(null);
      //suggestionsAdapter.notifyDataSetChanged();
    }
  }

  private void setOnQueryTextFocusChangeListener() {
    //Android emulator's strange behaviour: When input search string in searchview and click <Enter> instead of soft keyboard search button, search starts two times (instead of one)
    searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {

      } else {
        clearSearchView();
      }
    });
  }

  private void clearSearchView() {
    searchView.setQuery("", false);
    searchView.clearFocus();
    searchView.setIconified(true);
  }

  public void suggestionsUpdate(List<Word> suggestions) {
    Cursor cursor = WordCursorMapper.toCursor(suggestions);
    adapter.swapCursor(cursor);
  }
}
