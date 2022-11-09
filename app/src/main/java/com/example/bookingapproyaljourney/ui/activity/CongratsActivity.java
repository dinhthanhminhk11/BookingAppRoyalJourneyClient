package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.databinding.ActivityCongratsBinding;

public class CongratsActivity extends AppCompatActivity {
    ActivityCongratsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCongratsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRentNow.setOnClickListener(v -> {
            Intent intent = new Intent(CongratsActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}