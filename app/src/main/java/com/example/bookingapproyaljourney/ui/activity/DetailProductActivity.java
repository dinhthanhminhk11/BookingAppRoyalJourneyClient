package com.example.bookingapproyaljourney.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackGetBookmark;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.Bathroom;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.ui.activity.chat_message.ChatMessageActivity;
import com.example.bookingapproyaljourney.ui.activity.feedback.FeedbackListActivity;
import com.example.bookingapproyaljourney.ui.adapter.BathdRoomAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientListAdapter;
import com.example.bookingapproyaljourney.ui.adapter.FeedbackAdapter;
import com.example.bookingapproyaljourney.ui.adapter.GalleryAdapter;
import com.example.bookingapproyaljourney.ui.adapter.RoomAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetBathRoom;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetConvenient;
import com.example.bookingapproyaljourney.view_model.DetailProductViewModel;
import com.example.bookingapproyaljourney.view_model.FeedbackViewModel;
import com.example.librarycireleimage.CircleImageView;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity implements FeedbackAdapter.EventClick, OnMapReadyCallback {
    private ScrollView scrollView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private CardView contenTOp;
    private MaterialToolbar toolBar;
    private Location locationYouSelf;
    private ImageView ivimgHotel;
    private TextView tvNameHotel;
    private TextView tvAddress;
    private TextView titleMota;
    private TextView tvAmountBedRoom;
    private TextView tvAmountBedroom2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text7;
    private TextView text6;
    private TextView text8;
    private TextView text9;
    private TextView text10;
    private TextView text11;
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
    private FeedbackViewModel feedbackViewModel;
    private House house;
    private HouseDetailResponse houseDetailResponse;
    private String idHouse = "";
    private String id_house = "";
    private String idBoss = "";
    private String imgBoss = "";
    private String nameBoss = "";
    private RequestOptions options;
    private LottieAnimationView progressBar;
    private BottomSheetConvenient bottomSheetConvenient;
    private BottomSheetBathRoom bottomSheetBathRoom;
    private TextView btnDanhGia;
    private TextView statusHouse;
    private TextView startDateAndEndDate;
    private List<Convenient> data;
    private List<Bathroom> dataBathRoom;
    private TextView countSao;
    private TextView showMedical;
    private TextView textView1;
    private TextView text2;
    private NumberFormat fm = new DecimalFormat("#,###");
    private boolean isStillEmpty;
    private BookmarkRepository bookmarkRepository;
    private boolean isClickSpeed = true;
    private GoogleMap mMap;
    private ResultReceiver resultReceiver;
    private LatLng latLngLocationYourSelf;
    private MarkerOptions markerOptions;
    private Marker currentUser, searchPoint;
    private RelativeLayout contentBackground;
    private RelativeLayout contentBackgroundGradiend;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailproduct);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapinfo);
        mapFragment.getMapAsync(this);

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
        statusHouse = findViewById(R.id.statusHouse);
        startDateAndEndDate = findViewById(R.id.startDateAndEndDate);
        countSao = findViewById(R.id.tvCountSao);
        TextView tvSao = findViewById(R.id.tvSao);
        TextView btnShowFeedback = findViewById(R.id.btnShowFeedback);
        showMedical = findViewById(R.id.showMedical);
        contentBackground = findViewById(R.id.contentBackground);
        contentBackgroundGradiend = findViewById(R.id.contentBackgroundGradiend);
        titleMota = findViewById(R.id.titleMota);
        textView1 = findViewById(R.id.textView1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text7 = findViewById(R.id.text7);
        text6 = findViewById(R.id.text6);
        text8 = findViewById(R.id.text8);
        text9 = findViewById(R.id.text9);
        text10 = findViewById(R.id.text10);
        text11 = findViewById(R.id.text11);

        setSupportActionBar(toolBar);

        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);

        toolBar.setBackground(null);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                startActivity(new Intent(DetailProductActivity.this, MainActivity.class));
            }
        });
        getSupportActionBar().setTitle("");
        convenientAdapter = new ConvenientAdapter(this);
        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        bookmarkRepository = new BookmarkRepository();
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);
        idHouse = getIntent().getStringExtra(AppConstant.HOUSE_EXTRA);
        boolean checkFeedBack = getIntent().getBooleanExtra("CHECK_FEEDBACK", false);
        if (checkFeedBack) {
            CookieBar.build(DetailProductActivity.this)
                    .setTitle(this.getString(R.string.Successfully))
                    .setMessage(this.getString(R.string.Successfully_feedback))
                    .setIcon(R.drawable.ic_complete_order)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(3000)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();
        }

        initData(idHouse);

        btnDanhGia.setPaintFlags(btnDanhGia.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        rcvFeedback.setHasFixedSize(true);
        rcvFeedback.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        feedbackViewModel.getFeedbackId(idHouse).observe(this, it -> {
            FeedbackAdapter feedbackAdapter = new FeedbackAdapter(it, this);
            rcvFeedback.setAdapter(feedbackAdapter);
            if (it.size() > 0) {
                btnDanhGia.setText(it.size() + " Đánh giá");
                float total = 0;
                for (int i = 0; i < it.size(); i++) {
                    total = total + it.get(i).getSao();
                }
                float average = total / it.size();
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                if (average % 1 == 0) {
                    countSao.setText(decimalFormat.format(average) + ".0");
                    tvSao.setText(decimalFormat.format(average) + ".0");
                } else {
                    countSao.setText(decimalFormat.format(average));
                    tvSao.setText(decimalFormat.format(average));
                }
                feedbackViewModel.updateSaoProduct(id_house, (double) average);
            } else {
                btnDanhGia.setText("Đánh giá");
                tvSao.setText("5.0");
                countSao.setText("5.0");
            }

        });

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

        btnShowFeedback.setOnClickListener(v -> {
            Intent intent = new Intent(this, FeedbackListActivity.class);
            intent.putExtra("ID_BOSS", idBoss);
            intent.putExtra("ID_HOUSE", id_house);
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
        showMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailProductActivity.this, MedicalActivity.class));
            }
        });
        btnRentNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (token.equals("")) {
                    dialog.show();
                } else if (isStillEmpty) {
                    CookieBar.build(DetailProductActivity.this)
                            .setTitle(DetailProductActivity.this.getString(R.string.out_of_room))
                            .setMessage(DetailProductActivity.this.getString(R.string.dialogcontentnomal))
                            .setIcon(R.drawable.ic_warning_icon_check)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(3000)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
//                    ToastCheck toastCheck = new ToastCheck(DetailProductActivity.this, R.style.StyleToast, "Đã hết phòng", DetailProductActivity.this.getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
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
            id_house = item.get_id();
            imgBoss = item.getHostResponse().getImage();
            nameBoss = item.getHostResponse().getName();
            tvAddress.setText(item.getNameLocation());
            tvNameHotel.setText(item.getName());
            tvAmountBedRoom.setText(item.getSleepingPlaces().size() + getString(R.string.Bedroom));
            tvAmountBedroom2.setText(item.getBathrooms().size() + getString(R.string.phongtamdetail));
            ContentHouse.setText(item.getContent());
            legalHouse.setText(item.getLegal());
            startDateAndEndDate.setText(item.getStartDate() + " - " + item.getEndDate());
            isStillEmpty = item.isStillEmpty();
            if (item.isStillEmpty()) {
                statusHouse.setText("Hết Phòng");
            } else {
                statusHouse.setText("Còn Phòng");
            }
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
//            convenientAdapter.setConvenientTestList(item.getSupplement());
            rcvConvenient.setAdapter(convenientAdapter);
            data = item.getSupplement();

            Glide.with(this).load(item.getHostResponse().getImage()).apply(options).into(imgManage);
            NameManage.setText(item.getHostResponse().getName());

            opening.setText(item.getOpening());
            ending.setText(item.getEnding());
            GiaMoPhong.setText(fm.format(item.getPrice()) + " Vnd/đêm");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showMakerAndText(item);
                }
            }, 2000);


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
        getMenuInflater().inflate(R.menu.itemboomak, menu);
        menuItem = menu.findItem(R.id.iconbookmak);

        bookmarkRepository.getBookmarkByIdUserAndIdHouse(UserClient.getInstance().getId(), idHouse, new CallbackGetBookmark() {
            @Override
            public void onResponse(BookmarkResponse bookmarkResponse) {
                if (bookmarkResponse.getData().size() > 0) {
                    if (bookmarkResponse.getData().get(0).isCheck()) {
                        menuItem.setIcon(R.drawable.ic_baseline_bookmark_24_white_full);
                        isClickSpeed = false;
                    }
                } else {
                    menuItem.setIcon(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar);
                    isClickSpeed = true;
                }
            }

            @Override
            public void onFailure(BookmarkResponse bookmarkResponse) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.iconbookmak) {
            if (isClickSpeed) {
                bookmarkRepository.addBookMark(new PostIDUserAndIdHouse(UserClient.getInstance().getId(), idHouse), new InterfacePostBookmark() {
                    @Override
                    public void onResponse(BookmarkResponse bookmarkResponse) {
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                item.setIcon(R.drawable.ic_baseline_bookmark_24_white_full);
                isClickSpeed = false;
            } else {
                bookmarkRepository.deleteBookmark(UserClient.getInstance().getId(), idHouse, new InterfacePostBookmark() {
                    @Override
                    public void onResponse(BookmarkResponse bookmarkResponse) {
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                menuItem.setIcon(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar);
                isClickSpeed = true;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailProductActivity.this, MainActivity.class));
    }

    @Override
    public void onClick() {
        Intent intent = new Intent(this, FeedbackListActivity.class);
        intent.putExtra("ID_BOSS", idBoss);
        intent.putExtra("ID_HOUSE", id_house);
        intent.putExtra("IMG_BOSS", imgBoss);
        intent.putExtra("NAME_BOSS", nameBoss);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DetailProductActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showMakerAndText(HouseDetailResponse houseDetailResponse) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(houseDetailResponse.getLocation().getCoordinates().get(1), houseDetailResponse.getLocation().getCoordinates().get(0)), 13));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(houseDetailResponse.getLocation().getCoordinates().get(1), houseDetailResponse.getLocation().getCoordinates().get(0)))
                .zoom(10)
                .bearing(0)
                .tilt(40)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // vẽ maker
        LatLng myLocation = new LatLng(houseDetailResponse.getLocation().getCoordinates().get(1), houseDetailResponse.getLocation().getCoordinates().get(0));
        markerOptions = new MarkerOptions()
                .position(myLocation)
                .title(houseDetailResponse.getName())
                .snippet(houseDetailResponse.getNameLocation())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUser = mMap.addMarker(markerOptions);
        currentUser.setTag(false);
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            titleMota.setTextColor(Color.WHITE);
            ContentHouse.setTextColor(Color.WHITE);
            NameManage.setTextColor(Color.WHITE);
            textView1.setTextColor(Color.WHITE);
            text2.setTextColor(Color.WHITE);
            text3.setTextColor(Color.WHITE);
            text4.setTextColor(Color.WHITE);
            text5.setTextColor(Color.WHITE);
            text7.setTextColor(Color.WHITE);
            text6.setTextColor(Color.WHITE);
            text8.setTextColor(Color.WHITE);
            text9.setTextColor(Color.WHITE);
            legalHouse.setTextColor(Color.WHITE);
            text10.setTextColor(Color.WHITE);
            text11.setTextColor(Color.WHITE);
            convenientAdapter.setColor(Color.WHITE);
            contentBackgroundGradiend.setBackgroundResource(R.drawable.gradientbackground_detailproduct_drak);
//            textView2.setTextColor(Color.WHITE);
        } else {
            contentBackgroundGradiend.setBackgroundResource(R.drawable.gradientbackground_detailproduct);
            contentBackground.setBackgroundColor(Color.WHITE);
            titleMota.setTextColor(Color.BLACK);
            ContentHouse.setTextColor(Color.BLACK);
            NameManage.setTextColor(Color.BLACK);
            textView1.setTextColor(Color.BLACK);
            text2.setTextColor(Color.BLACK);
            text3.setTextColor(Color.BLACK);
            text4.setTextColor(Color.BLACK);
            text5.setTextColor(Color.BLACK);
            text7.setTextColor(Color.BLACK);
            text6.setTextColor(Color.BLACK);
            text8.setTextColor(Color.BLACK);
            text9.setTextColor(Color.BLACK);
            legalHouse.setTextColor(Color.BLACK);
            text10.setTextColor(Color.BLACK);
            text11.setTextColor(Color.BLACK);
            convenientAdapter.setColor(Color.BLACK);
//            textView2.setTextColor(Color.BLACK);
        }
    }

}