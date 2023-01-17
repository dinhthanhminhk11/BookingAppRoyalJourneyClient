package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.ui.adapter.DetailGalleryAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class DetailGalleryActivity extends BaseActivity implements DetailGalleryAdapter.EventClick {

    private ImageButton imgCancelGallery;
    private TextView tvCountGallery;
    private TextView tvNumberChange;
    private ConstraintLayout constraintDetailImage;
    private ViewPager2 viewPageGallery;
    private View view;
    private ArrayList<String> listImage = new ArrayList<>();
    private int position;
    boolean clicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gallery);
        imgCancelGallery = (ImageButton) findViewById(R.id.imgCancelGallery);
        tvCountGallery = (TextView) findViewById(R.id.tvCountGallery);
        tvNumberChange = (TextView) findViewById(R.id.tvNumberChange);
        viewPageGallery = (ViewPager2) findViewById(R.id.viewPageGallery);
        constraintDetailImage = (ConstraintLayout) findViewById(R.id.constraintDetailImage);
        view = (View) findViewById(R.id.view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        listImage = (ArrayList<String>) bundle.getSerializable("LIST_IMAGE");
        position = bundle.getInt("POSITION");
        imgCancelGallery.setOnClickListener(v->onBackPressed());
        viewPageGallery.setAdapter(new DetailGalleryAdapter(listImage,this));
        viewPageGallery.setClipToPadding(true);
        viewPageGallery.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.HORIZONTAL));
        viewPageGallery.setCurrentItem(position);
        tvCountGallery.setText("/ "+listImage.size());
        tvNumberChange.setText(position+1+"");

        viewPageGallery.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvNumberChange.setText(position+1+"");
            }
        });
    }

    @Override
    public void onClick() {
        if(clicked){
            constraintDetailImage.setBackgroundResource(R.color.black);
            imgCancelGallery.setVisibility(View.GONE);
            viewPageGallery.setUserInputEnabled(false);
            view.setVisibility(View.GONE);
            clicked = false;
        }else {
            constraintDetailImage.setBackgroundResource(R.color.white);
            imgCancelGallery.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            clicked = true;
            viewPageGallery.setUserInputEnabled(true);
        }

    }
}