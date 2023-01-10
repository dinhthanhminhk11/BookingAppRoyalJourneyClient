package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityHotelBinding;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.GalleryAdapter;
import com.example.bookingapproyaljourney.ui.adapter.RoomHotelAdapter;
import com.example.bookingapproyaljourney.view_model.HotelInfoViewModel;

public class HotelActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityHotelBinding binding;
    private MenuItem menuItem;
    private boolean isClickSpeed = true;
    private HotelInfoViewModel hotelInfoViewModel;
    private RequestOptions options;
    private String phone;
    private ConvenientAdapter convenientAdapter;
    private GalleryAdapter galleryAdapter;
    private RoomHotelAdapter roomHotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initView();
    }

    private void initView() {
        convenientAdapter = new ConvenientAdapter(this);
        roomHotelAdapter = new RoomHotelAdapter();
        binding.btPhone.setOnClickListener(this);

        binding.rcvConvenient.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);

        hotelInfoViewModel = new ViewModelProvider(this).get(HotelInfoViewModel.class);
        hotelInfoViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        hotelInfoViewModel.getHotelMutableLiveData().observe(this, new Observer<HotelById>() {
            @Override
            public void onChanged(HotelById item) {
                if (item instanceof HotelById) {
                    phone = item.getDataUser().getPhone();
                    binding.tvNameHotel.setText(item.getDataHotel().getName());
                    binding.tvAddress.setText(item.getDataHotel().getSonha() + ", " + item.getDataHotel().getXa() + ", " + item.getDataHotel().getHuyen() + ", " + item.getDataHotel().getTinh());
                    binding.tvSumDienTich.setText(item.getDataHotel().getDienTich() + " m²");
                    binding.priceRoom.setText(item.getDataHotel().getGiaDaoDong());
                    binding.ContentHouse.setText(item.getDataHotel().getMota());
                    binding.tvTimeNhanPhong.setText(item.getDataHotel().getTimeDat());
                    binding.dayEnd.setText(item.getDataHotel().getTimeTra());
                    binding.textPolicy.setText(item.getDataHotel().getChinhsach());

                    Glide.with(HotelActivity.this).load(item.getDataHotel().getImages().get(0)).apply(options).into(binding.ivimgHotel);

                    binding.NameManage.setText(item.getDataUser().getName());
                    Glide.with(HotelActivity.this).load(item.getDataUser().getImage()).apply(options).into(binding.imgManage);

                    convenientAdapter.setConvenientTestList(item.getDataHotel().getTienNghiKS());
                    binding.rcvConvenient.setAdapter(convenientAdapter);

                    galleryAdapter = new GalleryAdapter(HotelActivity.this, item.getDataHotel().getImages());
                    binding.rcvGallery.setAdapter(galleryAdapter);

                    if (item.getDataHotel().isChinhSachHuy()) {
                        binding.cancelTrue.setVisibility(View.VISIBLE);
                    } else {
                        binding.cancelFalse.setVisibility(View.VISIBLE);
                    }
                    roomHotelAdapter.setData(item.getDataRoom());
                    binding.rcvRoom.setAdapter(roomHotelAdapter);

                    roomHotelAdapter.setConsumer(o -> {
                        if (o instanceof Room) {
                            startActivity(new Intent(HotelActivity.this, RoomInfoActivity.class));
                        }
                    });
                }
            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);
        binding.toolBar.setBackground(null);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itemboomak, menu);
        menuItem = menu.findItem(R.id.iconbookmak);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.iconbookmak) {
            if (isClickSpeed) {
                item.setIcon(R.drawable.ic_baseline_bookmark_24_white_full);
                isClickSpeed = false;
            } else {
                menuItem.setIcon(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar);
                isClickSpeed = true;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hotelInfoViewModel.getHotelById("63ba4351a272c793b1222c4f");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPhone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
        }
    }
}