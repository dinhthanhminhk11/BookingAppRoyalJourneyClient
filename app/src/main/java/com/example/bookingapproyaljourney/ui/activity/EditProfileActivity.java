package com.example.bookingapproyaljourney.ui.activity;

import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.user.UserEditProfileRequest;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.EditProfileViewModel;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.librarycireleimage.CircleImageView;
import com.example.libraryimagepicker.ImagePicker;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private static final int REQUES_PERMISSION_CODE = 10;
    private MaterialToolbar toolBar;
    private CircleImageView avtEditProfile;
    private ImageView cameraEditProfile;
    private ImageView iconLocalEditProfile;
    private String idUser = "";
    private String linkImageAvt = "";
    private TextView titleNameEditProfile;
    private TextView titleEmailEditProfile;
    private TextInputEditText nameEditProfile;
    private TextInputEditText phoneEditProfile;
    private TextInputEditText cccdEditProfile;
    private TextInputEditText locationEditProfile;
    private AppCompatButton saveEditProfile;
    //    private LottieAnimationView progressBarEdiProfile;
    private LoginViewModel loginViewModel;
    private Location locationYouSelf;
    private String nameLocationYourSelf;
    private Uri imagePath;
    private static final String TAG = "Upload ###";
    private EditProfileViewModel editProfileViewModel;
    public static final int CAMERA_PERMISSION_REQ = 100;

    Dialog dialog;


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
//        progressBarEdiProfile = (LottieAnimationView) findViewById(R.id.progressBarEdiProfile);

        toolBar.setNavigationIcon(R.drawable.ic_exit_edit_profile);
        toolBar.setTitle(this.getString(R.string.edit_profile));
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        initData();
        initCongif();

//        edit image
        cameraEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intit();
                ImagePicker.with(EditProfileActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start(20);
            }
        });


//        ban du lieu len clouldy
        saveEditProfile.setOnClickListener(v -> {
            if (imagePath == null) {
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
                    linkImageAvt = resultData.get("url").toString();

                    editProfileViewModel.updateProfileUser(new UserEditProfileRequest(
                            idUser,
                            nameEditProfile.getText().toString(),
                            phoneEditProfile.getText().toString(),
                            cccdEditProfile.getText().toString(),
                            locationEditProfile.getText().toString(),
                            linkImageAvt
                    ));
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

// lay vi tri người dùng
        iconLocalEditProfile.setOnClickListener(view -> {
//            phien ban < android 6, khong can xin quyen
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, REQUES_PERMISSION_CODE);
            }

        });

        editProfileViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(CheckSuccess, AppConstant.GetTestResponseMutableLiveData);
                    startActivity(intent);
                } else {
                    CookieBar.build(EditProfileActivity.this)
                            .setTitle(R.string.Notify)
                            .setMessage(R.string.EditProfileError + "\n" + R.string.ThuLai)
                            .setIcon(R.drawable.ic_complete_order)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(3000)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
                }
            }
        });
    }

    private void intit() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            opencamera();
        } else {
            handlePermission();
        }
    }

    private void handlePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
// hoi xin quyen
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQ);
        }
    }

    //    thông báo khi người dùng lựa chọn cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUES_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                CookieBar.build(this)
                        .setTitle(R.string.Notify)
                        .setMessage(R.string.You_do_not_allow_location_access)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            }
        }

// bat su kien nguoi dung cap quyen Camera
        switch (requestCode) {
            case CAMERA_PERMISSION_REQ:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            CookieBar.build(this)
                                    .setTitle(R.string.Notify)
                                    .setMessage(R.string.Ban_tu_choi_cap_quyen_camera)
                                    .setTitleColor(R.color.black)
                                    .setMessageColor(R.color.black)
                                    .setDuration(3000)
                                    .setBackgroundRes(R.drawable.background_toast)
                                    .setCookiePosition(CookieBar.BOTTOM)
                                    .show();
                        } else {
                            showSettingsAlert();
                        }
                    } else {

                    }
                }
        }
    }

    // get data user
    private void initData() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        loginViewModel.getUserByToken(token);
        loginViewModel.getLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse s) {
                idUser = s.getUser().getId();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    lấy long lat
    private void getCurrentLocation() {
//        progressBarEdiProfile.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(EditProfileActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
//                            progressBarEdiProfile.setVisibility(View.GONE);
                            locationYouSelf = new Location("LocationYouSef");
                            locationYouSelf.setLongitude(longi);
                            locationYouSelf.setLatitude(lati);
                            getAddress(lati, longi);
                        } else {
//                            progressBarEdiProfile.setVisibility(View.GONE);
                        }
                    }
                }, Looper.getMainLooper());
    }

    //  truyen vao vi tri nguoi dung
    private void getAddress(double lati, double longi) {
        Geocoder geocoder = new Geocoder(EditProfileActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            nameLocationYourSelf = add;
            add = add + "\n" + obj.getCountryCode();
            locationEditProfile.setText(add);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //    show  dialog
    private void showSettingsAlert() {
        dialog = new Dialog(EditProfileActivity.this);
        dialog.setContentView(R.layout.permission_camera_editprofile_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog_editprofile));
        }
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        Button btnSetting = dialog.findViewById(R.id.btnSettingDialogEditProfile);
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialogEditProfile);
        btnSetting.setOnClickListener(view -> {
            dialog.dismiss();
            openAppSetting(EditProfileActivity.this);
        });

        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public static void openAppSetting(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

}