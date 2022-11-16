package com.example.bookingapproyaljourney.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.Bathroom;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.ui.activity.chat_message.ChatMessageActivity;
import com.example.bookingapproyaljourney.ui.adapter.BathdRoomAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientListAdapter;
import com.example.bookingapproyaljourney.ui.adapter.FeedbackAdapter;
import com.example.bookingapproyaljourney.ui.adapter.GalleryAdapter;
import com.example.bookingapproyaljourney.ui.adapter.RoomAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetBathRoom;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetConvenient;
import com.example.bookingapproyaljourney.view_model.DetailProductViewModel;
import com.example.librarycireleimage.CircleImageView;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private CardView contenTOp;
    private MaterialToolbar toolBar;
    private ImageView ivimgHotel;
    private TextView tvNameHotel;
    private TextView tvAddress;
    private TextView tvAmountBedRoom;
    private TextView tvAmountBedroom2;
    private ImageView imgStar1;
    private TextView ContentHouse;
    private CircleImageView imgManage;
    private TextView NameManage;
    private ImageButton btPhone;
    private ImageButton btMesseger;
    private RecyclerView rcvRoom;
    private RecyclerView rcvConvenient;
    private RecyclerView rcvGallery;
    private ImageView imgStar2;
    private RecyclerView rcvFeedback;
    private TextView opening;
    private TextView ending;
    private TextView GiaMoPhong;
    private TextView legalHouse;
    private TextView showMore;
    private TextView showMorebathdroom;
    private RecyclerView rcvBathdRoom;
    private Button btnRentNow;
    private MenuItem menuItem;
    private FeedbackAdapter feedbackAdapter;
    private RoomAdapter roomAdapter;
    private GalleryAdapter galleryAdapter;
    private ConvenientAdapter convenientAdapter;
    private BathdRoomAdapter bathdRoomAdapter;
    private ConvenientListAdapter convenientListAdapter;
    private DetailProductViewModel detailProductViewModel;
    private House house;
    private HouseDetailResponse houseDetailResponse;
    private String idHouse = "";
    private String idBoss = "";
    private String imgBoss = "";
    private String nameBoss = "";
    private RequestOptions options;
    private LottieAnimationView progressBar;
    private BottomSheetConvenient bottomSheetConvenient;
    private BottomSheetBathRoom bottomSheetBathRoom;
    private TextView btnDanhGia;
    private List<Convenient> data;
    private List<Bathroom> dataBathRoom;
    private NumberFormat fm = new DecimalFormat("#,###");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailproduct);

        progressBar = (LottieAnimationView) findViewById(R.id.progressBar);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        contenTOp = (CardView) findViewById(R.id.contenTOp);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        ivimgHotel = (ImageView) findViewById(R.id.ivimgHotel);
        tvNameHotel = (TextView) findViewById(R.id.tvNameHotel);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAmountBedRoom = (TextView) findViewById(R.id.tvAmountBedRoom);
        tvAmountBedroom2 = (TextView) findViewById(R.id.tvAmountBedroom2);
        imgStar1 = (ImageView) findViewById(R.id.imgStar1);
        ContentHouse = (TextView) findViewById(R.id.ContentHouse);
        imgManage = (CircleImageView) findViewById(R.id.imgManage);
        NameManage = (TextView) findViewById(R.id.NameManage);
        btPhone = (ImageButton) findViewById(R.id.btPhone);
        btMesseger = (ImageButton) findViewById(R.id.btMesseger);
        rcvRoom = (RecyclerView) findViewById(R.id.rcvRoom);
        rcvConvenient = (RecyclerView) findViewById(R.id.rcvConvenient);
        rcvGallery = (RecyclerView) findViewById(R.id.rcvGallery);
        rcvBathdRoom = (RecyclerView) findViewById(R.id.rcvBathdRoom);
        imgStar2 = (ImageView) findViewById(R.id.imgStar2);
        rcvFeedback = (RecyclerView) findViewById(R.id.rcvFeedback);
        opening = (TextView) findViewById(R.id.opening);
        ending = (TextView) findViewById(R.id.ending);
        GiaMoPhong = (TextView) findViewById(R.id.priceRoom);
        btnRentNow = (Button) findViewById(R.id.btnRentNow);
        legalHouse = (TextView) findViewById(R.id.legalHouse);
        showMore = (TextView) findViewById(R.id.showMore);
        showMorebathdroom = (TextView) findViewById(R.id.showMorebathdroom);
        btnDanhGia = findViewById(R.id.btnDanhGia);

        setSupportActionBar(toolBar);

        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolBar.setBackground(null);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");

        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);
        idHouse = getIntent().getStringExtra(AppConstant.HOUSE_EXTRA);
        initData(idHouse);
