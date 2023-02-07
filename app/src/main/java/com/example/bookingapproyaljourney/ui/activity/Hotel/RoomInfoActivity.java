package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityRoomInfoBinding;
import com.example.bookingapproyaljourney.model.hotel.Bedroom;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ImageAutoSliderAdapter;
import com.example.bookingapproyaljourney.view_model.RoomInfoViewModel;
import com.example.libraryautoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.libraryautoimageslider.SliderAnimations;
import com.example.libraryautoimageslider.SliderView;
import com.google.android.material.appbar.AppBarLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class RoomInfoActivity extends BaseActivity {
    private ActivityRoomInfoBinding binding;
    private RoomInfoViewModel roomInfoViewModel;
    private ImageAutoSliderAdapter imageAutoSliderAdapter;
    private NumberFormat fm = new DecimalFormat("#,###");
    private ConvenientAdapter convenientAdapter;
    private String idRoom;
    private int ageChildren;
    private boolean cancelBooking;
    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idRoom = getIntent().getStringExtra(AppConstant.ROOM_EXTRA);
        ageChildren = Integer.parseInt(getIntent().getStringExtra(AppConstant.ROOM_AGE_CHILDREN));
        cancelBooking = Boolean.parseBoolean(getIntent().getStringExtra(AppConstant.ROOM_CANCEL_BOOKING));
        initToolbar();
        initView();

        //        thay đổi Theme
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }
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
        sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        roomInfoViewModel.getRoomById(idRoom);
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
                binding.tvCountPerson.setText(RoomInfoActivity.this.getString(R.string.Stay) + " " + (item.getMaxTreEm() + item.getMaxNguoiLon()) + " " + RoomInfoActivity.this.getString(R.string.textBest3));
                binding.text2.setText("• " + RoomInfoActivity.this.getString(R.string.Maximum_occupancy_of) + " " + (item.getMaxTreEm() + item.getMaxNguoiLon()) + " " + RoomInfoActivity.this.getString(R.string.textBest3));
                binding.text1.setText("• " + RoomInfoActivity.this.getString(R.string.Allowed) + " " + item.getMaxNguoiLon() + " " + RoomInfoActivity.this.getString(R.string.adult));
                binding.text3.setText("• " + RoomInfoActivity.this.getString(R.string.Up_to) + " " + item.getMaxTreEm() + " " + RoomInfoActivity.this.getString(R.string.children_allowed));

                for (Bedroom bedroom : item.getBedroom()
                ) {
                    String content = "";
                    content += bedroom.getName() + ", ";
                    binding.tvSoGiuong.append(content);
                }

