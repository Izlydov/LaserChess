package Database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LaserWayConverter {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Object[].class, new ObjectArrayDeserializer())
                .create();
    }

    public static String fromLaserWay(ArrayList<Object[]> laserWay) {
        return gson.toJson(laserWay);
    }

    public static ArrayList<Object[]> toLaserWay(String laserWayJson) {
        Type type = new TypeToken<ArrayList<Object[]>>() {}.getType();
        return gson.fromJson(laserWayJson, type);
    }
}
