package com.example.bookingapproyaljourney.ui.activity;

import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityChangePasswordBinding;
import com.example.bookingapproyaljourney.model.user.ChangePasswordRequest;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.ChangePasswordViewModel;
import com.example.librarytoastcustom.CookieBar;

public class ChangePasswordActivity extends BaseActivity {

    private ActivityChangePasswordBinding binding;
    private ChangePasswordViewModel changePasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }
        binding.backChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        changePasswordViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.oldPasswordSettings.getText().toString().isEmpty()){
                    binding.oldPasswordSettings.requestFocus();
                    binding.oldPasswordSettings.setError(getString(R.string.OldPassIsEmpty));
                    return;
                }else  if(binding.newPassSettings.getText().toString().isEmpty()){
                    binding.newPassSettings.requestFocus();
                    binding.newPassSettings.setError(getString(R.string.NewPassIsEmpty));
                    return;
                }else if(binding.rePasswordSettings.getText().toString().isEmpty()){
                    binding.rePasswordSettings.requestFocus();
                    binding.rePasswordSettings.setError(getString(R.string.RePassIsEmpty));
                    return;
                }else if (binding.newPassSettings.length() <= 6) {
                    binding.newPassSettings.requestFocus();
                    binding.newPassSettings.setError(getString(R.string.textCheck1Register));
                    return;
                } else if (!binding.newPassSettings.getText().toString().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
                    binding.newPassSettings.requestFocus();
                    binding.newPassSettings.setError(getString(R.string.textCheck2Register));
                    return;
                } else if (!binding.rePasswordSettings.getText().toString().equals(binding.newPassSettings.getText().toString())) {
                    binding.rePasswordSettings.requestFocus();
                    binding.rePasswordSettings.setError(getString(R.string.RePassNotMatch));
                    return;
                } else {
                    changePasswordViewModel.ChangePassword(new ChangePasswordRequest(UserClient.getInstance().getId(), binding.oldPasswordSettings.getText().toString(), binding.rePasswordSettings.getText().toString()));
                }
            }
        });

        changePasswordViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        changePasswordViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(CheckSuccess, AppConstant.ChangePasswordResultSuccess);
                    startActivity(intent);
                } else {
                    CookieBar.build(ChangePasswordActivity.this)
                            .setTitle(ChangePasswordActivity.this.getString(R.string.Notify))
                            .setMessage(testResponse.getMessage())
                            .setIcon(R.drawable.ic_warning_icon_check)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(5000).setSwipeToDismiss(false)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
                }
            }
        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imageView.setImageResource(R.drawable.ic_shape_login_dark);
            binding.textView.setTextColor(Color.WHITE);
            binding.textView4.setTextColor(Color.WHITE);
        } else {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.color_F6F6F6));
            binding.imageView.setImageResource(R.drawable.ic_shape_login);
            binding.textView.setTextColor(Color.BLACK);
            binding.textView4.setTextColor(Color.BLACK);
        }
    }


}