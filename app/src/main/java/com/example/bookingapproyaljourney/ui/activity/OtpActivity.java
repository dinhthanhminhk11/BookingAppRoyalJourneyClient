package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityOtpBinding;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.TestResponse;
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

        String mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        viewModel = new ViewModelProvider(this).get(VerifyViewModel.class);
        binding.btnSignIn.setOnClickListener(v -> {
            String otp = binding.otp.getText().toString();
            viewModel.postVerify(new Verify(mail, otp));
        });

        binding.sendAgain.setOnClickListener(v -> {

        });

        viewModel.getmLoginResultMutableData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    Toast.makeText(OtpActivity.this, testResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(OtpActivity.this, testResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }
}