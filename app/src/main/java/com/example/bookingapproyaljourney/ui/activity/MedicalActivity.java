package com.example.bookingapproyaljourney.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityMedicalBinding;

public class MedicalActivity extends BaseActivity {
    private ActivityMedicalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgBack.setOnClickListener(v -> onBackPressed());

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.contentToolbar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imgBack.setColorFilter(getResources().getColor(R.color.white));

            binding.viewLine.setBackgroundColor(Color.WHITE);
            binding.text1.setTextColor(Color.WHITE);
            binding.textView10.setTextColor(Color.WHITE);
            binding.text2.setTextColor(Color.WHITE);
            binding.text3.setTextColor(Color.WHITE);
            binding.text4.setTextColor(Color.WHITE);
            binding.text5.setTextColor(Color.WHITE);
            binding.text6.setTextColor(Color.WHITE);
            binding.text7.setTextColor(Color.WHITE);
            binding.text8.setTextColor(Color.WHITE);
            binding.text9.setTextColor(Color.WHITE);
            binding.text10.setTextColor(Color.WHITE);
            binding.text11.setTextColor(Color.WHITE);
            binding.text12.setTextColor(Color.WHITE);
            binding.text13.setTextColor(Color.WHITE);
            binding.text14.setTextColor(Color.WHITE);
            binding.text15.setTextColor(Color.WHITE);
            binding.text16.setTextColor(Color.WHITE);
            binding.text17.setTextColor(Color.WHITE);
            binding.text18.setTextColor(Color.WHITE);
            binding.text19.setTextColor(Color.WHITE);
            binding.text20.setTextColor(Color.WHITE);
            binding.text21.setTextColor(Color.WHITE);
            binding.text22.setTextColor(Color.WHITE);

            binding.icon1.setColorFilter(Color.WHITE);
            binding.icon2.setColorFilter(Color.WHITE);
            binding.icon3.setColorFilter(Color.WHITE);
            binding.icon4.setColorFilter(Color.WHITE);
            binding.icon5.setColorFilter(Color.WHITE);
            binding.icon6.setColorFilter(Color.WHITE);
            binding.icon7.setColorFilter(Color.WHITE);
            binding.icon8.setColorFilter(Color.WHITE);
            binding.icon9.setColorFilter(Color.WHITE);
            binding.icon10.setColorFilter(Color.WHITE);
        } else {
            binding.contentBackground.setBackgroundColor(Color.WHITE);
            binding.contentToolbar.setBackgroundColor(Color.WHITE);
            binding.imgBack.setColorFilter(Color.BLACK);

            binding.viewLine.setBackgroundColor(Color.BLACK);
            binding.text1.setTextColor(Color.BLACK);
            binding.textView10.setTextColor(Color.BLACK);
            binding.text2.setTextColor(Color.BLACK);
            binding.text3.setTextColor(Color.BLACK);
            binding.text4.setTextColor(Color.BLACK);
            binding.text5.setTextColor(Color.BLACK);
            binding.text6.setTextColor(Color.BLACK);
            binding.text7.setTextColor(Color.BLACK);
            binding.text8.setTextColor(Color.BLACK);
            binding.text9.setTextColor(Color.BLACK);
            binding.text10.setTextColor(Color.BLACK);
            binding.text11.setTextColor(Color.BLACK);
            binding.text12.setTextColor(Color.BLACK);
            binding.text13.setTextColor(Color.BLACK);
            binding.text14.setTextColor(Color.BLACK);
            binding.text15.setTextColor(Color.BLACK);
            binding.text16.setTextColor(Color.BLACK);
            binding.text17.setTextColor(Color.BLACK);
            binding.text18.setTextColor(Color.BLACK);
            binding.text19.setTextColor(Color.BLACK);
            binding.text20.setTextColor(Color.BLACK);
            binding.text21.setTextColor(Color.BLACK);
            binding.text22.setTextColor(Color.BLACK);

            binding.icon1.setColorFilter(Color.BLACK);
            binding.icon2.setColorFilter(Color.BLACK);
            binding.icon3.setColorFilter(Color.BLACK);
            binding.icon4.setColorFilter(Color.BLACK);
            binding.icon5.setColorFilter(Color.BLACK);
            binding.icon6.setColorFilter(Color.BLACK);
            binding.icon7.setColorFilter(Color.BLACK);
            binding.icon8.setColorFilter(Color.BLACK);
            binding.icon9.setColorFilter(Color.BLACK);
            binding.icon10.setColorFilter(Color.BLACK);
        }
    }
}