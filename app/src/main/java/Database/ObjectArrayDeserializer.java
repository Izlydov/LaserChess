package Database;

import com.example.laserchesstest.CellResult;
import com.example.laserchesstest.Coordinates;
import com.google.gson.*;
import java.lang.reflect.Type;

public class ObjectArrayDeserializer implements JsonDeserializer<Object[]> {
    @Override
    public Object[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        Object[] objects = new Object[jsonArray.size()];

        objects[0] = context.deserialize(jsonArray.get(0), CellResult.class);
        objects[1] = context.deserialize(jsonArray.get(1), Coordinates.class);
        objects[2] = jsonArray.get(2).getAsInt();
        objects[3] = jsonArray.get(3).getAsInt();

        return objects;
    }
}
