package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityCongratsBinding;

public class CongratsActivity extends AppCompatActivity {
    ActivityCongratsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCongratsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        binding.btnRentNow.setOnClickListener(v -> {
            Intent intent = new Intent(CongratsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.textView1.setTextColor(Color.WHITE);
            binding.textView2.setTextColor(Color.WHITE);
        } else {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.color_F6F6F6));
            binding.textView1.setTextColor(Color.BLACK);
            binding.textView2.setTextColor(Color.BLACK);
        }
    }
}