package com.example.bookingapproyaljourney.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityCancellationPolicyBinding;

public class CancellationPolicyActivity extends AppCompatActivity {

    private ActivityCancellationPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCancellationPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);


        binding.toolBar.setTitle("");
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }
        binding.btnContact.setOnClickListener(v -> {
        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.tvContact.setTextColor(Color.WHITE);
            binding.btnContact.setBackgroundResource(R.drawable.textview_border_ver2_dark);
            binding.btnContact.setTextColor(Color.WHITE);

            binding.toolBar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.toolBar.setTitleTextColor(Color.WHITE);
            binding.toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

            binding.text1.setTextColor(Color.WHITE);
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

        } else {
            binding.toolBar.setBackgroundColor(Color.WHITE);
            binding.toolBar.setTitleTextColor(Color.BLACK);
            binding.toolBar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            binding.contentBackground.setBackgroundColor(Color.WHITE);
            binding.tvContact.setTextColor(Color.BLACK);
            binding.btnContact.setBackgroundResource(R.drawable.textview_border_black);
            binding.btnContact.setTextColor(Color.BLACK);

            binding.text1.setTextColor(Color.BLACK);
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
        }
    }

}