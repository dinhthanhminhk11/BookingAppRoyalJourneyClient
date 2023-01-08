package com.example.bookingapproyaljourney.ui.activity.Hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapproyaljourney.databinding.ActivityRoomInfoBinding;

public class RoomInfoActivity extends AppCompatActivity {
    private ActivityRoomInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}