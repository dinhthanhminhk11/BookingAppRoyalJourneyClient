package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityPayCashYourBinding;

public class PayCashYourActivity extends AppCompatActivity {

    private ActivityPayCashYourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayCashYourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        binding.contentAddMoney.setOnClickListener(v -> {
            startActivity(new Intent(this, AddMoneyActivity.class));
        });

    }

    private void initToolbar() {
        binding.toolBar.setTitle(R.string.yourpayment);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
    }
}