package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityRoomInfoBinding;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ImageAutoSliderAdapter;
import com.example.bookingapproyaljourney.view_model.RoomInfoViewModel;
import com.example.libraryautoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.libraryautoimageslider.SliderAnimations;
import com.example.libraryautoimageslider.SliderView;
import com.google.android.material.appbar.AppBarLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class RoomInfoActivity extends AppCompatActivity {
    private ActivityRoomInfoBinding binding;
    private RoomInfoViewModel roomInfoViewModel;
    private ImageAutoSliderAdapter imageAutoSliderAdapter;
    private NumberFormat fm = new DecimalFormat("#,###");
    private ConvenientAdapter convenientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();

    }

    private void initToolbar() {
        convenientAdapter = new ConvenientAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.collapseToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        binding.collapseToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if ((binding.collapseToolbarLayout.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(binding.collapseToolbarLayout))) {

                    binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        roomInfoViewModel.getRoomById("63ba7b3128b619e3e26e5825");
    }

    private void initView() {
        roomInfoViewModel = new ViewModelProvider(this).get(RoomInfoViewModel.class);
        roomInfoViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        roomInfoViewModel.getRoomMutableLiveData().observe(this, new Observer<Room>() {
            @Override
            public void onChanged(Room item) {
                imageAutoSliderAdapter = new ImageAutoSliderAdapter(item.getImages());
                binding.imateside.setSliderAdapter(imageAutoSliderAdapter);
                binding.imateside.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                binding.imateside.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                binding.imateside.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                binding.imateside.setIndicatorSelectedColor(Color.WHITE);
                binding.imateside.setIndicatorUnselectedColor(Color.GRAY);
                binding.imateside.setScrollTimeInSec(4); //set scroll delay in seconds :
                binding.imateside.startAutoCycle();

                binding.collapseToolbarLayout.setTitle(item.getName());

                binding.countImage.setText(item.getImages().size() + "");
                binding.tvCountPerson.setText("Ở " + (item.getMaxTreEm() + item.getMaxNguoiLon()) + " người");
                binding.text2.setText("• Phòng chứa tối đa " + (item.getMaxTreEm() + item.getMaxNguoiLon()) + " người");
                binding.text1.setText("• Cho phép " + item.getMaxNguoiLon() + " người lớn");
                binding.text3.setText("• Cho phép tối đa " + item.getMaxTreEm() + " trẻ em đi theo");
                binding.tvSoGiuong.setText(item.getBedroom().get(0).getName());
                binding.tvDientich.setText(item.getDienTich() + " m²");
                binding.tvSoPhong.setText("Còn " + item.getSoPhong() + " phòng khác giống phòng này");
                binding.tvGia.setText(fm.format(item.getPrice()) + " đ");
                binding.contentMota.setText(item.getMota());
                convenientAdapter.setConvenientTestList(item.getTienNghiPhong());
                binding.recyclerView.setAdapter(convenientAdapter);
            }
        });

        binding.btnThem.setOnClickListener(v -> {
            startActivity(new Intent(RoomInfoActivity.this, BookingActivity.class));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}