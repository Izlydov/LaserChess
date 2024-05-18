package Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {GameBoard.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class GameBoardDatabase extends RoomDatabase{
    public abstract GameBoardDao getGameBoardDao();
}
