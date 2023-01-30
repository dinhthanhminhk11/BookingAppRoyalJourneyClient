package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityListFilterHotelBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.ui.adapter.ListFilterHotelAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetFilterHome;
import com.example.bookingapproyaljourney.view_model.ListFilterHotelViewModel;

import java.util.List;

public class ListFilterHotelActivity extends BaseActivity implements BottomSheetFilterHome.EventClick {

    private ActivityListFilterHotelBinding binding;
    private int countRoom;
    private MenuItem menuItem;
    private int countPerson;
    private int count_children;
    private int ageChildren;
    private String textSearch;
    private ListFilterHotelViewModel listFilterHotelViewModel;
    private ListFilterHotelAdapter listFilterHotelAdapter;
    private Location locationYouSelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFilterHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initView();
        initData();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences_user_count_room = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, MODE_PRIVATE);
        countRoom = sharedPreferences_user_count_room.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, 2);

        SharedPreferences sharedPreferences_user_count_person = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, MODE_PRIVATE);
        countPerson = sharedPreferences_user_count_person.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, 2);

        SharedPreferences sharedPreferences_user_count_children = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, MODE_PRIVATE);
        count_children = sharedPreferences_user_count_children.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, 2);

        SharedPreferences sharedPreferences_user_count_text_search = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH, MODE_PRIVATE);
        textSearch = sharedPreferences_user_count_text_search.getString(AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH, "");

        SharedPreferences sharedPreferences_user_age_children = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, MODE_PRIVATE);
        ageChildren = sharedPreferences_user_age_children.getInt(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, 1);

        getSupportActionBar().setTitle(textSearch);
        getSupportActionBar().setSubtitle(countRoom + " phòng, " + countPerson + " người lớn, " + count_children + " trẻ em"/*+ ageChildren + "tuổi)"*/);
    }

    private void initView() {
        binding.btnSearch.setOnClickListener(v -> {
            finish();
        });
        listFilterHotelAdapter = new ListFilterHotelAdapter();
        listFilterHotelAdapter.setHotelConsumer(o -> {
            Intent intent = new Intent(this, HotelActivity.class);
            intent.putExtra(AppConstant.HOTEL_EXTRA, o.get_id());
            startActivity(intent);
        });
        listFilterHotelViewModel = new ViewModelProvider(this).get(ListFilterHotelViewModel.class);

        binding.rcvSeeMoreBestForYou.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        binding.btnFilter.setOnClickListener(v -> {
            showDiaLog();
        });
    }

    private void initData() {
        double longi = Double.parseDouble(getIntent().getStringExtra(AppConstant.LOCATION_YOUR_SELF_LONG));
        double lati = Double.parseDouble(getIntent().getStringExtra(AppConstant.LOCATION_YOUR_SELF_LAT));
        locationYouSelf = new Location("locationYourSelf");
        locationYouSelf.setLongitude(longi);
        locationYouSelf.setLatitude(lati);
        checkFilter();
        listFilterHotelViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        if (textSearch.equals("Khách sạn gần nhất")) {
            if (locationYouSelf != null) {
                listFilterHotelViewModel.nearByUserLocationAndFilter(new LocationNearByRequest(locationYouSelf.getLongitude(), locationYouSelf.getLatitude(), 10000, ageChildren), ageChildren, countPerson / countRoom, count_children, countRoom);
            }
        } else {
            listFilterHotelViewModel.getListFilterHotelByHome(textSearch, ageChildren, countPerson / countRoom, count_children, countRoom);
        }

        listFilterHotelViewModel.getSearchModelMutableLiveData().observe(this, new Observer<List<Hotel>>() {
            @Override
            public void onChanged(List<Hotel> hotels) {
                if (hotels.size() > 0) {
                    listFilterHotelAdapter.setDataHotel(hotels);
                    binding.rcvSeeMoreBestForYou.setAdapter(listFilterHotelAdapter);
                } else {
                    binding.contentNullList.setVisibility(View.VISIBLE);
                    binding.rcvSeeMoreBestForYou.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showDiaLog() {
        BottomSheetFilterHome bottomSheetFilterHome = new BottomSheetFilterHome(this, R.style.MaterialDialogSheet, this);
        bottomSheetFilterHome.show();
        bottomSheetFilterHome.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onCLickFilter(String giaBd, String giaKt, String sao, String idLoai) {
        if (Integer.parseInt(giaBd) == 120000 && Integer.parseInt(giaKt) == 5000000 && Integer.parseInt(sao) == 5) {
            binding.btnFilter.setImageResource(R.drawable.ic_filter_null);
        } else {
            binding.btnFilter.setImageResource(R.drawable.ic_filter_not_null);
        }
        if (textSearch.equals("Khách sạn gần nhất")) {
            if (locationYouSelf != null) {
                listFilterHotelViewModel.nearByUserLocationAndFilterAndPriceAndStar(new LocationNearByRequest(locationYouSelf.getLongitude(), locationYouSelf.getLatitude(), 10000, ageChildren), ageChildren, countPerson / countRoom, count_children, countRoom, Integer.parseInt(giaBd), Integer.parseInt(giaKt), Integer.parseInt(sao));
            }
        } else {
            listFilterHotelViewModel.getFilterHotelAndStarAndPrice(textSearch, ageChildren, countPerson / countRoom, count_children, countRoom, Integer.parseInt(giaBd), Integer.parseInt(giaKt), Integer.parseInt(sao));
        }
    }

    private void checkFilter() {
        SharedPreferences sharedPreferences_user_data_start_price = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_START_PRICE, MODE_PRIVATE);
        int giaBd = sharedPreferences_user_data_start_price.getInt(AppConstant.SHAREDPREFERENCES_USER_START_PRICE, 120000);

        SharedPreferences sharedPreferences_user_data_end_price = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_END_PRICE, MODE_PRIVATE);
        int giaKt = sharedPreferences_user_data_end_price.getInt(AppConstant.SHAREDPREFERENCES_USER_END_PRICE, 5000000);

        SharedPreferences sharedPreferences_user_data_star = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_STAR, MODE_PRIVATE);
        int sao = sharedPreferences_user_data_star.getInt(AppConstant.SHAREDPREFERENCES_USER_STAR, 5);

        if (giaBd == 120000 && giaKt == 5000000 && sao == 5) {
            binding.btnFilter.setImageResource(R.drawable.ic_filter_null);
        } else {
            binding.btnFilter.setImageResource(R.drawable.ic_filter_not_null);
        }
    }
}