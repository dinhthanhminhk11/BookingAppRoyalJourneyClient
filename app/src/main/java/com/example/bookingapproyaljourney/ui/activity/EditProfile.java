package com.example.bookingapproyaljourney.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.librarycireleimage.CircleImageView;
import com.example.libraryimagepicker.ImagePicker;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private CircleImageView avtEditProfile;
    private ImageView cameraEditProfile;
    private ImageView iconLocalEditProfile;
    private TextView titleNameEditProfile;
    private TextView titleEmailEditProfile;
    private TextInputEditText nameEditProfile;
    private TextInputEditText phoneEditProfile;
    private TextInputEditText cccdEditProfile;
    private TextInputEditText locationEditProfile;
    private AppCompatButton saveEditProfile;

    private LoginViewModel loginViewModel;

    private Uri imagePath;
    private static final String TAG = "Upload ###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        avtEditProfile = (CircleImageView) findViewById(R.id.avtEditProfile);
        cameraEditProfile = (ImageView) findViewById(R.id.cameraEditProfile);
        iconLocalEditProfile = (ImageView) findViewById(R.id.iconLocalEditProfile);
        titleNameEditProfile = (TextView) findViewById(R.id.titleNameEditProfile);
        titleEmailEditProfile = (TextView) findViewById(R.id.titleEmailEditProfile);
        nameEditProfile = (TextInputEditText) findViewById(R.id.nameEditProfile);
        phoneEditProfile = (TextInputEditText) findViewById(R.id.phoneEditProfile);
        cccdEditProfile = (TextInputEditText) findViewById(R.id.cccdEditProfile);
        locationEditProfile = (TextInputEditText) findViewById(R.id.locationEditProfile);
        saveEditProfile = (AppCompatButton) findViewById(R.id.saveEditProfile);

        toolBar.setNavigationIcon(R.drawable.ic_exit_edit_profile);
        toolBar.setTitle("Chỉnh sửa hồ sơ");
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        initData();
        initCongif();

//        edit image
        cameraEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfile.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(20);
            }
        });

//        ban du lieu len clouldy
        saveEditProfile.setOnClickListener(v -> {
            Log.d(TAG, "button click");
            if(imagePath == null){
                onBackPressed();
                return;
            }
            MediaManager.get().upload(imagePath).callback(new UploadCallback() {

                @Override
                public void onStart(String requestId) {
                    Log.d(TAG, "onStart " + "started");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    Log.d(TAG, "onStart " + imagePath);
                }

                // tra ve link img cloudy
                @Override
                public void onSuccess(String requestId, Map resultData) {
//                    tvURL.setText(resultData.get("url").toString());
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart " + error);
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    Log.d(TAG, "onStart " + error);
                }
            }).dispatch();
        });
    }

// get data user
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

    //  set img cho circleAvt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePath = data.getData();
        avtEditProfile.setImageURI(imagePath);
    }

    //  put dia chi clouldy
    private void initCongif() {
        try {
            Map config = new HashMap();
            config.put("cloud_name", "dphlpcxrq");
            config.put("api_key", "234574664596899");
            config.put("api_secret", "qRHVXqdqyJHBvv4nhZWMQBv1oe0");
            MediaManager.init(this, config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}