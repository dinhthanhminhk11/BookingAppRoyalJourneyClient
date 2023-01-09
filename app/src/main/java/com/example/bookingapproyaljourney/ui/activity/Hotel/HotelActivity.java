package com.example.bookingapproyaljourney.ui.activity.Hotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.databinding.ActivityHotelBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.view_model.HotelInfoViewModel;

public class HotelActivity extends AppCompatActivity {
    private ActivityHotelBinding binding;
    private MenuItem menuItem;
    private boolean isClickSpeed = true;
    private HotelInfoViewModel hotelInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initView();
    }

    private void initView() {
        hotelInfoViewModel = new ViewModelProvider(this).get(HotelInfoViewModel.class);
        hotelInfoViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        hotelInfoViewModel.getHotelMutableLiveData().observe(this, new Observer<Hotel>() {
            @Override
            public void onChanged(Hotel item) {
                if (item instanceof Hotel) {
                    binding.tvNameHotel.setText(item.getName());
                    binding.tvAddress.setText(item.getSonha() + ", " + item.getXa() + ", " + item.getHuyen() + ", " + item.getTinh());
                    binding.tvSumDienTich.setText(item.getDienTich()+" m²");
                    binding.priceRoom.setText(item.getGiaDaoDong());
                    binding.titleMota.setText(item.getMota());
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
}