package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import today.learnslovak.first.data.common.util.DataConfig;

@Data @NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
@Entity(tableName = DataConfig.TABLE_NAME_SNIPPET)
public class SnippetDb {

  @PrimaryKey @SerializedName("sid")
  private int id;
  private String sk;
  private String en;
  private String ru;
  private String uk;
}
