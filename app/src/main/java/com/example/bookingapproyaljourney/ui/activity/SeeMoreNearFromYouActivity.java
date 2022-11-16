package com.example.bookingapproyaljourney.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.Constants;
import com.example.bookingapproyaljourney.databinding.ActivitySeeMoreNearFromYouBinding;
import com.example.bookingapproyaljourney.map.FetchAddressIntentServices;
import com.example.bookingapproyaljourney.repository.MapRepository;
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
import java.util.List;
import java.util.Locale;

public class SeeMoreNearFromYouActivity extends AppCompatActivity implements OnMapReadyCallback {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeMoreNearFromYouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initView();
    }

    private void initView() {
        mapRepository = new MapRepository();
        setSupportActionBar(binding.toolBar2);
        getSupportActionBar().setTitle("Near From You");
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

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(@NonNull Marker marker) {
//                if (marker.getPosition().latitude == latLngLocationYourSelf.latitude && marker.getPosition().longitude == latLngLocationYourSelf.longitude) {
//                    return false;
//                }
////                markerView = getLayoutInflater().inflate(R.layout.marker_ver2, null);
////                LatLng latLng = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
////                iconGenerator.setContentView(markerView);
////                iconGenerator.setTextAppearance(R.style.iconGenText);
////                marker.setIcon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon()));
//                binding.recyclerview.smoothScrollToPosition(Integer.parseInt(marker.getId().substring(1)) - 1);
//                return false;
//            }
//        });

//        mapActivityNearByFromYouViewModel.getNearestByUserOnMapResultMutableData().observe(this, new Observer<HouseNearestByUserResponse>() {
//            @Override
//            public void onChanged(HouseNearestByUserResponse houseNearestByUserResponse) {
//                for (DataMap house : houseNearestByUserResponse.getDataMaps()
//                ) {
//                    drawMakerListDataHouse(house);
//                }
//                initData(houseNearestByUserResponse.getDataMaps());
////                mapRepository.getRootDistanceAndDuration(nameLocationYourSelf, houseNearestByUserResponse.getDataMaps().get(0).getData().getNameLocation(), binding.distance, binding.time);
//            }
//        });
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
//                            mapActivityNearByFromYouViewModel.getHouseNearestByUserOnPosition(latLngLocationYourSelf);
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
}