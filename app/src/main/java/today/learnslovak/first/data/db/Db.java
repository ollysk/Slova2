package today.learnslovak.first.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import today.learnslovak.first.data.db.dao.SnippetDao;
import today.learnslovak.first.data.db.dao.WordDao;
import today.learnslovak.first.data.db.model.SkipDb;
import today.learnslovak.first.data.db.model.SnippetDb;
import today.learnslovak.first.data.db.model.WordDb;
import today.learnslovak.first.data.db.model.WordDbAscii;
import today.learnslovak.first.data.db.model.WordDbFts;

@Database(entities = {
    WordDb.class, WordDbFts.class, WordDbAscii.class, SnippetDb.class, SkipDb.class
},
    version = 1, exportSchema = false)
@TypeConverters({ Converters.class })
public abstract class Db extends RoomDatabase {

  public Db() {

  }

  public abstract WordDao wordDao();

  public abstract SnippetDao snippetDao();
}
