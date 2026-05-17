package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.learnslovak.first.data.common.util.DataConfig;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Entity(tableName = DataConfig.TABLE_NAME_MAIN)
public class WordDb {

  @PrimaryKey @Json(name = "id")
  private int id;
  private String sk;
  private String en;
  private String ru;
  private String uk;
  @Json(name = "snippet_ids")
  private String snippetIds;

  private String partOfSpeech;

  private List<DeclensionItem> declensions;

  private List<ConjugationItem> conjugations;

  @Data @NoArgsConstructor @AllArgsConstructor @Builder
  public static class DeclensionItem {
    private String number;
    @Json(name = "case_")
    private String caseName;
    private String gender;
    private String form;
  }

  @Data @NoArgsConstructor @AllArgsConstructor @Builder
  public static class ConjugationItem {
    private String tense;
    private String person;
    private String number;
    private String form;
  }
}
