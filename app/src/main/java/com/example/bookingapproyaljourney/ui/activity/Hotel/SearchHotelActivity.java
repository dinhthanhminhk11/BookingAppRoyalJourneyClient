package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bookingapproyaljourney.databinding.ActivitySearchHotelBinding;
import com.example.bookingapproyaljourney.ui.adapter.TrendHotelAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchHotelActivity extends AppCompatActivity {

    private ActivitySearchHotelBinding binding;
    private List<String> listLocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    private void initData() {
        listLocationName = new ArrayList<>();
        listLocationName.add("Hà nội");
        listLocationName.add("Phú quốc");
        listLocationName.add("Nha trang");
        listLocationName.add("Đà nẵng");
        listLocationName.add("Sa pa");
        listLocationName.add("Phan thiết");
        listLocationName.add("Hội an");
        binding.listTrend.setLayoutManager(new GridLayoutManager(this, 2));
        TrendHotelAdapter trendHotelAdapter = new TrendHotelAdapter(listLocationName, o -> {

        });
        binding.listTrend.setAdapter(trendHotelAdapter);
    }

    private void initView() {
        binding.close.setOnClickListener(v -> {
            finish();
        });

        binding.contentHistory.setVisibility(View.GONE);
    }
}