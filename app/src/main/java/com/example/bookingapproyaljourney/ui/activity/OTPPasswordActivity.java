package com.example.bookingapproyaljourney.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityOtppasswordBinding;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.OTPPasswordViewModel;
import com.example.librarytoastcustom.CookieBar;

public class OTPPasswordActivity extends BaseActivity {

    private ActivityOtppasswordBinding binding;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;
    private OTPPasswordViewModel otpPasswordViewModel;
    private EditText otp;
    private String mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtppasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        otpPasswordViewModel = new ViewModelProvider(this).get(OTPPasswordViewModel.class);
        mail = getIntent().getStringExtra(AppConstant.EMAIL_USER);

        binding.textAlien.setText(this.getString(R.string.enterCodeOTP) + mail);

        binding.sendAgain.setPaintFlags(binding.sendAgain.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }


        binding.sendAgain.setOnClickListener(v -> {
            CookieBar.build(this)
                    .setTitle(R.string.Notify)
                    .setMessage(R.string.SEND)
                    .setIcon(R.drawable.ic_complete_order)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();
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
            logOut.setText(R.string.out);

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
            String otp = binding.otp.getText().toString();
            validateinfo(otp);

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

    private Boolean validateinfo(String otp) {
        if (otp.length() != 6) {
//            binding.otp.requestFocus();
//            binding.otp.setError(getString(R.string.textOTP));
            CookieBar.build(OTPPasswordActivity.this)
                    .setTitle(getString(R.string.Notify))
                    .setMessage(getString(R.string.textOTP))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();
            return true;
        } else {
            otpPasswordViewModel.postOTP(new Verify(mail, binding.otp.getText().toString().trim()));
        }
        return null;
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.textAlien.setTextColor(Color.WHITE);
            binding.title.setTextColor(Color.WHITE);
            binding.textView1.setTextColor(Color.WHITE);
            binding.sendAgain.setTextColor(Color.WHITE);
            binding.otp.setBackgroundResource(R.drawable.textview_border_ver2_dark);
            binding.close.setColorFilter(getResources().getColor(R.color.white));
        } else {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.color_F6F6F6));
            binding.textAlien.setTextColor(Color.BLACK);
            binding.title.setTextColor(Color.BLACK);
            binding.textView1.setTextColor(Color.BLACK);
            binding.sendAgain.setTextColor(Color.BLACK);
            binding.otp.setBackgroundResource(R.drawable.textview_border_ver2);
            binding.close.setColorFilter(Color.BLACK);
        }
    }
}