//                binding.tvSoGiuong.setText(item.getBedroom().get(0).getName());
                binding.tvDientich.setText(item.getDienTich() + " m²");
                binding.tvSoPhong.setText(" " + RoomInfoActivity.this.getString(R.string.There_are) + " " + item.getSoPhong() + " " + RoomInfoActivity.this.getString(R.string.other_rooms_like_this_one));
                binding.tvGia.setText(fm.format(item.getPrice()) + " đ");
                binding.text4.setText(" " + RoomInfoActivity.this.getString(R.string.Children_over_the_age_of) + " " + ageChildren + " " + RoomInfoActivity.this.getString(R.string.will_be_counted_as_adults));
                binding.contentMota.setText(item.getMota());
                convenientAdapter.setConvenientTestList(item.getTienNghiPhong());
                binding.recyclerView.setAdapter(convenientAdapter);

                if (cancelBooking) {
                    binding.canceltrue.setVisibility(View.VISIBLE);
                } else {
                    binding.cancelfalse.setVisibility(View.VISIBLE);
                }
            }
        });

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dia_log_comfirm_logout);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);

        Button login = (Button) dialog.findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent checkLogin = new Intent(RoomInfoActivity.this, LoginActivity.class);
            checkLogin.putExtra(AppConstant.CHECK_LOGIN_TOKEN_NULL, "checkNotSignIn");
            startActivity(checkLogin);
            dialog.dismiss();
        });

        binding.btnThem.setOnClickListener(v -> {
            if (token.equals("")) {
                dialog.show();
            } else {
                Intent intent = new Intent(RoomInfoActivity.this, BookingActivity.class);
                intent.putExtra(AppConstant.ROOM_EXTRA, idRoom);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.layoutScrollView.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.layoutLinearRoomInfo.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imgItemnguoi.setColorFilter(getResources().getColor(R.color.white));
            binding.tvCountPerson.setTextColor(Color.WHITE);
            binding.text2.setTextColor(Color.WHITE);
            binding.text1.setTextColor(Color.WHITE);
            binding.text3.setTextColor(Color.WHITE);
            binding.imgItemBedRoom.setColorFilter(getResources().getColor(R.color.white));
            binding.tvSoGiuong.setTextColor(Color.WHITE);
            binding.imgItemDienTich.setColorFilter(getResources().getColor(R.color.white));
            binding.tvDientich.setTextColor(Color.WHITE);
            binding.text5.setTextColor(Color.WHITE);
            binding.tvGia.setTextColor(Color.WHITE);
            binding.contentContentHotel.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.tvMota.setTextColor(Color.WHITE);
            binding.contentMota.setTextColor(Color.WHITE);
            binding.layoutChinhSachHuy.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.tvTextCSH.setTextColor(Color.WHITE);
            binding.canceltrue.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.cancelfalse.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.layoutTienNghiPhong.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.viewCSH.setBackgroundColor(Color.WHITE);
            binding.viewCSH1.setBackgroundColor(Color.WHITE);
            binding.viewCSH2.setBackgroundColor(Color.WHITE);
            binding.tvChinhSachHuyPhong.setTextColor(Color.WHITE);
            binding.tvChinhSachHuyPhong1.setTextColor(Color.WHITE);
            binding.tvChinhSachHuyPhong2.setTextColor(Color.WHITE);
            binding.tvTienNghiPhong.setTextColor(Color.WHITE);
            binding.recyclerView.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.layoutBtn.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            convenientAdapter.setColor(Color.WHITE);
        } else {
            binding.layoutScrollView.setBackgroundColor(this.getResources().getColor(R.color.color_EBEBEB));
            binding.layoutLinearRoomInfo.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.imgItemnguoi.setColorFilter(getResources().getColor(R.color.black));
            binding.tvCountPerson.setTextColor(Color.BLACK);
            binding.text2.setTextColor(Color.BLACK);
            binding.text1.setTextColor(Color.BLACK);
            binding.text3.setTextColor(Color.BLACK);
            binding.imgItemBedRoom.setColorFilter(getResources().getColor(R.color.black));
            binding.tvSoGiuong.setTextColor(Color.BLACK);
            binding.imgItemDienTich.setColorFilter(getResources().getColor(R.color.black));
            binding.tvDientich.setTextColor(Color.BLACK);
            binding.text5.setTextColor(Color.BLACK);
            binding.tvGia.setTextColor(Color.BLACK);
            binding.contentContentHotel.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.tvMota.setTextColor(Color.BLACK);
            binding.contentMota.setTextColor(Color.BLACK);
            binding.layoutChinhSachHuy.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.tvTextCSH.setTextColor(Color.BLACK);
            binding.canceltrue.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.cancelfalse.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.layoutTienNghiPhong.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.viewCSH.setBackgroundColor(Color.BLACK);
            binding.viewCSH1.setBackgroundColor(Color.BLACK);
            binding.viewCSH2.setBackgroundColor(Color.BLACK);
            binding.tvChinhSachHuyPhong.setTextColor(Color.BLACK);
            binding.tvChinhSachHuyPhong1.setTextColor(Color.BLACK);
            binding.tvChinhSachHuyPhong2.setTextColor(Color.BLACK);
            binding.tvTienNghiPhong.setTextColor(Color.BLACK);
            binding.recyclerView.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.layoutBtn.setBackgroundColor(this.getResources().getColor(R.color.white));
            convenientAdapter.setColor(Color.BLACK);
        }
    }

}