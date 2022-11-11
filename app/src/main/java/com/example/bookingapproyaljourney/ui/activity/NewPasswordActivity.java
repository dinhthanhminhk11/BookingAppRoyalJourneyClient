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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityNewPasswordBinding;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.NewPassViewModel;

public class NewPasswordActivity extends AppCompatActivity {

    private ActivityNewPasswordBinding binding;
    private NewPassViewModel newPassViewModel;

    private ImageView close;
    private TextView btnCancel;
    private Button login;
    private TextView text;
    private String mail;
    private EditText edPass;
    private EditText edCfPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        newPassViewModel = new ViewModelProvider(this).get(NewPassViewModel.class);
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        binding.btnSend.setOnClickListener(v -> {
            String Password  = binding.edPass.getText().toString();
            String CFPassword = binding.edCfPass.getText().toString();
            validateinfo(Password, CFPassword);

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
                    Toast.makeText(NewPasswordActivity.this, "Tài khoản của bạn chưa xác thực email", Toast.LENGTH_SHORT).show();
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
        if (password.length() <=6){
            binding.edPass.requestFocus();
            binding.edPass.setError("Phải trên 6 kí tự, ít nhất chứa 1 chữ số và 1 chứ cái");
            return false;
        }else if (cfPassword.length() <= 6){
            binding.edCfPass.requestFocus();
            binding.edCfPass.setError("Mật khẩu không trùng khớp");
            return false;
        }else {
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
        login.setText("Đăng nhập");
        text.setText("Đổi mật khẩu thành công, bạn có muốn đăng nhập ?");
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


}