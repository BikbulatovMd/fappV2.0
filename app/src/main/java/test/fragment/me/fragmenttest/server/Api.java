package test.fragment.me.fragmenttest.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import test.fragment.me.fragmenttest.model.AccessToken;
import test.fragment.me.fragmenttest.model.GitHubList;
import test.fragment.me.fragmenttest.model.User;

public interface Api {

    //Интерфейс который описывает наши запросы на сервер

    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);

    //Метод для получения юзера по токену
    @GET("/user")
    Call<User> getCurrentUser();

    //метод для получения репозиторие пользователя
    @GET("/users/{user}/repos")
    Call<List<GitHubList>> getReposForUser(@Path("user") String user);

    //Метод для сброса авторизации
    @POST("/applications/{clientId}/tokens/{token}")
    Call<String> logOut(@Path("clientId") String clientId,
                        @Path("token") String token);

}