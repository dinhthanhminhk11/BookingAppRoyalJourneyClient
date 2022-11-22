package com.example.bookingapproyaljourney.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.librarycireleimage.CircleImageView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfile extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private CircleImageView avtEditProfile;
    private ImageView cameraEditProfile;
    private TextView titleNameEditProfile;
    private TextView titleEmailEditProfile;
    private TextInputEditText nameEditProfile;
    private TextInputEditText phoneEditProfile;
    private TextInputEditText cccdEditProfile;
    private TextInputEditText locationEditProfile;
    private ImageView imageView3;
    private AppCompatButton saveEditProfile;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        avtEditProfile = (CircleImageView) findViewById(R.id.avtEditProfile);
        cameraEditProfile = (ImageView) findViewById(R.id.cameraEditProfile);
        titleNameEditProfile = (TextView) findViewById(R.id.titleNameEditProfile);
        titleEmailEditProfile = (TextView) findViewById(R.id.titleEmailEditProfile);
        nameEditProfile = (TextInputEditText) findViewById(R.id.nameEditProfile);
        phoneEditProfile = (TextInputEditText) findViewById(R.id.phoneEditProfile);
        cccdEditProfile = (TextInputEditText) findViewById(R.id.cccdEditProfile);
        locationEditProfile = (TextInputEditText) findViewById(R.id.locationEditProfile);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        saveEditProfile = (AppCompatButton) findViewById(R.id.saveEditProfile);

        toolBar.setNavigationIcon(R.drawable.ic_exit_edit_profile);
        toolBar.setTitle("Chỉnh sửa hồ sơ");
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        initData();

    }

    private void initData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        loginViewModel.getUserByToken(token);
        loginViewModel.getLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse s) {
                titleNameEditProfile.setText(s.getUser().getName());
                titleEmailEditProfile.setText(s.getUser().getEmail());
                nameEditProfile.setText(s.getUser().getName());
                phoneEditProfile.setText(s.getUser().getPhone());
                locationEditProfile.setText(s.getUser().getAddress());
            }
        });


    }


}