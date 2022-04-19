package com.example.flight_mode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3500);
                    }
                }
                catch (InterruptedException ex) { }
                SharedPreferences sp=getSharedPreferences("login",0);
                Intent intent=new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        };
        mSplashThread.start();
    }
}

