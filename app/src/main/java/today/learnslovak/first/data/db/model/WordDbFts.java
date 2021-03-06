package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.Fts4;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.learnslovak.first.data.common.util.DataConfig;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Fts4(contentEntity = WordDb.class)
@Entity(tableName = DataConfig.TABLE_NAME_MAIN_FTS)
public class WordDbFts {
  /*    @PrimaryKey
      @ColumnInfo(name = "rowid")
      private int rowId;*/
  private int id;
  private String sk;
  private String en;
  private String ru;
  private String uk;
}
