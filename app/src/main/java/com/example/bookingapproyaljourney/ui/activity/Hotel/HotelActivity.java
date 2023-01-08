package com.example.bookingapproyaljourney.ui.activity.Hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityHotelBinding;

public class HotelActivity extends AppCompatActivity {
    private ActivityHotelBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}