package com.example.bookingapproyaljourney;

import static com.example.bookingapproyaljourney.constants.AppConstant.CancelBookingActivity;
import static com.example.bookingapproyaljourney.constants.AppConstant.CancelBookingActivityByAccess;
import static com.example.bookingapproyaljourney.constants.AppConstant.ChangePasswordResultSuccess;
import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;
import static com.example.bookingapproyaljourney.constants.AppConstant.LoginResultSuccess;
import static com.example.bookingapproyaljourney.constants.AppConstant.deleteOrderResponse;
import static com.example.bookingapproyaljourney.constants.AppConstant.text1111111111111;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.callback.CallDialog;
import com.example.bookingapproyaljourney.callback.CallbackOrderClick;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.constants.Constants;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.map.FetchAddressIntentServices;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.CountNotiResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.NearFromYouMapsActivity;
import com.example.bookingapproyaljourney.ui.custom.LighterHelper;
import com.example.bookingapproyaljourney.ui.fragment.BookmarkFragment;
import com.example.bookingapproyaljourney.ui.fragment.ChatFragment;
import com.example.bookingapproyaljourney.ui.fragment.HelpFragment;
import com.example.bookingapproyaljourney.ui.fragment.HomeFragment;
import com.example.bookingapproyaljourney.ui.fragment.ListOrderAllFragment;
import com.example.bookingapproyaljourney.ui.fragment.NotificationFragment;
import com.example.bookingapproyaljourney.ui.fragment.ProfileFragment;
import com.example.bookingapproyaljourney.ui.fragment.SettingFragment;
import com.example.bookingapproyaljourney.ui.view.menu.DrawerAdapter;
import com.example.bookingapproyaljourney.ui.view.menu.DrawerItem;
import com.example.bookingapproyaljourney.ui.view.menu.SimpleItem;
import com.example.bookingapproyaljourney.ui.view.menu.SpaceItem;
import com.example.bookingapproyaljourney.utils.LanguageConfig;
import com.example.bookingapproyaljourney.utils.SharedPrefs;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;
import com.example.hightlight.Lighter;
import com.example.hightlight.interfaces.OnLighterListener;
import com.example.hightlight.parameter.Direction;
import com.example.hightlight.parameter.LighterParameter;
import com.example.hightlight.parameter.MarginOffset;
import com.example.hightlight.shape.CircleShape;
import com.example.hightlight.shape.RectShape;
import com.example.librarynav.SlidingRootNav;
import com.example.librarynav.SlidingRootNavBuilder;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, CallbackOrderClick {

    private static final int POS_HOME = 0;
    private static final int POS_PROFILE = 1;
    private static final int POS_NEARBY = 2;

    private static final int POS_BOOKMARK = 4;
    private static final int POS_NOTIFICATION = 5;
    private static final int POS_MESSAGES = 6;

    private static final int POS_TRAVEL = 8;
    private static final int POS_SETTING = 9;
    private static final int POS_HELP = 10;
    private static final int POS_LOGOUT = 11;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int code = 100;
    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private FrameLayout container;
    private MaterialToolbar toolbar;
    private Spinner spinnerNav;
    private ImageView bell;
    private ImageView dotCheck;
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
    private String token1 = "";
    private String languageCode = "vi";
    private Context context;
    public static MainActivity instance;
    private Handler h2 = new Handler();

    private RelativeLayout contentBackgroundMenu;

    SharedPrefs sharedPreferences;

    public void setCallDialog(CallDialog callDialog) {
        this.callDialog = callDialog;
    }

    public void RestartMain() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 1);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        sharedPreferences = new SharedPrefs(newBase);
        String languageCode = sharedPreferences.getLocale();
        Context context = LanguageConfig.ChangeLanguage(newBase, languageCode);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);


        setContentView(R.layout.activity_main);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        container = (FrameLayout) findViewById(R.id.containerMain);
        toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        spinnerNav = (Spinner) findViewById(R.id.spinnerLocationMain);
        bell = (ImageView) findViewById(R.id.bellMain);
        dotCheck = (ImageView) findViewById(R.id.dotCheck);
        nameCity = (TextView) findViewById(R.id.nameCity);
        nameAddress = (TextView) findViewById(R.id.nameAddress);
        resultReceiver = new AddressResultReceiver(new Handler());


        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_BookingAppRoyalJourney_Dark);
        } else {
            setTheme(R.style.Theme_BookingAppRoyalJourney_Light);
        }

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withMenuOpened(false)
                .withToolbarMenuToggle(toolbar)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        contentBackgroundMenu = (RelativeLayout) findViewById(R.id.contentBackgroundMenu);

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
                createItemFor(POS_TRAVEL),
                createItemFor(POS_SETTING),
                createItemFor(POS_HELP),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        String check = getIntent().getStringExtra(CheckSuccess);

        if (!(check == null)) {
            if (check.equals(text1111111111111)) {
                CookieBar.build(this)
                        .setTitle(R.string.Successfully)
                        .setMessage(R.string.textcheck111)
                        .setIcon(R.drawable.ic_complete_order)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (check.equals(CancelBookingActivity)) {
                CookieBar.build(this)
                        .setTitle(R.string.Successfully)
                        .setMessage(R.string.textCheckCancelBookingActivity)
                        .setIcon(R.drawable.ic_complete_order)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (check.equals(CancelBookingActivityByAccess)) {
                CookieBar.build(this)
                        .setTitle(R.string.Successfully)
                        .setMessage(R.string.Request_canceled)
                        .setIcon(R.drawable.ic_complete_order)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (check.equals(deleteOrderResponse)) {
                CookieBar.build(this)
                        .setTitle(R.string.Successfully)
                        .setMessage(R.string.Delete_successfully)
                        .setIcon(R.drawable.ic_complete_order)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (check.equals(LoginResultSuccess)) {
                CookieBar.build(this)
                        .setTitle(R.string.Notify)
                        .setMessage(R.string.Logged_in_successfully)
                        .setIcon(R.drawable.ic_complete_order)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (check.equals(ChangePasswordResultSuccess)) {
                adapter.setSelected(POS_SETTING);
                CookieBar.build(this)
                        .setTitle(R.string.Notify)
                        .setMessage(R.string.ChangePasswordSuccess)
                        .setIcon(R.drawable.ic_complete_order)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
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

        bell.setOnClickListener(v -> {
            adapter.setSelected(POS_NOTIFICATION);
        });

        loginViewModel.getLoginResultMutableDataToKen().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse.isStatus()) {
                    loginViewModel.getCountNotificationByUser(UserClient.getInstance().getId());
                }
            }
        });

        loginViewModel.getCountNotiResponseMutableLiveData().observe(this, new Observer<CountNotiResponse>() {
            @Override
            public void onChanged(CountNotiResponse countNotiResponse) {
                if (countNotiResponse.getSize() > 0) {
                    dotCheck.setVisibility(View.VISIBLE);
                } else {
                    dotCheck.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        h2.postDelayed(r2, 30000);
        super.onResume();
    }

    @Override
    public void onItemSelected(int position) {
        final Dialog dialogLogOut = new Dialog(this);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dia_log_comfirm_logout);
        dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
        Window window = dialog.getWindow();
        Window window2 = dialogLogOut.getWindow();

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogLogOut.getWindow() != null) {
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

        Log.e("MInhToken", token + " token");
        if (position == POS_LOGOUT) {
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
        } else if (position == POS_HOME) {
            nameAddress.setVisibility(View.GONE);
            nameCity.setVisibility(View.VISIBLE);
            showFragment(new HomeFragment(locationYouSelf));
        } else if (position == POS_NEARBY) {
            startActivity(new Intent(MainActivity.this, NearFromYouMapsActivity.class));
        } else if (position == POS_PROFILE) {
            nameAddress.setText(R.string.File);
            nameAddress.setVisibility(View.VISIBLE);
            nameCity.setVisibility(View.GONE);
            showFragment(new ProfileFragment());
        } else if (position == POS_MESSAGES) {
            nameAddress.setText(R.string.Message);
            nameAddress.setVisibility(View.VISIBLE);
            nameCity.setVisibility(View.GONE);
            if (token == null || token.equals("")) {
                dialog.show();
            } else {
                showFragment(new ChatFragment(this));
            }
            login.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
            });
        } else if (position == POS_TRAVEL) {
            if (token == null || token.equals("")) {
                dialog.show();
            } else {
                nameAddress.setVisibility(View.VISIBLE);
                nameCity.setVisibility(View.GONE);
                nameAddress.setText(R.string.Your_trip);
                showFragment(new ListOrderAllFragment(this));
            }
            login.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
            });
        } else if (position == POS_BOOKMARK) {
            if (token == null || token.equals("")) {
                dialog.show();
            } else {
                nameAddress.setVisibility(View.VISIBLE);
                nameCity.setVisibility(View.GONE);
                nameAddress.setText(R.string.Favourite);
                showFragment(new BookmarkFragment(this));
            }
            login.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
            });
        } else if (position == POS_HELP) {
            nameAddress.setVisibility(View.VISIBLE);
            nameCity.setVisibility(View.GONE);
            nameAddress.setText(R.string.Help);
            showFragment(new HelpFragment());
        } else if (position == POS_SETTING) {
            nameAddress.setVisibility(View.VISIBLE);
            nameCity.setVisibility(View.GONE);
            nameAddress.setText(R.string.Setting);
            showFragment(new SettingFragment());
        } else if (position == POS_NOTIFICATION) {
            if (token == null || token.equals("")) {
                dialog.show();
            } else {
                nameAddress.setVisibility(View.VISIBLE);
                nameCity.setVisibility(View.GONE);
                nameAddress.setText(R.string.Notify);
                showFragment(new NotificationFragment());
            }
            login.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
            });

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
                .withBackground(Color.TRANSPARENT)
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
                CookieBar.build(this)
                        .setTitle(R.string.Notify)
                        .setMessage(R.string.You_do_not_allow_location_access)
                        .setIcon(R.drawable.ic_icon_logo_app)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void clickHome() {
        adapter.setSelected(POS_HOME);
    }

    @Override
    public void clickHelps() {
        adapter.setSelected(POS_HELP);
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

                        }
                    }
                }, Looper.getMainLooper());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(KeyEvent event) {
        if (event.getIdEven() == AppConstant.CHECK_EVENT_CLICK_NOTIFICATION) {
            loginViewModel.getCountNotificationByUser(UserClient.getInstance().getId());
        } else if (event.getIdEven() == 10) {
            sharedPreferences.setLocale("vi");
        } else if (event.getIdEven() == 11) {
            languageCode = "en";
            sharedPreferences.setLocale("en");
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_DARK) {
            changeTheme(1);
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_LIGHT) {
            changeTheme(2);
        } else if (event.getIdEven() == AppConstant.BY_USER_NEW) {
            adapter.setSelected(POS_HOME);
            showGuide();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        h2.removeCallbacks(r2);
        super.onPause();
    }

    public final boolean isInternetOn() {
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(this, R.string.NotConnectNetwork, Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private Runnable r2 = new Runnable() {
        @Override
        public void run() {
            isInternetOn();
            h2.postDelayed(r2, 10000);
        }
    };

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            toolbar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            container.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            bell.setColorFilter(getResources().getColor(R.color.white));
            nameAddress.setTextColor(getResources().getColor(R.color.white));
            nameCity.setTextColor(getResources().getColor(R.color.white));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            contentBackgroundMenu.setBackgroundColor(getResources().getColor(R.color.dark_1E202D));
        } else {
            toolbar.setBackgroundColor(this.getResources().getColor(R.color.white));
            container.setBackgroundColor(this.getResources().getColor(R.color.white));
            bell.setColorFilter(getResources().getColor(R.color.black));
            nameAddress.setTextColor(getResources().getColor(R.color.black));
            nameCity.setTextColor(getResources().getColor(R.color.black));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            contentBackgroundMenu.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    @SuppressLint("RestrictedApi")
    private void showGuide() {
        TranslateAnimation translateAnimation = new TranslateAnimation(-500, 0, 0, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new BounceInterpolator());

        CircleShape circleShape = new CircleShape(25);
        circleShape.setPaint(LighterHelper.getDashPaint()); //set custom paint


        RectShape rectShape = new RectShape();
        rectShape.setPaint(LighterHelper.getDiscretePaint());

        Lighter.with(this)
                .setBackgroundColor(0xB3000000)
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {

                    }

                    @Override
                    public void onDismiss() {
                        EventBus.getDefault().postSticky(new KeyEvent(AppConstant.BY_USER_VER2));
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.nameCity)
                        .setTipView(LighterHelper.createCommonTipView(this, R.drawable.icon_tip_4, "Vị trí của bạn"))
                        .setLighterShape(new RectShape(0, 0, 25))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewDisplayAnimation(LighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(0, 20, 0, 0))
                        .build())
                .show();
    }
}