package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.squareup.moshi.Json;
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
}
