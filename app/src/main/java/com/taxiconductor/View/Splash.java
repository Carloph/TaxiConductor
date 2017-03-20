package com.taxiconductor.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;

import com.taxiconductor.R;
import com.taxiconductor.Utils.Util;
import com.taxiconductor.View.ViewHome.Home;
import com.taxiconductor.View.ViewLogin.Login;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    private static long TIME_SPLASH_SCREEN = 5000;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent_ini = new Intent(Splash.this, Login.class);
                Intent intent_menu = new Intent(Splash.this, Home.class);

                if (!TextUtils.isEmpty(Util.getUserPrefs(preferences)) && Util.getIdDriverPrefs(preferences) >= 1){
                    startActivity(intent_menu);
                }else {
                    startActivity(intent_ini);
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIME_SPLASH_SCREEN);
    }
}
