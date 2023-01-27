package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityListFilterHotelBinding;

public class ListFilterHotelActivity extends BaseActivity {

    private ActivityListFilterHotelBinding binding;
    private int countRoom;
    private int countPerson;
    private int count_children;
    private int ageChildren;
    private String textSearch;

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
        getSupportActionBar().setSubtitle(countRoom + " phòng, " + countPerson + " người lớn, " + count_children + " trẻ em");
    }

    private void initView() {
    }

    private void initData() {
    }
}