package Database;

import androidx.room.TypeConverter;

import com.example.laserchesstest.Position;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromPositionArray(Position[][] board) {
        return gson.toJson(board);
    }

    @TypeConverter
    public static Position[][] toPositionArray(String boardJson) {
        Type type = new TypeToken<Position[][]>() {}.getType();
        return gson.fromJson(boardJson, type);
    }
}
