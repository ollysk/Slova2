package today.learnslovak.first.presentation.html;

import java.util.List;
import today.learnslovak.first.domain.model.Lang;
import today.learnslovak.first.domain.model.Snippet;
import today.learnslovak.first.domain.model.Word;

public interface HtmlSnippetProvider extends HtmlProvider {

  String getHtml(Word word, List<Snippet> snippets, String searchQuery, Lang searchLang);

  String getHtml(Word word, List<Snippet> snippets);
/*    default String withCss(String css, String html) {
        return "<style>"+css+"</style>" + html;
    }*/
}
