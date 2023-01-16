package com.example.bookingapproyaljourney.ui.activity;

import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityLoginBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRequestTokenDevice;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity {
    private String correct_email = "";
    private String correct_password = "";
    private UserLogin userLogin;
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private String checkStartDateResponse;
    private String tokenDevice;
    private String stringCheckNullToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        stringCheckNullToken = getIntent().getStringExtra(AppConstant.CHECK_LOGIN_TOKEN_NULL);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

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
            if (binding.edEmail.getText().toString().isEmpty()) {
                CookieBar.build(LoginActivity.this)
                        .setTitle(LoginActivity.this.getString(R.string.Notify))
                        .setMessage(LoginActivity.this.getString(R.string.enterMail))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();

            } else if (binding.edPass.getText().toString().isEmpty()) {
                CookieBar.build(LoginActivity.this)
                        .setTitle(LoginActivity.this.getString(R.string.Notify))
                        .setMessage(LoginActivity.this.getString(R.string.enterPass))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else {
                loginViewModel.login(binding.edEmail.getText().toString(), binding.edPass.getText().toString());
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CookieBar.build(LoginActivity.this)
                        .setTitle(R.string.Notify)
                        .setMessage(s)
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            }
        });

        loginViewModel.getLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getUser().isActive()) {
                    Log.e("MinhCooking", loginResponse.getUser().getId().toString());

                    if (!loginResponse.getUser().getTokenDevice().equals(tokenDevice)) {
                        loginViewModel.updateTokenDevice(new UserRequestTokenDevice(loginResponse.getUser().getId(), tokenDevice));
                    }

                    editor.putString(AppConstant.TOKEN_USER, loginResponse.getToken());
                    editor.putString(AppConstant.ID_USER, loginResponse.getUser().getId());
                    editor.commit();
                    if (!(stringCheckNullToken == null)) {
                        if (stringCheckNullToken.equals("checkNotSignIn")) {
                            onBackPressed();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(CheckSuccess, AppConstant.LoginResultSuccess);
                            startActivity(intent);
                        }
                    }else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(CheckSuccess, AppConstant.LoginResultSuccess);
                        startActivity(intent);
                    }

                } else {
                    EventBus.getDefault().postSticky(new KeyEvent(AppConstant.CHECK_EVENT_CONFIRM_ACCOUNT));
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

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imageView.setImageResource(R.drawable.ic_shape_login_dark);
            binding.textView.setTextColor(Color.WHITE);
            binding.textView3.setTextColor(Color.WHITE);
        } else {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.color_F6F6F6));
            binding.imageView.setImageResource(R.drawable.ic_shape_login);
            binding.textView.setTextColor(Color.BLACK);
            binding.textView3.setTextColor(Color.BLACK);
        }
    }
}