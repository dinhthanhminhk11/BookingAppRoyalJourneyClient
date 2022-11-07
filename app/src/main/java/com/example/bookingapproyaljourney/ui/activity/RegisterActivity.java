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
import com.example.bookingapproyaljourney.databinding.ActivityRegisterBinding;
import com.example.bookingapproyaljourney.view_model.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private TextView textView4;
    private EditText edNameRegister;
    private EditText edMailRegister;
    private EditText edPassRegister;
    private EditText edCfPassRegister;
    private AppCompatButton btnRegister;
    private TextView textView3;
    private TextView tvSignIn;
    private LottieAnimationView progressBar;
    private String correct_name = "";
    private String correct_email = "";
    private String correct_password = "";
    private String correct_cfpassword = "";


    private RegisterViewModel viewModel;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        textView4 = (TextView) findViewById(R.id.textView4);
        edNameRegister = (EditText) findViewById(R.id.edNameRegister);
        edMailRegister = (EditText) findViewById(R.id.edMailRegister);
        edPassRegister = (EditText) findViewById(R.id.edPassRegister);
        edCfPassRegister = (EditText) findViewById(R.id.edCfPassRegister);
        btnRegister = (AppCompatButton) findViewById(R.id.btnRegister);
        textView3 = (TextView) findViewById(R.id.textView3);
        tvSignIn = (TextView) findViewById(R.id.tvSignIn);
        progressBar = (LottieAnimationView) findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        binding.btnRegister.setOnClickListener(v -> {
            String Name = edNameRegister.getText().toString();
            String Email = edMailRegister.getText().toString();
            String Password = edPassRegister.getText().toString();
            String CFPassword = edCfPassRegister.getText().toString();

            validateinfo(Name, Email, Password, CFPassword);
        });

        viewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(RegisterActivity.this.getResources().getString(R.string.RegisterSuccess))) {
                    Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                    onBackPressed();
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
            edNameRegister.requestFocus();
            edNameRegister.setError("name");
            return true;
        } else if (!name.matches("[a-zA-z]+")) {
            edNameRegister.requestFocus();
            edNameRegister.setError("Name2");
            return false;
        } else if (email.length() == 0) {
            edMailRegister.requestFocus();
            edMailRegister.setError("email");
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            edMailRegister.requestFocus();
            edMailRegister.setError("email 2");
            return false;
        } else if (password.length() <= 6) {
            edPassRegister.requestFocus();
            edPassRegister.setError("phải trên 6 kí tự có chữ số ");
            return false;
        } else if (cfPassword.length() <= 6) {
            edCfPassRegister.requestFocus();
            edCfPassRegister.setError("Không trùng khớp");
            return false;
        } else {
            viewModel.register(binding.edNameRegister.getText().toString(), binding.edMailRegister.getText().toString(), binding.edPassRegister.getText().toString(), this.getResources().getString(R.string.RegisterSuccess), this.getResources().getString(R.string.RegisterFailed));
            return true;
        }
        return null;
    }
}