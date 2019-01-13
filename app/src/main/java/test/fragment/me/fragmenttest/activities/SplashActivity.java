package test.fragment.me.fragmenttest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import test.fragment.me.fragmenttest.utils.App;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();

        if (App.getAccessToken()!= null) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        } else {
            intent = new Intent(this, ActivityVhod.class);
            startActivity(intent);
            finishAffinity();
        }
    }
}
