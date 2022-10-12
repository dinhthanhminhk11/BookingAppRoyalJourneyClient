package com.example.bookingapproyaljourney.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.Constants;
import com.example.bookingapproyaljourney.databinding.ActivityNearFromYouMapsBinding;
import com.example.bookingapproyaljourney.map.FetchAddressIntentServices;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapterMap;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetFilterMap;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NearFromYouMapsActivity extends AppCompatActivity implements OnMapReadyCallback, BottomSheetFilterMap.Callback {


    private GoogleMap mMap;
    private ActivityNearFromYouMapsBinding binding;
    private LatLng latLngLocationYourSelf;
    private IconGenerator iconGenerator;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private NumberFormat fm = new DecimalFormat("#,###");

    private Location locationYouSelf;

    private ResultReceiver resultReceiver;
    private String nameLocationYourSelf;

    private LatLng currentUserLocation, searchPointLocation;
    private Marker currentUser, searchPoint;
    private MarkerOptions markerOptions;
    private NearFromYouAdapterMap nearFromYouAdapterMap;
    private List<House> data;
    private boolean checkSelectItem = false;
    private View markerView;
    private TextView priceTag;
    private BottomSheetFilterMap bottomSheetFilterMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNearFromYouMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setSupportActionBar(binding.toolBar2);
        getSupportActionBar().setTitle("Near From You");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolBar2.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar2                                                       .setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerview);
        resultReceiver = new AddressResultReceiver(new Handler());
        binding.filter.setOnClickListener(v -> {
            showDialog();
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        iconGenerator = new IconGenerator(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NearFromYouMapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();// hàm lấy vị trí chỗ mình đang đứng
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (marker.getPosition().latitude == latLngLocationYourSelf.latitude && marker.getPosition().longitude == latLngLocationYourSelf.longitude) {
                    return false;
                }
                binding.recyclerview.smoothScrollToPosition(Integer.parseInt(marker.getId().substring(1)) - 1);
                return false;
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

        LocationServices.getFusedLocationProviderClient(NearFromYouMapsActivity.this)
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
                            binding.contentTime.setVisibility(View.VISIBLE);
                            locationYouSelf = new Location("LocationYouSef");
                            locationYouSelf.setLongitude(longi);
                            locationYouSelf.setLatitude(lati);
                            fetchAddressFromLocation(locationYouSelf);
                            getAddress(lati, longi);
                            // vị trí của mình
                            latLngLocationYourSelf = new LatLng(lati, longi);
                            /// get long lat từ địa chỉ
                            // tinh postion đầu tiên của list

//                            distance.setText(decimalFormat.format(SphericalUtil.computeDistanceBetween(latLngLocationYourSelf, latLng) / 1000) + " km");

                            //tính quãng đường chim bay
                            // dm chỗ này đéo có tiền đéo tính đc quảng đường thực
//                            SphericalUtil.computeDistanceBetween(latLngStart, latLng);
                            showMakerAndText(locationYouSelf);

                            initData();
//                            controller.getRootDistanceAndDuration(nameLocationYourSelf, data.get(0).getAddress(), distance, time);
//                            LatLng latLng = getLocationFromAddress(data.get(0).getAddress());
//                            decimalFormat.setRoundingMode(RoundingMode.UP);
                            for (House house : data
                            ) {
                                drawMakerListDataHouse(house);
                            }
                        } else {
                            binding.progressCircular.setVisibility(View.GONE);
                            binding.contentTime.setVisibility(View.VISIBLE);
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void drawMakerListDataHouse(House house) {
        markerView = this.getLayoutInflater().inflate(R.layout.marker, null);
        priceTag = (TextView) markerView.findViewById(R.id.priceTag);
        priceTag.setText("$" + fm.format(house.getPrice()));
        LatLng latLng = new LatLng(house.getLat(), house.getLog());
//        iconfactory.setBackground(getResources().getDrawable(R.drawable.marker_background));

        iconGenerator.setContentView(markerView);
        iconGenerator.setTextAppearance(R.style.iconGenText);
        markerOptions = new MarkerOptions()
                .position(latLng)
                .title(house.getName())
                .snippet(house.getAddress())
                .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon("$" + fm.format(house.getPrice()))));
        currentUser = mMap.addMarker(markerOptions);
        currentUser.setTag(false);
    }

    private void initData() {
        data = new ArrayList<>();
        data.add(new House(1, "Blandford Hotel", 4, "21-23 P. Nhân Hòa, Thanh Xuân Trung, Thanh Xuân, Hà Nội, Việt Nam", 250, 21.0021109, 105.8061301));
        data.add(new House(1, "Blandford Hotel", 2, "N2E, số 60,khu, Đ. Lê Văn Lương, Trung Hòa Nhân Chính, Thanh Xuân, Hà Nội, Việt Nam", 250, 21.0030395, 105.7985941));
        data.add(new House(1, "Blandford Hotel", 1, "N2A, 2A Hoàng Minh Giám, Nhân Chính, Thanh Xuân, Hà Nội, Việt Nam", 210, 21.0036213, 105.7973736));
        data.add(new House(1, "Blandford Hotel", 3, "B52 P. Nguyễn Thị Định, Trung Hoà, Cầu Giấy, Hà Nội, Việt Nam", 12, 21.0099931, 105.8024565));
        data.add(new House(1, "Blandford Hotel", 4, "29 Ng. 2 P. Trần Kim Xuyến, Yên Hoà, Cầu Giấy, Hà Nội 100000, Việt Nam", 5, 21.0178902, 105.7954016));
        nearFromYouAdapterMap = new NearFromYouAdapterMap(data, new NearFromYouAdapterMap.Callback() {
            @Override
            public void onClickBookMark(House house) {

            }

            @Override
            public void onDirect(House house) {
                LatLng latLng = getLocationFromAddress(house.getAddress());
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f?q=%s", latLng.latitude, latLng.longitude, house.getAddress());
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        binding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerview.setAdapter(nearFromYouAdapterMap);

        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = getCurrentItem();// lấy vị trí recyclerview
                    House house = data.get(position);
//                    controller.getRootDistanceAndDuration(nameLocationYourSelf, house.getAddress(), distance, time);
                    selectCamera(house);
                    checkSelectItem = true;
                }
            }
        });
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) binding.recyclerview.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(NearFromYouMapsActivity.this);
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

    private void showMakerAndText(Location location) {
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(15)
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
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentUser = mMap.addMarker(markerOptions);
            currentUser.setTag(false);
        }
    }

    private void getAddress(double lati, double longi) {
        Geocoder geocoder = new Geocoder(NearFromYouMapsActivity.this, Locale.getDefault());
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

    private void fetchAddressFromLocation(Location locationYouSelf) {
        Intent intent = new Intent(this, FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, locationYouSelf);
        startService(intent);
    }

    public void selectCamera(House house) {
//        LatLng latLng = getLocationFromAddress(house.getAddress());
        LatLng latLng1 = new LatLng(house.getLat(), house.getLog());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng1)
                .zoom(15)
                .bearing(0)
                .tilt(40)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onCLickCLose() {
        bottomSheetFilterMap.dismiss();
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
//                toolBar2.setTitle(resultData.getString(Constants.DISTRICT) + resultData.getString(Constants.STATE));
                Log.e("Minh", resultData.getString(Constants.DISTRICT));
            } else {
                Toast.makeText(NearFromYouMapsActivity.this, resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
            binding.progressCircular.setVisibility(View.GONE);
            binding.contentTime.setVisibility(View.VISIBLE);
        }
    }

    private void showDialog() {
        bottomSheetFilterMap = new BottomSheetFilterMap(NearFromYouMapsActivity.this, R.style.MaterialDialogSheet, this);
        bottomSheetFilterMap.show();
        bottomSheetFilterMap.setCanceledOnTouchOutside(false);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}