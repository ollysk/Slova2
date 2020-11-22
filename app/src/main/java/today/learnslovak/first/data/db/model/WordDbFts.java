package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.Fts4;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import today.learnslovak.first.data.common.util.DataConfig;

@Data @NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
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
