package com.example.bookingapproyaljourney.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.bookingapproyaljourney.databinding.ActivityOtppasswordBinding;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.OTPPasswordViewModel;

public class OTPPasswordActivity extends AppCompatActivity {

    private ActivityOtppasswordBinding binding;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;
    private OTPPasswordViewModel otpPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtppasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otpPasswordViewModel = new ViewModelProvider(this).get(OTPPasswordViewModel.class);

        String mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        binding.textAlien.setText("Nhập mã mà chúng tôi đã gửi qua tin nhắn email " + mail);

        binding.sendAgain.setPaintFlags(binding.sendAgain.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.sendAgain.setOnClickListener(v -> {
            Toast.makeText(this, "Đã Gửi", Toast.LENGTH_SHORT).show();
            otpPasswordViewModel.checkMail(new Email(mail));
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

        binding.btnSignIn.setOnClickListener(v -> {
            otpPasswordViewModel.postOTP(new Verify(mail, binding.otp.getText().toString().trim()));
        });

        otpPasswordViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        otpPasswordViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    Intent intent = new Intent(OTPPasswordActivity.this, NewPasswordActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, mail);
                    startActivity(intent);
                } else {
                    Toast.makeText(OTPPasswordActivity.this, testResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}