package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityRegisterBinding;
import com.example.bookingapproyaljourney.response.RegisterResponse;
import com.example.bookingapproyaljourney.view_model.RegisterViewModel;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;
    private String tokenDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        tokenDevice = token;
                        Log.d("MinhtokenFirebase", token);
                    }
                });

        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        new Handler().postDelayed(() -> {
            Log.d("MinhTokenRegister", tokenDevice.toString());
        }, 1000);

        binding.btnRegister.setOnClickListener(v -> {
            String Name = binding.edNameRegister.getText().toString();
            String Email = binding.edMailRegister.getText().toString();
            String Password = binding.edPassRegister.getText().toString();
            String CFPassword = binding.edCfPassRegister.getText().toString();
            validateinfo(Name, Email, Password, CFPassword, tokenDevice.toString());
        });

        viewModel.getLoginResult().observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse.isStatus()) {
                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, binding.edMailRegister.getText().toString());
                    intent.putExtra(AppConstant.PASS_USER, binding.edCfPassRegister.getText().toString());
                    startActivity(intent);
                } else {
                    CookieBar.build(RegisterActivity.this)
                            .setTitle(R.string.Notify)
                            .setMessage(registerResponse.getMessage())
                            .setIcon(R.drawable.ic_warning_icon_check)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(3000)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
                }
            }
        });
//        viewModel.getLoginResult().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                if (s.equals(RegisterActivity.this.getResources().getString(R.string.RegisterSuccess))) {
////                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
//                    intent.putExtra(AppConstant.EMAIL_USER, binding.edMailRegister.getText().toString());
//                    intent.putExtra(AppConstant.PASS_USER, binding.edCfPassRegister.getText().toString());
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
        viewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    private Boolean validateinfo(String name, String email, String password, String cfPassword, String tokenDeviceValidate) {
        if (name.length() == 0) {
            binding.edNameRegister.requestFocus();
            binding.edNameRegister.setError(getString(R.string.enterName));
            return true;
        } else if (email.length() == 0) {
            binding.edMailRegister.requestFocus();
            binding.edMailRegister.setError(getString(R.string.enterMail));
        } else if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$")) {
            binding.edMailRegister.requestFocus();
            binding.edMailRegister.setError(getString(R.string.enterMailFaild));
            return false;
        } else if (password.length() <= 6) {
            binding.edPassRegister.requestFocus();
            binding.edPassRegister.setError(getString(R.string.textCheck1Register));
        } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
            binding.edPassRegister.requestFocus();
            binding.edPassRegister.setError(getString(R.string.textCheck2Register));
            return false;
        } else if (cfPassword.length() <= 6) {
            binding.edCfPassRegister.requestFocus();
            binding.edCfPassRegister.setError(getString(R.string.textCheck3Register));
            return false;
        } else {
            viewModel.register(binding.edNameRegister.getText().toString(), binding.edMailRegister.getText().toString(),
                    binding.edPassRegister.getText().toString(), tokenDeviceValidate);
            return true;
        }
        return null;
    }


}