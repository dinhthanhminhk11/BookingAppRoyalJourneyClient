package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityLoginBinding;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ConstraintLayout contentView;
    private ImageView imageView;
    private TextView textView;
    private ImageView imageView2;
    private EditText edEmail;
    private EditText edPass;
    private TextView tvForgotPass;
    private AppCompatButton btnSignIn;
    private TextView textView3;
    private TextView tvSignUp;
    private LottieAnimationView progressBar;
    private String correct_email = "";
    private String correct_password = "";

    private UserLogin userLogin;


    private LoginViewModel loginViewModel;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        contentView = (ConstraintLayout) findViewById(R.id.contentView);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPass = (EditText) findViewById(R.id.edPass);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        btnSignIn = (AppCompatButton) findViewById(R.id.btnSignIn);
        textView3 = (TextView) findViewById(R.id.textView3);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        progressBar = (LottieAnimationView) findViewById(R.id.progressBar);
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        edEmail.setText("admin@gmail.com");
        edPass.setText("abc123");
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        binding.tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });

        binding.btnSignIn.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPass.getText().toString())){
//                Toast.makeText(LoginActivity.this,"Hãy điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
//            }else if (edEmail.getText().toString().equals(correct_email)){
//                if (edPass.getText().toString().equals(correct_password)){
//                    Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác ",Toast.LENGTH_SHORT).show();
//                }
//            }else {
//                Toast.makeText(LoginActivity.this,"Mật khẩu hoặc tài khoản không đúng",Toast.LENGTH_SHORT).show();
//            }
            loginViewModel.login(binding.edEmail.getText().toString(), binding.edPass.getText().toString(), this.getResources().getString(R.string.LoginSuccess), this.getResources().getString(R.string.LoginFailed));
        });

        loginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals(LoginActivity.this.getResources().getString(R.string.LoginSuccess))) {
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginViewModel.getLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getUser().isActive()) {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    editor.putString(AppConstant.TOKEN_USER, loginResponse.getToken());
                    editor.commit();
                    onBackPressed();
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản của bạn chưa xác thực email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, loginResponse.getUser().getEmail());
                    startActivity(intent);
                }
            }
        });

        loginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }


}