package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivitySearchHotelBinding;
import com.example.bookingapproyaljourney.model.search.SearchModel;
import com.example.bookingapproyaljourney.ui.adapter.SearchAdapter;
import com.example.bookingapproyaljourney.view_model.SearchHotelViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class SearchHotelActivity extends BaseActivity implements View.OnClickListener {
    public static String nameLocation = "";
    private static SearchHotelActivity instance;
    private Consumer consumer;
    private SearchAdapter searchAdapter;
    private List<SearchModel> listSearchModel;
    private List<SearchModel> listPrivate;
    private SearchHotelViewModel searchHotelViewModel;

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
        initData();
        initView();
    }

    private void initData() {
        listSearchModel = new ArrayList<>();
        listPrivate = new ArrayList<>();
        listSearchModel.add(new SearchModel("Hà Nội", 1));
        listSearchModel.add(new SearchModel("Hải Dương", 1));
        listSearchModel.add(new SearchModel("Hưng Yên", 1));
        listSearchModel.add(new SearchModel("Quảng Ninh", 1));
        listSearchModel.add(new SearchModel("Bắc Giang", 1));
        listSearchModel.add(new SearchModel("Bắc Ninh", 1));
        listSearchModel.add(new SearchModel("Đà Nẵng", 1));
        listSearchModel.add(new SearchModel("Đà Lạt", 1));
    }

    private void initView() {
        searchHotelViewModel = new ViewModelProvider(this).get(SearchHotelViewModel.class);
        searchAdapter = new SearchAdapter();
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

        binding.listSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

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
                if (binding.etSearchHomeFragment.getText().toString().length() > 0) {
                    binding.contentSearch.setVisibility(View.GONE);
                    binding.listSearch.setVisibility(View.VISIBLE);
                    binding.contentViewLine.setVisibility(View.GONE);
                    binding.contentBottom.setVisibility(View.GONE);
                    searchHotelViewModel.getListSearchLocationHotel(binding.etSearchHomeFragment.getText().toString().trim());
                } else {
                    binding.contentSearch.setVisibility(View.VISIBLE);
                    binding.listSearch.setVisibility(View.GONE);
                    binding.contentViewLine.setVisibility(View.VISIBLE);
                    binding.contentBottom.setVisibility(View.VISIBLE);
                }
            }
        });

        searchHotelViewModel.getSearchModelMutableLiveData().observe(this, new Observer<List<SearchModel>>() {
            @Override
            public void onChanged(List<SearchModel> searchModel) {
                searchAdapter.setData(searchModel);
                searchAdapter.setConsumer(o -> {
                    if (o instanceof SearchModel) {
                        if (((SearchModel) o).getType() == 2) {
                            Intent intent = new Intent(SearchHotelActivity.this, HotelActivity.class);
                            intent.putExtra(AppConstant.HOTEL_EXTRA, ((SearchModel) o).getIdHotel());
                            startActivity(intent);
                        } else {
                            onClickTrendLocation(((SearchModel) o).getNameHotel());
                        }
                    }
                });
                searchAdapter.setTextHighLight(binding.etSearchHomeFragment.getText().toString().trim());
                binding.listSearch.setAdapter(searchAdapter);
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