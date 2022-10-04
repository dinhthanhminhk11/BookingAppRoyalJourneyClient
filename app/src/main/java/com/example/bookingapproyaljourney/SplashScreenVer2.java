package com.example.bookingapproyaljourney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
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
        setTitle("");
        ImageView imageView = findViewById(R.id.Logo);
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenVer2.this, LoginActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenVer2.this, imageView,ViewCompat.getTransitionName(imageView));
                startActivity(intent, options.toBundle());
            }
        },3000);
    }
}