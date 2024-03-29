package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Image;
import com.example.bookingapproyaljourney.ui.adapter.ImageAdapter;

import com.example.bookingapproyaljourney.view_model.DetailProductViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private ViewPager viewPage;
    private TextView tvBd;
    private TextView tvTong;
    private RecyclerView rcvImage;
    private ImageAdapter imageAdapter;
    private DetailProductViewModel detailProductViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        viewPage = (ViewPager) findViewById(R.id.viewPage);
        tvBd = (TextView) findViewById(R.id.tv_bd);
        tvTong = (TextView) findViewById(R.id.tv_tong);
        rcvImage = (RecyclerView) findViewById(R.id.rcvImage);




    }
    private void initData(String id){
        detailProductViewModel.getHouseById(id).observe(this,item->{
            rcvImage.setHasFixedSize(true);
            rcvImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
            ImageAdapter imageAdapter = new ImageAdapter(this,item.getImages());
            rcvImage.setAdapter(imageAdapter);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);

        });
    }
//    private List<Image> getListImage(){
//        List<Image> list = new ArrayList<>();
////        list.add(new Image(R.drawable.imagetest,R.id.imgBackground));
////        list.add(new Image(R.drawable.imagetest,R.id.imgBackground));
////        list.add(new Image(R.drawable.imagetest,R.id.imgBackground));
////        list.add(new Image(R.drawable.imagetest,R.id.imgBackground));
////        list.add(new Image(R.drawable.imagetest,R.id.imgBackground));
////        list.add(new Image(R.drawable.imagetest,R.id.imgBackground));
//        return list;
//    }
}