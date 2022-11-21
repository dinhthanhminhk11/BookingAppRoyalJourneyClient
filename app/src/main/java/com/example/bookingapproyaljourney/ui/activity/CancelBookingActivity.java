package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.databinding.ActivityCancelBookingBinding;

public class CancelBookingActivity extends AppCompatActivity {

    private ActivityCancelBookingBinding binding;
    private String dateCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCancelBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dateCancel = getIntent().getStringExtra("dateCancel");

        binding.textView8.setText("Hủy trước ngày " + dateCancel + " để được hoàn lại tiền , nếu bạn hủy sau ngày " + dateCancel +" sẽ không hoàn lại tiền");
        binding.imgBackFB.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}