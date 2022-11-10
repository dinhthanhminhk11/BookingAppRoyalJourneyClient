package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapterNotNull;
import com.google.android.material.appbar.MaterialToolbar;
import com.example.bookingapproyaljourney.callback.UpdateRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class SeeMoreBestForYouActivity extends AppCompatActivity implements UpdateRecyclerView, BestForYouAdapterNotNull.Listernaer {
    private List<House> list;
    private BestForYouAdapterNotNull bestForYouAdapterNotNull;
    private MaterialToolbar toolBar;
    private RecyclerView rcvSeeMoreBestForYou;
    private LottieAnimationView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_best_for_you);

        rcvSeeMoreBestForYou = (RecyclerView) findViewById(R.id.rcvSeeMoreBestForYou);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("Best For You");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void callbacksNearFromYou(int position, HouseNearestByUserResponse houseNearestByUserResponse) {

    }

    @Override
    public void callbacksBestForYou(int position, CategoryBestForYouResponse categoryBestForYouResponse) {
        bestForYouAdapterNotNull.setDataHouse(categoryBestForYouResponse.getHouses());
        rcvSeeMoreBestForYou.setAdapter(bestForYouAdapterNotNull);
    }


    @Override
    public void callLoading(int view) {
        progressBar.setVisibility(view);
    }

    @Override
    public void callBackNull(CategoryBestForYouResponse categoryBestForYouResponse) {
        bestForYouAdapterNotNull.setDataHouse(categoryBestForYouResponse.getHouses());
        rcvSeeMoreBestForYou.setAdapter(bestForYouAdapterNotNull);
    }

    @Override
    public void onClick(House house) {
        Intent intent = new Intent(this, DetailProductActivity.class);
        intent.putExtra(AppConstant.HOUSE_EXTRA, house.getId());
        startActivity(intent);
    }
}