package com.example.bookingapproyaljourney.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityAddPassPinBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.model.user.UserPin;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.AddPassPinViewModel;
import com.example.librarytoastcustom.CookieBar;

public class AddPassPinActivity extends BaseActivity {

    private ActivityAddPassPinBinding binding;

    private String textString1;
    private String textString2;
    private AddPassPinViewModel addPassPinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPassPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        addPassPinViewModel = new ViewModelProvider(this).get(AddPassPinViewModel.class);
        CookieBar.build(this)
                .setTitle("Thông báo")
                .setMessage("Hãy tạo mật khẩu ví của bạn để an toàn hơn")
                .setIcon(R.drawable.ic_warning_icon_check)
                .setTitleColor(R.color.black)
                .setMessageColor(R.color.black)
                .setDuration(5000).setSwipeToDismiss(false)
                .setBackgroundRes(R.drawable.background_toast)
                .setCookiePosition(CookieBar.BOTTOM)
                .show();        binding.editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    textString1 = String.valueOf(s);
                    binding.text.setText("Nhập lại mã pin");
                    binding.editText1.setVisibility(View.GONE);
                    binding.editText2.setVisibility(View.VISIBLE);
                    binding.text2.setVisibility(View.VISIBLE);
                    binding.editText2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.text2.setOnClickListener(v -> {
            binding.text2.setVisibility(View.GONE);
            binding.editText1.setVisibility(View.VISIBLE);
            binding.editText2.setVisibility(View.GONE);
            binding.text.setText("Thiết lập mật khẩu của bạn");
            binding.editText1.setText("");
        });

        binding.editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    if (String.valueOf(s).equals(textString1)) {
                        addPassPinViewModel.createPassCash(new UserPin(UserClient.getInstance().getId(), String.valueOf(s)));
                    } else {
                        Toast.makeText(AddPassPinActivity.this, "Mã pin nhập không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addPassPinViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    onBackPressed();
                } else {
                    Toast.makeText(AddPassPinActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //thay đổi Theme
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.toolbar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);
            binding.toolbar.setTitleTextColor(Color.WHITE);
            binding.layoutAddPassPin.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));

            binding.text.setTextColor(Color.WHITE);
            binding.editText1.setTextColor(Color.WHITE);
            binding.editText2.setTextColor(Color.WHITE);
            binding.text2.setTextColor(Color.WHITE);

        } else {
            binding.toolbar.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
            binding.toolbar.setTitleTextColor(Color.BLACK);
            binding.layoutAddPassPin.setBackgroundColor(this.getResources().getColor(R.color.white));

            binding.text.setTextColor(Color.BLACK);
            binding.editText1.setTextColor(Color.BLACK);
            binding.editText2.setTextColor(Color.BLACK);
            binding.text2.setTextColor(Color.BLACK);
        }
    }


}