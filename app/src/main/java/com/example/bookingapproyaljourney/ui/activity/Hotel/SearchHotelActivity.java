package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.databinding.ActivitySearchHotelBinding;

import java.util.function.Consumer;


public class SearchHotelActivity extends BaseActivity implements View.OnClickListener {
    public static String nameLocation = "Khách sạn gần nhất";
    private static SearchHotelActivity instance;
    private Consumer consumer;

    public SearchHotelActivity() {
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public static SearchHotelActivity getInstance() {
        if (instance == null) {
            instance = new SearchHotelActivity();
        }
        return instance;
    }

    private ActivitySearchHotelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        binding.btnPhuQuoc.setOnClickListener(this);
        binding.btnNhaTrang.setOnClickListener(this);
        binding.btnBaRia.setOnClickListener(this);
        binding.btnDaLat.setOnClickListener(this);
        binding.btnDaNang.setOnClickListener(this);
        binding.btnSaPa.setOnClickListener(this);
        binding.btnPhanThiet.setOnClickListener(this);
        binding.btnHaLong.setOnClickListener(this);
        binding.btnHoChiMinh.setOnClickListener(this);
        binding.btnHoiAn.setOnClickListener(this);
        binding.contentSearch.setOnClickListener(this);

        binding.close.setOnClickListener(v -> {
            finish();
        });

        binding.contentHistory.setVisibility(View.GONE);
        binding.etSearchHomeFragment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPhuQuoc:
//                nameLocation = "PHus quoocs";
                onClickTrendLocation("Phú Quốc");
                break;
            case R.id.btnNhaTrang:
                onClickTrendLocation("Nha Trang");

                break;
            case R.id.btnBaRia:
                onClickTrendLocation("Bà Rịa - Vũng Tàu");

                break;
            case R.id.btnDaLat:
                onClickTrendLocation("Đà Lạt");

                break;
            case R.id.btnDaNang:
                onClickTrendLocation("Đà Nẵng");

                break;
            case R.id.btnSaPa:
                onClickTrendLocation("Sa Pa");

                break;
            case R.id.btnPhanThiet:
                onClickTrendLocation("Phan Thiết");

                break;
            case R.id.btnHaLong:
                onClickTrendLocation("Hạ Long");

                break;
            case R.id.btnHoChiMinh:
                onClickTrendLocation("Hồ Chí Minh");

                break;
            case R.id.btnHoiAn:
                onClickTrendLocation("Hội An");

                break;
            case R.id.contentSearch:
                onClickTrendLocation("Khách sạn gần nhất");

                break;
        }
    }

    @SuppressLint("NewApi")
    private void onClickTrendLocation(String textLocation) {
        nameLocation = textLocation;
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("CheckSuccess", "222222222");
        startActivity(intent);
    }
}