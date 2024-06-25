package network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// К сожалению у меня нет возможности развернуть сервер на хостинг
// Весь код сервера на Spring boot здесь https://github.com/Izlydov/LaserChessServer
// Разкомментируйте кнопку onlineButton в MenuActivity для игры по локальной сети, если вы запустите сервер

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.106:8080"; // Сюда нужно ввести ваш айпи, если вы запустите сервер с https://github.com/Izlydov/LaserChessServer
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
