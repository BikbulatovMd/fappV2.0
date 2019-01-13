package test.fragment.me.fragmenttest.utils;

import android.app.Application;
import android.content.SharedPreferences;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.transport.TransportFactory;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.transport.TransportFactory;

import test.fragment.me.fragmenttest.R;
import test.fragment.me.fragmenttest.server.NetClient;

public class App extends Application {


    // номер приложения на гите
    private static final String APP_ID = "965646";
    //Класс для управления нетклиентом
    private static final String AUTH_URL = "https://github.com/";
    private static final String BASE_URL = "https://api.github.com/";

    //ClientId приложения на гите
    private static final String clientId = "a073d30abda203764e62";
    //ClientSecret приложения на гите
    private static final String clientSecret = "06275a6adf1fbc9bc5b615ac6661c057d5e0e3af";

    //CallBack для окончания авторизации
    private static final String redirectUri = "test.fragment.me.fragmenttest://callback";
    private static String USERNAME;
    private static NetClient netClient;
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        //проводим инициализацию карты
        MapKitFactory.setApiKey("9170c631-f793-4739-94cc-72d1d2add41d");
        MapKitFactory.initialize(this);
        TransportFactory.initialize(this);

        sharedPreferences = getSharedPreferences(String.valueOf(R.string.prefs_name), MODE_PRIVATE);
        USERNAME = sharedPreferences.getString(String.valueOf(R.string.username), null);
        if (sharedPreferences.getString(String.valueOf(R.string.token), null) != null) {
            setBaseNetClient();
        } else {
            setAuthNetClient();
        }

        //Вообще все важные инициализации надо проводить тут - где инициализация идет через статик методы
    }

    public static NetClient getNetClient() {
        return netClient;
    }

    //Мы будем использовать этот нетклиент для авторизации
    public static void setAuthNetClient() {
        netClient = new NetClient(AUTH_URL, null);
    }

    //А этот нетклиент для отправки запрос к апи
    public static void setBaseNetClient() {
        netClient = new NetClient(BASE_URL, getAccessToken());
    }

    //Метод сохранения токена на устройстве
    public static void setAccessToken(String token) {
        sharedPreferences.edit().putString(String.valueOf(R.string.token), token).apply();
    }

    //Метод получения токена с устройства
    public static String getAccessToken() {
        return sharedPreferences.getString(String.valueOf(R.string.token), null);
    }

    //Метод очистки токена на устройстве
    public static void clearAccessToken() {
        sharedPreferences.edit().putString(String.valueOf(R.string.token), null).apply();
    }

    //Метод для очистки пользователя
    public static void clearUserName() {
        sharedPreferences.edit().putString(String.valueOf(R.string.username), null).apply();
    }

    public static String getClientId() {
        return clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getRedirectUri() {
        return redirectUri;
    }

    //Метод получения
    public static String getUsername() {
        return USERNAME;
    }

    public static void setUsername(String username) {
        USERNAME = username;
        sharedPreferences.edit().putString(String.valueOf(R.string.username), username).apply();
    }

    public static String getAppId() {
        return APP_ID;
    }
}