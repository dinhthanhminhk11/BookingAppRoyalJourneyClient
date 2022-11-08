package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.databinding.ActivityOtpBinding;

public class OtpActivity extends AppCompatActivity {

    private ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignIn.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.sendAgain.setOnClickListener(v -> {

        });


    }
}