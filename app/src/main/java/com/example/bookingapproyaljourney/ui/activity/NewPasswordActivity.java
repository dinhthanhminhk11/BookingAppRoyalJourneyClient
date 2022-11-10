package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityNewPasswordBinding;

public class NewPasswordActivity extends AppCompatActivity {

    ActivityNewPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}