package com.claudylab.flashbytes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.claudylab.flashbytes.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("flash",MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.contains("lang")){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this,StartActivity.class));
                    finish();
                }

            }
        },2000);
    }
}