package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import today.learnslovak.first.data.common.util.DataConfig;

@Data @NoArgsConstructor
/*
After gradle update from room-compiler:2.3.0-alpha02 to alpha03, @Builder annotation started causing compilation error:
"javax.lang.model.element.UnknownElementException: Unknown element"
Had to switch to experimental @SuperBuilder to fix the problem
Good side - @AllArgsConstructor is no longer needed for Builder pattern to work
TODO check again after room gradle next upgrade if it is possible to switch back to non-experimental @Builder
@AllArgsConstructor
@Builder */
@SuperBuilder
@Entity(tableName = DataConfig.TABLE_NAME_MAIN)
public class WordDb {

  @PrimaryKey @SerializedName("id")
  private int id;
  private String sk;
  private String en;
  private String ru;
  private String uk;
  @SerializedName("snippet_ids")
  private String snippetIds;
}
