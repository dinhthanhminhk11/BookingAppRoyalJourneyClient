package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.databinding.ActivityCancelBookingBinding;

public class CancelBookingActivity extends AppCompatActivity {

    private ActivityCancelBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCancelBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackFB.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}