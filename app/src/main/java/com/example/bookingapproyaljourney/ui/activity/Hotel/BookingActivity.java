package com.example.bookingapproyaljourney.ui.activity.Hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapproyaljourney.databinding.ActivityBookingBinding;

public class BookingActivity extends AppCompatActivity {
    private ActivityBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}