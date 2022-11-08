package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.databinding.ActivityOtpBinding;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.bookingapproyaljourney.view_model.VerifyViewModel;

public class OtpActivity extends AppCompatActivity {

    private ActivityOtpBinding binding;
    private VerifyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(VerifyViewModel.class);
        binding.btnSignIn.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.sendAgain.setOnClickListener(v -> {

        });
    }
}