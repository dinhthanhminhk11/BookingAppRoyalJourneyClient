package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivitySeeMoreBestForYouBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.ui.activity.Hotel.HotelActivity;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapterNotNull;
import com.example.bookingapproyaljourney.view_model.SeeMoreBestForYouViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.function.Consumer;

public class SeeMoreBestForYouActivity extends BaseActivity {
    private List<House> list;
    private BestForYouAdapterNotNull bestForYouAdapterNotNull;
    private MaterialToolbar toolBar;
    private RecyclerView rcvSeeMoreBestForYou;
    private RelativeLayout contentBackground;
    private ActivitySeeMoreBestForYouBinding binding;

    private SeeMoreBestForYouViewModel seeMoreBestForYouViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeMoreBestForYouBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        seeMoreBestForYouViewModel = new ViewModelProvider(this).get(SeeMoreBestForYouViewModel.class);

        rcvSeeMoreBestForYou = (RecyclerView) findViewById(R.id.rcvSeeMoreBestForYou);
        contentBackground = (RelativeLayout) findViewById(R.id.contentBackground);
        rcvSeeMoreBestForYou.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle(R.string.BestForYou_homeFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        bestForYouAdapterNotNull = new BestForYouAdapterNotNull();
        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        seeMoreBestForYouViewModel.getHotelReponseMutableLiveData().observe(this, new Observer<HotelReponse>() {
            @Override
            public void onChanged(HotelReponse hotelReponse) {
                if(hotelReponse instanceof HotelReponse){

                    bestForYouAdapterNotNull.setDataHouse(hotelReponse.getDatapros());
                    bestForYouAdapterNotNull.setConsumer(o ->{
                        if(o instanceof Hotel){
                            Intent intent = new Intent(SeeMoreBestForYouActivity.this, HotelActivity.class);
                            intent.putExtra(AppConstant.HOTEL_EXTRA, o.get_id());
                            startActivity(intent);
                        }
                    });
                    rcvSeeMoreBestForYou.setAdapter(bestForYouAdapterNotNull);
                }
            }
        });

        seeMoreBestForYouViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        seeMoreBestForYouViewModel.getListAllHotel();
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            toolBar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            toolBar.setTitleTextColor(Color.WHITE);
            toolBar.setSubtitleTextColor(Color.WHITE);
            toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            bestForYouAdapterNotNull.setColor(Color.WHITE, Color.WHITE);
        } else {
            toolBar.setBackgroundColor(Color.WHITE);
            toolBar.setTitleTextColor(Color.BLACK);
            toolBar.setSubtitleTextColor(Color.BLACK);
            toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            contentBackground.setBackgroundColor(Color.WHITE);
            bestForYouAdapterNotNull.setColor(Color.BLACK, this.getResources().getColor(R.color.color_858585));
        }
    }

}