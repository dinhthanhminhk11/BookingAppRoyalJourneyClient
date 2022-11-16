package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityFeedBackBinding;

public class FeedBackActivity extends AppCompatActivity {
    private ActivityFeedBackBinding binding;
    private int sao =0;
    private String id_boss="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        id_boss = intent.getStringExtra("ID_BOSS");
        String name_boss = intent.getStringExtra("NAME_BOSS");
        String img_boss = intent.getStringExtra("IMG_BOSS");
        Glide.with(this).load(img_boss).into(binding.imgBoss);
        binding.tvNameBoss.setText(name_boss);
        binding.imgBackFB.setOnClickListener(v->onBackPressed());
        binding.imgStar1.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 1;
        });
        binding.imgStar2.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 2;
        });
        binding.imgStar3.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 3;
        });
        binding.imgStar4.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 4;
        });
        binding.imgStar5.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_click);
            sao = 5;
        });

        binding.btnSubmit.setOnClickListener(v ->{
            Toast.makeText(this, sao+" sao", Toast.LENGTH_SHORT).show();
        });
    }
}