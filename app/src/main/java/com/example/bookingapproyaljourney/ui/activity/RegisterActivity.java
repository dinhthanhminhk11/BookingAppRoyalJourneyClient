package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityRegisterBinding;
import com.example.bookingapproyaljourney.view_model.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnRegister.setOnClickListener(v -> {
            String Name = binding.edNameRegister.getText().toString();
            String Email = binding.edMailRegister.getText().toString();
            String Password = binding.edPassRegister.getText().toString();
            String CFPassword = binding.edCfPassRegister.getText().toString();
            validateinfo(Name, Email, Password, CFPassword);
        });

        viewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(RegisterActivity.this.getResources().getString(R.string.RegisterSuccess))) {
//                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, binding.edMailRegister.getText().toString());
                    intent.putExtra(AppConstant.PASS_USER, binding.edCfPassRegister.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    private Boolean validateinfo(String name, String email, String password, String cfPassword) {
        if (name.length() == 0) {
            binding.edNameRegister.requestFocus();
            binding.edNameRegister.setError("Xin hãy nhập tên");
            return true;
        } else if (email.length() == 0) {
            binding.edMailRegister.requestFocus();
            binding.edMailRegister.setError("Xin hãy nhập Email");
        } else if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$")) {
            binding.edMailRegister.requestFocus();
            binding.edMailRegister.setError("Địa chỉ Email không đúng");
            return false;
        } else if (password.length() <= 6) {
            binding.edPassRegister.requestFocus();
            binding.edPassRegister.setError("Phải trên 6 kí tự ");
        }else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")){
            binding.edPassRegister.requestFocus();
            binding.edPassRegister.setError("ít nhất chứa 1 chữ số và 1 chứ cái ");
            return false;
        } else if (cfPassword.length() <= 6) {
            binding.edCfPassRegister.requestFocus();
            binding.edCfPassRegister.setError("Không trùng khớp");
            return false;
        } else {
            viewModel.register(binding.edNameRegister.getText().toString(), binding.edMailRegister.getText().toString(), binding.edPassRegister.getText().toString(), this.getResources().getString(R.string.RegisterSuccess), this.getResources().getString(R.string.RegisterFailed));

            return true;
        }
        return null;
    }
}