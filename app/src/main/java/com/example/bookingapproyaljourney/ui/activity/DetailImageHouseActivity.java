package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.ui.adapter.GalleryImageAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class DetailImageHouseActivity extends AppCompatActivity implements GalleryImageAdapter.EventClick {

    private ArrayList<String> img = new ArrayList<>();
    private MaterialToolbar toolBar;
    private RecyclerView rcvImageGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image_house);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        rcvImageGallery = (RecyclerView) findViewById(R.id.rcvImageGallery);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        img = (ArrayList<String>) bundle.getSerializable("IMAGE_GALLERY");
        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolBar.setTitle("Gallery");
        toolBar.setPadding(30, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 3 == 0) return 2;
                else return 1;
            }
        });
        rcvImageGallery.setLayoutManager(gridLayoutManager);
        rcvImageGallery.setAdapter(new GalleryImageAdapter(img,this));
    }

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(this, DetailGalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION",id);
        bundle.putSerializable("LIST_IMAGE",img);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}