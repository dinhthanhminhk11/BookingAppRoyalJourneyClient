package com.example.bookingapproyaljourney.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.constants.Constants;
import com.example.bookingapproyaljourney.databinding.ActivitySeeMoreNearFromYouBinding;
import com.example.bookingapproyaljourney.map.FetchAddressIntentServices;
import com.example.bookingapproyaljourney.model.house.DataMap;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.repository.MapRepository;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapterMap;
import com.example.bookingapproyaljourney.view_model.MapActivityNearByFromYouViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SeeMoreNearFromYouActivity extends BaseActivity implements OnMapReadyCallback {
    private String nameLocationYourSelf;
    private ActivitySeeMoreNearFromYouBinding binding;
    private GoogleMap mMap;
    private MapRepository mapRepository;
    private IconGenerator iconGenerator;
    private View markerView;
    private TextView priceTag;
    private static final Drawable TRANSPARENT_DRAWABLE = new ColorDrawable(Color.TRANSPARENT);
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Location locationYouSelf;
    private ResultReceiver resultReceiver;
    private LatLng latLngLocationYourSelf;
    private LatLng currentUserLocation, searchPointLocation;
    private MarkerOptions markerOptions;
    private Marker currentUser, searchPoint;
    private NumberFormat fm = new DecimalFormat("#,###");
    private MapActivityNearByFromYouViewModel mapActivityNearByFromYouViewModel;
    private NearFromYouAdapterMap nearFromYouAdapterMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeMoreNearFromYouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(SeeMoreNearFromYouActivity.this);
                mapActivityNearByFromYouViewModel = new ViewModelProvider(mapFragment.getActivity()).get(MapActivityNearByFromYouViewModel.class);

            }
        }, 1000);
        initView();
    }

    private void initView() {
        nearFromYouAdapterMap = new NearFromYouAdapterMap();
        mapRepository = new MapRepository();
        setSupportActionBar(binding.toolBar2);
        getSupportActionBar().setTitle(R.string.textBest4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        resultReceiver = new AddressResultReceiver(new Handler());
        binding.toolBar2.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        iconGenerator = new IconGenerator(this);
        iconGenerator.setBackground(TRANSPARENT_DRAWABLE);

        markerView = this.getLayoutInflater().inflate(R.layout.marker, null);
        priceTag = (TextView) markerView.findViewById(R.id.priceTag);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SeeMoreNearFromYouActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();// hàm lấy vị trí chỗ mình đang đứng
        }

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
//                CookieBar.build(SeeMoreNearFromYouActivity.this)
//                        .setCustomView(R.layout.custom_cookie)
//                        .setCustomViewInitializer(new CookieBar.CustomViewInitializer() {
//                            @Override
//                            public void initView(View view) {
//                                ImageView btnNew = view.findViewById(R.id.close);
////                                Button btnOpen = view.findViewById(R.id.custom_cookie_btn_open);
////                                Button btnSave = view.findViewById(R.id.custom_cookie_btn_save);
//                                View.OnClickListener btnListener = new View.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View view) {
////                                        Button button = (Button) view;
////                                        button.setText(R.string.clicked);
//                                    }
//                                };
//                                btnNew.setOnClickListener(btnListener);
////                                btnOpen.setOnClickListener(btnListener);
////                                btnSave.setOnClickListener(btnListener);
//                            }
//                        })
//                        .setAction("Close", new OnActionClickListener() {
//                            @Override
//                            public void onClick() {
//                                CookieBar.dismiss(SeeMoreNearFromYouActivity.this);
//                            }
//                        })
//                        .setTitle("R.string.custom_view_cookie_title")
//                        .setMessage("R.string.custom_view_cookie_message")
//                        .setEnableAutoDismiss(false)
//                        .setSwipeToDismiss(false)
//                        .setCookiePosition(Gravity.BOTTOM)
//                        .show();

//                CookieBar.build(SeeMoreNearFromYouActivity.this)
////                        .setTitle(R.string.Successfully)
////                        .setMessage(R.string.textcheck111)
////                        .setIcon(R.drawable.ic_complete_order)
////                        .setTitleColor(R.color.black)
////                        .setMessageColor(R.color.black)
////                        .setDuration(5000)
//                        .setCustomView(R.layout.custom_cookie)
//                        .setBackgroundRes(R.drawable.background_toast)
//                        .setCookiePosition(CookieBar.BOTTOM)
//                        .setEnableAutoDismiss(false)
//                        .setSwipeToDismiss(false)
//                        .show();
                return false;
            }
        });

        mapActivityNearByFromYouViewModel.getNearestByUserOnMapResultMutableData().observe(this, new Observer<HouseNearestByUserResponse>() {
            @Override
            public void onChanged(HouseNearestByUserResponse houseNearestByUserResponse) {
                for (DataMap house : houseNearestByUserResponse.getDataMaps()
                ) {
                    drawMakerListDataHouse(house);
                }
                initData(houseNearestByUserResponse.getDataMaps());
            }
        });
    }

    private void getCurrentLocation() {
        binding.progressCircular.setVisibility(View.VISIBLE);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.getFusedLocationProviderClient(SeeMoreNearFromYouActivity.this)
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
                            Log.e("Minh", String.format("Latitude : %s\n Longitude: %s", lati, longi));
                            binding.progressCircular.setVisibility(View.GONE);
                            locationYouSelf = new Location("LocationYouSef");
                            locationYouSelf.setLongitude(longi);
                            locationYouSelf.setLatitude(lati);
                            fetchAddressFromLocation(locationYouSelf);
                            getAddress(lati, longi);
                            // vị trí của mình
                            latLngLocationYourSelf = new LatLng(lati, longi);
                            mapActivityNearByFromYouViewModel.getHouseNearestByUserOnPosition(latLngLocationYourSelf);
                            showMakerAndText(locationYouSelf);
                            /// get long lat từ địa chỉ
                            // tinh postion đầu tiên của list

//                            distance.setText(decimalFormat.format(SphericalUtil.computeDistanceBetween(latLngLocationYourSelf, latLng) / 1000) + " km");

                            //tính quãng đường chim bay
                            // dm chỗ này đéo có tiền đéo tính đc quảng đường thực :))
//                            SphericalUtil.computeDistanceBetween(latLngStart, latLng);
//                            controller.getRootDistanceAndDuration(nameLocationYourSelf, data.get(0).getAddress(), distance, time);
//                            LatLng latLng = getLocationFromAddress(data.get(0).getAddress());
//                            decimalFormat.setRoundingMode(RoundingMode.UP);
                        } else {
                            binding.progressCircular.setVisibility(View.GONE);
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void fetchAddressFromLocation(Location locationYouSelf) {
        Intent intent = new Intent(this, FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, locationYouSelf);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {
                nameLocationYourSelf = resultData.getString(Constants.DISTRICT) + resultData.getString(Constants.STATE);
                Log.e("Minh", resultData.getString(Constants.DISTRICT));
            } else {
                Toast.makeText(SeeMoreNearFromYouActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            binding.progressCircular.setVisibility(View.GONE);
        }
    }

    private void getAddress(double lati, double longi) {
        Geocoder geocoder = new Geocoder(SeeMoreNearFromYouActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longi, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            nameLocationYourSelf = add;
            Log.e("MinhLocation", add);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();
            binding.toolBar2.setTitle(obj.getAdminArea());
            binding.toolBar2.setSubtitle(add);
            Log.v("IGA", "Address" + add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showMakerAndText(Location location) {
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(10)
                    .bearing(0)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // vẽ maker
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserLocation = myLocation;
            markerOptions = new MarkerOptions()
                    .position(myLocation)
                    .title("Your position at here")
                    .snippet("Hello RoyalJourney Company")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            currentUser = mMap.addMarker(markerOptions);
            currentUser.setTag(false);
        }
    }

    private void drawMakerListDataHouse(DataMap house) {
        priceTag.setText("$" + fm.format(house.getData().getPrice()));
        LatLng latLng = new LatLng(house.getData().getLocation().getCoordinates().get(1), house.getData().getLocation().getCoordinates().get(0));
        iconGenerator.setContentView(markerView);
        iconGenerator.setTextAppearance(R.style.iconGenText);
        markerOptions = new MarkerOptions()
                .position(latLng)
                .title(house.getData().getName())
                .snippet(house.getData().getNameLocation())
                .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon()));
        currentUser = mMap.addMarker(markerOptions);
        currentUser.setTag(false);
    }

    private void initData(List<DataMap> data) {
        binding.textCountHouse.setText(this.getString(R.string.textBest5) + " " + data.size() + " " + this.getString(R.string.textBest6));
        nearFromYouAdapterMap.setData(data);
        nearFromYouAdapterMap.setCallback(new NearFromYouAdapterMap.Callback() {
            @Override
            public void onClickBookMark(House house) {

            }

            @Override
            public void onDirect(House house) {
                LatLng latLng = getLocationFromAddress(house.getNameLocation());
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%s", latLng.latitude, latLng.longitude, house.getNameLocation());
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }

            @Override
            public void clickItem(String id) {
                Intent intent = new Intent(SeeMoreNearFromYouActivity.this, DetailProductActivity.class);
                intent.putExtra(AppConstant.HOUSE_EXTRA, id);
                startActivity(intent);
            }
        });
        binding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerview.setAdapter(nearFromYouAdapterMap);
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(SeeMoreNearFromYouActivity.this);
        List<Address> address;
        LatLng latLngGetAddress = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            latLngGetAddress = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return latLngGetAddress;
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.toolBar2.setBackgroundResource(R.drawable.background_button_filter_dark);
            binding.toolBar2.setTitleTextColor(Color.WHITE);
            binding.toolBar2.setSubtitleTextColor(Color.WHITE);
            binding.toolBar2.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            nearFromYouAdapterMap.setColor(Color.WHITE, this.getResources().getColor(R.color.dark_282A37));
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            binding.bottomSheetProfile.setBackgroundResource(R.drawable.background_card_profile);
            binding.textCountHouse.setTextColor(Color.WHITE);
        } else {
            binding.toolBar2.setBackgroundResource(R.drawable.background_button_filter);
            binding.toolBar2.setTitleTextColor(Color.BLACK);
            binding.toolBar2.setSubtitleTextColor(Color.BLACK);
            binding.toolBar2.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            nearFromYouAdapterMap.setColor(Color.BLACK, Color.WHITE);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            binding.bottomSheetProfile.setBackgroundResource(R.drawable.background_card_profile_white);
            binding.textCountHouse.setTextColor(Color.BLACK);
        }
    }

}