package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
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
            if (edEmail.getText().toString().isEmpty()) {
                binding.edEmail.requestFocus();
                binding.edEmail.setError("Xin vui lòng nhập địa chỉ Email");
            } else if (edPass.getText().toString().isEmpty()) {
                binding.edPass.requestFocus();
                binding.edPass.setError("Xin vui lòng nhập địa chỉ Password");
            } else {
                loginViewModel.login(binding.edEmail.getText().toString(), binding.edPass.getText().toString(), this.getResources().getString(R.string.LoginSuccess), this.getResources().getString(R.string.LoginFailed));
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (!s.equals(LoginActivity.this.getResources().getString(R.string.LoginSuccess))) {
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                    Log.e("dua", s);
                }
            }
        });

        loginViewModel.getLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getUser().isActive()) {
                    editor.putString(AppConstant.TOKEN_USER, loginResponse.getToken());
                    editor.putString(AppConstant.ID_USER, loginResponse.getUser().getId());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("CheckSuccess", "LoginResultSuccess");
                    startActivity(intent);
                } else {
                    ToastCheck toastCheck = new ToastCheck(LoginActivity.this, R.style.StyleToast,
//                            this.getString(R.string.dialogstartdate),
                            "Tài khoản của bạn chưa xác thực email",
//                            this.getString(R.string.dialogcontentnomal),
                            "Bảo mật bằng việc xác thực qua mã OTP được coi là hình thức bảo mật an toàn",
                            R.drawable.ic_warning_icon_check);

//                    Toast.makeText(LoginActivity.this, "Tài khoản của bạn chưa xác thực email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    loginViewModel.sendAgain(new Email(loginResponse.getUser().getEmail()));
                    intent.putExtra(AppConstant.EMAIL_USER, loginResponse.getUser().getEmail());
                    intent.putExtra(AppConstant.PASS_USER, binding.edPass.getText().toString());
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