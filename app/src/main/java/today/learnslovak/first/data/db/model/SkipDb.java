package today.learnslovak.first.data.db.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import today.learnslovak.first.data.common.util.DataConfig;
import today.learnslovak.first.domain.model.Lang;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Entity(tableName = DataConfig.TABLE_NAME_SKIP, indices = {
    @Index(value = { "id", "lang" }, unique = true)
})
public class SkipDb {

  @PrimaryKey(autoGenerate = true)
  private int aid;
  private int id;
  private Lang lang;
}
