package com.example.bookingapproyaljourney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.example.bookingapproyaljourney.ui.activity.LoginActivity;

public class SplashScreenVer2 extends AppCompatActivity {
    private View Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_ver2);

        Splash.blackIconStatusBar(SplashScreenVer2.this, R.color.light_Background);

        Logo = findViewById(R.id.Logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenVer2.this, LoginActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenVer2.this,
                        Pair.create(Logo ,"logo"));
                startActivity(intent, options.toBundle());
            }
        },3000);
    }
}