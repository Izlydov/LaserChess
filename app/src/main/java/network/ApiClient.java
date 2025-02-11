package network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Серверная часть находится в доработке.
// Весь код сервера на Spring boot здесь https://github.com/Izlydov/LaserChessServer

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.107:8080";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
