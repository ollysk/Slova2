package today.learnslovak.first.presentation.html;

public interface HtmlProvider {

  default String getHtml() {
    return "";
  }

  default String getHeader(String css) {
    return "<html><head><style>" + css + "</style></head><body>";
  }

  default String getFooter() {
    return "</body><html>";
  }

  default void setCss(String css) {
  }
}
