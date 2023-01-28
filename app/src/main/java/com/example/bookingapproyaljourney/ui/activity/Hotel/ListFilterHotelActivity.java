package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityListFilterHotelBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.ui.adapter.ListFilterHotelAdapter;
import com.example.bookingapproyaljourney.view_model.ListFilterHotelViewModel;

import java.util.List;

public class ListFilterHotelActivity extends BaseActivity {

    private ActivityListFilterHotelBinding binding;
    private int countRoom;
    private int countPerson;
    private int count_children;
    private int ageChildren;
    private String textSearch;
    private ListFilterHotelViewModel listFilterHotelViewModel;
    private ListFilterHotelAdapter listFilterHotelAdapter;

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
        binding.btnSearch.setOnClickListener(v->{
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
    }

    private void initData() {
        listFilterHotelViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
        listFilterHotelViewModel.getListFilterHotelByHome(textSearch, ageChildren, countPerson, count_children, countRoom);
        listFilterHotelViewModel.getSearchModelMutableLiveData().observe(this, new Observer<List<Hotel>>() {
            @Override
            public void onChanged(List<Hotel> hotels) {
                if (hotels.size() > 0) {
                    listFilterHotelAdapter.setDataHotel(hotels);
                    binding.rcvSeeMoreBestForYou.setAdapter(listFilterHotelAdapter);
                } else {
                    binding.contentNullList.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}