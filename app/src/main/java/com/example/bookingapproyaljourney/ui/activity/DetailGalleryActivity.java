package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.ui.adapter.DetailGalleryAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class DetailGalleryActivity extends AppCompatActivity {

    private ImageButton imgCancelGallery;
    private TextView tvCountGallery;
    private View view;
    private ViewPager2 viewPageGallery;
    private ArrayList<String> listImage = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gallery);
        imgCancelGallery = (ImageButton) findViewById(R.id.imgCancelGallery);
        tvCountGallery = (TextView) findViewById(R.id.tvCountGallery);
        view = (View) findViewById(R.id.view);
        viewPageGallery = (ViewPager2) findViewById(R.id.viewPageGallery);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        listImage = (ArrayList<String>) bundle.getSerializable("LIST_IMAGE");
        position = bundle.getInt("POSITION");
        imgCancelGallery.setOnClickListener(v->onBackPressed());
        viewPageGallery.setAdapter(new DetailGalleryAdapter(listImage));
        viewPageGallery.setClipToPadding(true);
    }
}