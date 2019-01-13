package test.fragment.me.fragmenttest.activities;


import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.util.Objects;

import test.fragment.me.fragmenttest.model.AccessToken;
import test.fragment.me.fragmenttest.model.User;
import test.fragment.me.fragmenttest.utils.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.fragment.me.fragmenttest.R;

public class ActivityVhod extends AppCompatActivity {

    Button btnSignIn;

    private String LOG = "MyLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhod);

        btnSignIn = findViewById(R.id.btn_auth);

        //меняем цвет тулбара на нужный
        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(bar).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

        //меняем цвет статус бара на нужны
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorGrey), 0);

        Log.d(LOG, "onCreate: THATS ME");
    }

    public void clickSignIn (View view) {
        signIn();
    }

    //метод для входа в акк
    private void signIn() {
        String myUrlGit = "https://github.com/login/oauth/authorize?client_id=" + App.getClientId() +
                "&scope=repo&redirect_uri=" + App.getRedirectUri();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrlGit));
        startActivity(intent);

        Log.d(LOG, "signIn: im working");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(App.getRedirectUri())) {

            String code = uri.getQueryParameter("code");

            App.getNetClient().getAccessToken(App.getClientId(), App.getClientSecret(), code, new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Токен = " + response.body().getAccessToken(), Toast.LENGTH_LONG).show();
                        App.setAccessToken(response.body().getAccessToken());
                        App.setBaseNetClient();
                        getUserName();
                    } else {
                        Log.d(LOG, "Код ошибки = " + response.code());
                        try {
                            Log.d(LOG, "Сообщение ошибки = " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        Log.d(LOG, "onResume: im working");
    }

    private void getUserName() {
        App.getNetClient().getCurrentUser(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    App.setUsername(response.body().getLogin());
                    goMainActivity();
                } else {
                    Log.d(LOG, "Код ошибки = " + response.code());
                    try {
                        Log.d(LOG, "Сообщение ошибки = " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Log.d(LOG, "getUserName: im working");
    }

    //Метод для перехода на след активити - которое головное
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();

        Log.d(LOG, "goMainActivity: im working");
    }
}