//        rcvFeedback.setHasFixedSize(true);
//        rcvFeedback.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        FeedbackAdapter feedbackAdapter = new FeedbackAdapter(this, getListFeedback());
//        rcvFeedback.setAdapter(feedbackAdapter);
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });
        showMorebathdroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBathRoom();
            }
        });
        btnDanhGia.setOnClickListener(v -> {
            Intent intent = new Intent(this, FeedBackActivity.class);
            intent.putExtra("ID_BOSS", idBoss);
            intent.putExtra("IMG_BOSS", imgBoss);
            intent.putExtra("NAME_BOSS", nameBoss);
            startActivity(intent);
        });


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dia_log_comfirm_logout);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        Button login = (Button) dialog.findViewById(R.id.login);
        login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            dialog.dismiss();
        });
        btMesseger.setOnClickListener(v -> {
            if (token.equals("")) {
                dialog.show();
            } else {
                Intent intent = new Intent(this, ChatMessageActivity.class);
                intent.putExtra("ID_BOSS", idBoss);
                intent.putExtra("IMG_BOSS", imgBoss);
                intent.putExtra("NAME_BOSS", nameBoss);
                startActivity(intent);
            }
        });

        btnRentNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (token.equals("")) {
                    dialog.show();
                } else {
                    Intent intent = new Intent(DetailProductActivity.this, BillOderActivity.class);
                    intent.putExtra(AppConstant.HOUSE_EXTRA, idHouse);
                    startActivity(intent);
                }

            }
        });
    }

    private void initData(String id) {
        detailProductViewModel.getHouseById(id).observe(this, item -> {
            idBoss = item.getHostResponse().get_id();
            imgBoss = item.getHostResponse().getImage();
            nameBoss = item.getHostResponse().getName();
            tvAddress.setText(item.getNameLocation());
            tvNameHotel.setText(item.getName());
            tvAmountBedRoom.setText(item.getSleepingPlaces().size() + " Phòng ngủ");
            tvAmountBedroom2.setText(item.getBathrooms().size() + " Phòng tắm");
            ContentHouse.setText(item.getContent());
            legalHouse.setText(item.getLegal());
            btPhone.setOnClickListener(view -> {
                String phone = item.getHostResponse().getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            });

            Glide.with(this).load(item.getImages().get(0)).apply(options).into(ivimgHotel);

            rcvGallery.setHasFixedSize(true);
            rcvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            GalleryAdapter galleryAdapter = new GalleryAdapter(this, item.getImages());
            rcvGallery.setAdapter(galleryAdapter);

            rcvRoom.setHasFixedSize(true);
            rcvRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            RoomAdapter roomAdapter = new RoomAdapter(this, item.getSleepingPlaces());
            rcvRoom.setAdapter(roomAdapter);

            rcvBathdRoom.setHasFixedSize(true);
            rcvBathdRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            BathdRoomAdapter bathdRoomAdapter = new BathdRoomAdapter(item.getBathrooms(), this);
            rcvBathdRoom.setAdapter(bathdRoomAdapter);
            dataBathRoom = item.getBathrooms();


            rcvConvenient.setHasFixedSize(true);
            rcvConvenient.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ConvenientAdapter convenientAdapter = new ConvenientAdapter(item.getSupplement(), this);
            rcvConvenient.setAdapter(convenientAdapter);
            data = item.getSupplement();

            Glide.with(this).load(item.getHostResponse().getImage()).apply(options).into(imgManage);
            NameManage.setText(item.getHostResponse().getName());

            opening.setText(item.getOpening());
            ending.setText(item.getEnding());
            GiaMoPhong.setText(fm.format(item.getPrice()) + " Vnd/đêm");
            progressBar.setVisibility(View.GONE);
        });

    }

    private void showDialog() {
        bottomSheetConvenient = new BottomSheetConvenient(DetailProductActivity.this, R.style.MaterialDialogSheet, data, new BottomSheetConvenient.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetConvenient.dismiss();
            }
        });
        bottomSheetConvenient.show();
        bottomSheetConvenient.setCanceledOnTouchOutside(false);
    }

    private void showDialogBathRoom() {
        bottomSheetBathRoom = new BottomSheetBathRoom(DetailProductActivity.this, R.style.MaterialDialogSheet, dataBathRoom, new BottomSheetBathRoom.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetBathRoom.dismiss();
            }
        });
        bottomSheetBathRoom.show();
        bottomSheetBathRoom.setCanceledOnTouchOutside(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.itemboomak, menu);
        menuItem = menu.findItem(R.id.iconbookmak);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.iconbookmak) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}