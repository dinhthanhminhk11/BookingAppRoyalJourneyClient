package com.example.bookingapproyaljourney.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityNewPasswordBinding;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.NewPassViewModel;
import com.example.librarytoastcustom.CookieBar;

public class NewPasswordActivity extends BaseActivity {

    private ActivityNewPasswordBinding binding;
    private NewPassViewModel newPassViewModel;

    private ImageView close;
    private TextView btnCancel;
    private Button login;
    private TextView text;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        newPassViewModel = new ViewModelProvider(this).get(NewPassViewModel.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        binding.btnSend.setOnClickListener(v -> {
            String Password = binding.edPass.getText().toString();
            String CFPassword = binding.edCfPass.getText().toString();
            validateinfo(Password, CFPassword);

        });

        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(NewPasswordActivity.this, RegisterActivity.class));
        });


        newPassViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    showDialog();
                } else {
                    Toast.makeText(NewPasswordActivity.this, testResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        newPassViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        newPassViewModel.getmLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.getUser().isActive()) {
                    editor.putString(AppConstant.TOKEN_USER, loginResponse.getToken());
                    editor.commit();
                    Toast.makeText(NewPasswordActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewPasswordActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // gọi thêm hàm xác thực mail ở đây
                    newPassViewModel.sendAgain(new Email(mail));

                    CookieBar.build(NewPasswordActivity.this)
                            .setTitle(R.string.Notify)
                            .setMessage(R.string.titleConfrimMail)
                            .setIcon(R.drawable.ic_warning_icon_check)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(3000)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
                    Intent intent = new Intent(NewPasswordActivity.this, OtpActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, loginResponse.getUser().getEmail());
                    Log.e("MinhEmailLogin", loginResponse.getUser().getEmail());
                    intent.putExtra(AppConstant.PASS_USER, binding.edCfPass.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }

    private Boolean validateinfo(String password, String cfPassword) {
        if (password.length() <= 6) {
//            binding.edPass.requestFocus();
//            binding.edPass.setError(getString(R.string.textCheck1Register));
            CookieBar.build(NewPasswordActivity.this)
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.textCheck1Register))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();

        } else if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
//            binding.edPass.requestFocus();
//            binding.edPass.setError(getString(R.string.textCheck2Register));
            CookieBar.build(NewPasswordActivity.this)
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.textCheck2Register))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();
            return false;
        } else if (cfPassword.length() <= 6) {
//            binding.edCfPass.requestFocus();
//            binding.edCfPass.setError(getString(R.string.textCheckNewPass2));
            CookieBar.build(NewPasswordActivity.this)
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.textCheckNewPass2))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();
            return false;
        } else {
            newPassViewModel.newPassword(new UserLogin(mail, binding.edCfPass.getText().toString().trim()));
        }
        return null;
    }

    private void showDialog() {
        final Dialog dialogLogOut = new Dialog(this);
        dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
        Window window2 = dialogLogOut.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
            dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        close = (ImageView) dialogLogOut.findViewById(R.id.close);
        btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
        text = (TextView) dialogLogOut.findViewById(R.id.text);
        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login = (Button) dialogLogOut.findViewById(R.id.login);
        login.setText(R.string.sign_in_login);
        text.setText(R.string.changePassWhyLogin);
        close.setOnClickListener(v1 -> {
            Intent intent = new Intent(NewPasswordActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        btnCancel.setOnClickListener(v1 -> {
            Intent intent = new Intent(NewPasswordActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        login.setOnClickListener(v2 -> {
            newPassViewModel.login(mail, binding.edCfPass.getText().toString().trim());
        });
        dialogLogOut.show();
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imageView.setImageResource(R.drawable.ic_shape_login_dark);
            binding.imageBot1.setImageResource(R.drawable.ic_ellipse_2_dark);
            binding.imageBot2.setImageResource(R.drawable.ic_ellipse_3_dark);
            binding.textView.setTextColor(Color.WHITE);
            binding.textView3.setTextColor(Color.WHITE);
            binding.textView4.setTextColor(Color.WHITE);
            binding.textView5.setTextColor(Color.WHITE);
        } else {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.color_F6F6F6));
            binding.imageView.setImageResource(R.drawable.ic_shape_login);
            binding.textView.setTextColor(Color.BLACK);
            binding.textView3.setTextColor(Color.BLACK);
            binding.textView4.setTextColor(Color.BLACK);
            binding.textView5.setTextColor(Color.BLACK);
            binding.imageBot1.setImageResource(R.drawable.ic_ellipse_2);
            binding.imageBot2.setImageResource(R.drawable.ic_ellipse_3);
        }
    }

}