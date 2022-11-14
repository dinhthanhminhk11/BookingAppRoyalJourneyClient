package com.example.bookingapproyaljourney;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.callback.CallDialog;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.constants.Constants;
import com.example.bookingapproyaljourney.map.FetchAddressIntentServices;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.NearFromYouMapsActivity;
import com.example.bookingapproyaljourney.ui.fragment.ChatFragment;
import com.example.bookingapproyaljourney.ui.fragment.HomeFragment;
import com.example.bookingapproyaljourney.ui.fragment.ProfileFragment;
import com.example.bookingapproyaljourney.ui.view.menu.DrawerAdapter;
import com.example.bookingapproyaljourney.ui.view.menu.DrawerItem;
import com.example.bookingapproyaljourney.ui.view.menu.SimpleItem;
import com.example.bookingapproyaljourney.ui.view.menu.SpaceItem;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.librarynav.SlidingRootNav;
import com.example.librarynav.SlidingRootNavBuilder;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_NEARBY = 2;

    private static final int POS_BOOKMARK = 4;
    private static final int POS_NOTIFICATION = 5;
    private static final int POS_MESSAGES = 6;

    private static final int POS_SETTING = 8;
    private static final int POS_HELP = 9;
    private static final int POS_LOGOUT = 10;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int code = 100;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private FrameLayout container;
    private MaterialToolbar toolbar;
    private Spinner spinnerNav;
    private ImageView bell;
    private TextView nameCity;
    private TextView nameAddress;
    private DrawerAdapter adapter;
    private List<String> locations = new ArrayList<>();
    private ResultReceiver resultReceiver;
    private Location locationYouSelf;
    private Button login;
    private CallDialog callDialog;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;
    private LoginViewModel loginViewModel;

    public void setCallDialog(CallDialog callDialog) {
        this.callDialog = callDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        container = (FrameLayout) findViewById(R.id.containerMain);
        toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        spinnerNav = (Spinner) findViewById(R.id.spinnerLocationMain);
        bell = (ImageView) findViewById(R.id.bellMain);
        nameCity = (TextView) findViewById(R.id.nameCity);
        nameAddress = (TextView) findViewById(R.id.nameAddress);
        resultReceiver = new AddressResultReceiver(new Handler());

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withToolbarMenuToggle(toolbar)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_PROFILE),
                createItemFor(POS_NEARBY),
                new SpaceItem(48),
                createItemFor(POS_BOOKMARK),
                createItemFor(POS_NOTIFICATION),
                createItemFor(POS_MESSAGES),
                new SpaceItem(48),
                createItemFor(POS_SETTING),
                createItemFor(POS_HELP),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        String check = getIntent().getStringExtra("CheckSuccess");

        if (!(check == null)) {
            if (check.equals("1111111111111")) {
                ToastCheck toastCheck = new ToastCheck(MainActivity.this, R.style.StyleToast, "Thành công", "Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của chúng tôi , chúc bạn 1 ngày mới tốt lành", R.drawable.ic_complete_order);
            }
        }

        RecyclerView list = findViewById(R.id.listMenuLeftDrawer);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        if (token != null || !token.equals("")) {
            loginViewModel.getUserByToken(token);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            final Dialog dialog = new Dialog(this);
            final Dialog dialogLogOut = new Dialog(this);
            dialog.setContentView(R.layout.dia_log_comfirm_logout);
            dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
            Window window = dialog.getWindow();
            Window window2 = dialogLogOut.getWindow();

            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
                dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            login = (Button) dialog.findViewById(R.id.login);
            close = (ImageView) dialogLogOut.findViewById(R.id.close);
            btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
            btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            logOut = (Button) dialogLogOut.findViewById(R.id.login);
            SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
            if (token == null || token.equals("")) {
                dialog.show();
            } else {
                dialogLogOut.show();
            }

            login.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
            });

            logOut.setOnClickListener(v -> {
                editor.putString(AppConstant.TOKEN_USER, "");
                editor.commit();
                showFragment(new ProfileFragment());
                dialogLogOut.dismiss();
            });

            btnCancel.setOnClickListener(v -> {
                dialogLogOut.dismiss();
            });
            close.setOnClickListener(v -> {
                dialogLogOut.dismiss();
            });
//            0352145615

        } else if (position == POS_HOME) {
//            Log.e("testLoaction", " click lcilk");
            nameAddress.setVisibility(View.GONE);
            nameCity.setVisibility(View.VISIBLE);
            showFragment(new HomeFragment(locationYouSelf));
        } else if (position == POS_NEARBY) {
            startActivity(new Intent(MainActivity.this, NearFromYouMapsActivity.class));
        } else if (position == POS_PROFILE) {
            nameAddress.setText("Hồ Sơ");
            nameAddress.setVisibility(View.VISIBLE);
            nameCity.setVisibility(View.GONE);
            showFragment(new ProfileFragment());
        } else if (position == POS_MESSAGES) {
            nameAddress.setText("Tin Nhắn");
            nameAddress.setVisibility(View.VISIBLE);
            nameCity.setVisibility(View.GONE);
            showFragment(new ChatFragment());
        }
        slidingRootNav.closeMenu();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerMain, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withBackground(R.drawable.background_blue_activity)
                .withSelectedIconTint(color(R.color.blue))
                .withSelectedTextTint(color(R.color.blue))
                .withSelectedBackGroundTint(R.drawable.background_select_white_activity);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


    private void fetchAddressFromLocation(Location locationYouSelf) {
        Intent intent = new Intent(this, FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, locationYouSelf);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                adapter.setSelected(POS_HOME);
                Toast.makeText(this, "Bạn không cho phép truy cập vị trí", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                nameCity.setText(resultData.getString(Constants.ADDRESS));
                Log.e("Minh", resultData.getString(Constants.DISTRICT));
            } else {
                Toast.makeText(MainActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAddress(double lati, double longi) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            Log.e("MinhLocation", add);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            nameCity.setText(obj.getAdminArea());
            nameAddress.setText(add);
            Log.v("IGA", "Address" + add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
//        progressBar.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
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
                            Log.e("MinhLoaction", String.format("Latitude : %s\n Longitude: %s", lati, longi));
                            locationYouSelf = new Location("locationYourSelf");
                            locationYouSelf.setLongitude(longi);
                            locationYouSelf.setLatitude(lati);
                            fetchAddressFromLocation(locationYouSelf);
                            getAddress(lati, longi);
                            adapter.setSelected(POS_HOME);
                        } else {
//                            progressBar.setVisibility(View.GONE);

                        }
                    }
                }, Looper.getMainLooper());

    }

}