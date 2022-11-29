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

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityOtpBinding;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.bookingapproyaljourney.view_model.VerifyViewModel;
import com.example.librarytoastcustom.CookieBar;

public class OtpActivity extends AppCompatActivity {

    private ActivityOtpBinding binding;
    private VerifyViewModel viewModel;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendAgain.setPaintFlags(binding.sendAgain.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);
        String pass = getIntent().getStringExtra(AppConstant.PASS_USER);

        binding.textAlien.setText("Nhập mã mà chúng tôi đã gửi qua tin nhắn email " + mail);
        viewModel = new ViewModelProvider(this).get(VerifyViewModel.class);
        binding.btnSignIn.setOnClickListener(v -> {
            String otp = binding.otp.getText().toString();
            validateinfo(otp);
            
        });

        binding.sendAgain.setOnClickListener(v -> {
            viewModel.sendAgain(new Email(mail));
        });

        binding.close.setOnClickListener(v -> {
            final Dialog dialogLogOut = new Dialog(this);
            dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
            Window window2 = dialogLogOut.getWindow();
            window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
                dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            close = (ImageView) dialogLogOut.findViewById(R.id.close);
            btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
            btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            logOut = (Button) dialogLogOut.findViewById(R.id.login);
            logOut.setText("Thoát");

            close.setOnClickListener(v1 -> {
                dialogLogOut.dismiss();
            });
            btnCancel.setOnClickListener(v1 -> {
                dialogLogOut.dismiss();
            });
            logOut.setOnClickListener(v2 -> {
                onBackPressed();
            });
            dialogLogOut.show();
        });

        viewModel.getmLoginResultMutableData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    viewModel.login(mail, pass);
                } else {
                    CookieBar.build(OtpActivity.this)
                            .setTitle(OtpActivity.this.getString(R.string.otp_is_incorrect))
                            .setMessage(OtpActivity.this.getString(R.string.check_otp))
                            .setIcon(R.drawable.ic_warning_icon_check)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(3000)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
//                    ToastCheck toastCheck = new ToastCheck(OtpActivity.this, R.style.StyleToast,
//                            "OTP không chính xác",
//                            "Bạn hãy kiểm tra lại mã OTP chúng tôi đã gửi trong email của bạn",
//                            R.drawable.ic_warning_icon_check);
//                    Log.e("sads",testResponse.getMessage());
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
//                Toast.makeText(OtpActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OtpActivity.this, CongratsActivity.class));
            }
        });

        viewModel.getSendAgainTestResponse().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                Toast.makeText(OtpActivity.this, testResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        String check = getIntent().getStringExtra("CheckSuccess");
        if (!(check == null)){
            if (check.equals("LoginActivity")){
                CookieBar.build(OtpActivity.this)
                        .setTitle(OtpActivity.this.getString(R.string.accuracy_email))
                        .setMessage(OtpActivity.this.getString(R.string.security_otp))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            }
        }
    }

    private Boolean validateinfo(String otp) {
        if (otp.length() != 6 ) {
            binding.otp.requestFocus();
            binding.otp.setError(this.getString(R.string.letter_otp));
            return true;
        }else {
            viewModel.postVerify(new Verify(mail, otp));
        }
        return null;
    }
}