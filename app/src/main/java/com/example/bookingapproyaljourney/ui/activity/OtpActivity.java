package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityOtpBinding;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.LoginResponse;
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
        binding.sendAgain.setPaintFlags(binding.sendAgain.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        String mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);
        String pass = getIntent().getStringExtra(AppConstant.PASS_USER);

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
                    viewModel.login(mail ,pass );
                    Toast.makeText(OtpActivity.this, testResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OtpActivity.this, CongratsActivity.class));
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

        viewModel.getmLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                SharedPreferences sharedPreferences = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(AppConstant.TOKEN_USER, loginResponse.getToken());
                editor.commit();
            }
        });
    }
}