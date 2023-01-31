package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.callback.CallbackGetBookmark;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityHotelBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.model.hotel.TienNghiK;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.MedicalActivity;
import com.example.bookingapproyaljourney.ui.activity.chat_message.ChatMessageActivity;
import com.example.bookingapproyaljourney.ui.activity.feedback.FeedbackListActivity;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.FeedbackAdapter;
import com.example.bookingapproyaljourney.ui.adapter.GalleryAdapter;
import com.example.bookingapproyaljourney.ui.adapter.RoomHotelAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetConvenient;
import com.example.bookingapproyaljourney.view_model.FeedbackViewModel;
import com.example.bookingapproyaljourney.view_model.HotelInfoViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HotelActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, FeedbackAdapter.EventClick {
    private ActivityHotelBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MenuItem menuItem;
    private boolean isClickSpeed = true;
    private HotelInfoViewModel hotelInfoViewModel;
    private RequestOptions options;
    private String phone;
    private ConvenientAdapter convenientAdapter;
    private GalleryAdapter galleryAdapter;
    private RoomHotelAdapter roomHotelAdapter;
    private String idHotel;
    private GoogleMap mMap;
    private BottomSheetConvenient bottomSheetConvenient;
    private MarkerOptions markerOptions;
    private Marker currentUser;
    private String ageChildren;
    private String cancelBooking;
    private FeedbackViewModel feedbackViewModel;
    private String idBoss = "";
    private String nameBoss = "";
    private String imgBoss = "";
    private String id_house = "";
    private TextView showMore;
    private RecyclerView rcvConvenientList;
    private TextView showMedical;
    private ArrayList<TienNghiK> data;
    private SharedPreferences sharedPreferences;
    private BookmarkRepository bookmarkRepository;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        idHotel = getIntent().getStringExtra(AppConstant.HOTEL_EXTRA);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapinfo);
        mapFragment.getMapAsync(this);
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        initToolbar();
        initView();

        binding.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        binding.showMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HotelActivity.this, MedicalActivity.class));
            }
        });
    }

    private void showDialog() {

        bottomSheetConvenient = new BottomSheetConvenient(HotelActivity.this, R.style.MaterialDialogSheet, data, new BottomSheetConvenient.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetConvenient.dismiss();
            }
        });
        bottomSheetConvenient.show();
        bottomSheetConvenient.setCanceledOnTouchOutside(false);
    }

    private void initView() {


        convenientAdapter = new ConvenientAdapter(this);
        roomHotelAdapter = new RoomHotelAdapter();
        binding.btPhone.setOnClickListener(this);

        binding.rcvConvenient.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rcvFeedback.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        options = new RequestOptions().centerCrop().placeholder(R.drawable.img).error(R.drawable.img);

        bookmarkRepository = new BookmarkRepository();
        hotelInfoViewModel = new ViewModelProvider(this).get(HotelInfoViewModel.class);
        hotelInfoViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        feedbackViewModel.getFeedbackId(idHotel).observe(this, it -> {
            FeedbackAdapter feedbackAdapter = new FeedbackAdapter(it, this);
            binding.rcvFeedback.setAdapter(feedbackAdapter);
            if (it.size() > 0) {
                binding.btnDanhGia.setText(it.size() + " Đánh giá");
                binding.tvTotalFeedback.setText(it.size() + " Đánh giá");
                float total = 0;
                for (int i = 0; i < it.size(); i++) {
                    total = total + it.get(i).getSao();
                }
                float average = total / it.size();
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                if (average % 1 == 0) {
                    binding.tvCountSao.setText(decimalFormat.format(average) + ".0");
                } else {
                    binding.tvCountSao.setText(decimalFormat.format(average));
                }
                feedbackViewModel.updateSaoProduct(idHotel, (double) average);
            } else {
                binding.btnDanhGia.setText("Đánh giá");
                binding.tvCountSao.setText("5.0");
                binding.tvTotalFeedback.setText("0 Đánh giá");
            }

        });


        hotelInfoViewModel.getHotelMutableLiveData().observe(this, new Observer<HotelById>() {
            @Override
            public void onChanged(HotelById item) {
                if (item instanceof HotelById) {
                    idBoss = item.getDataUser().get_id();
                    nameBoss = item.getDataUser().getName();
                    imgBoss = item.getDataUser().getImage();
                    id_house = item.getDataHotel().get_id();
                    Log.e("MinhCheck", item.getDataHotel().getTreEm() + " trẻ em");
                    Log.e("MinhCheck", item.getDataHotel().isChinhSachHuy() + " huỷ");
                    ageChildren = String.valueOf(item.getDataHotel().getTreEm());
                    cancelBooking = String.valueOf(item.getDataHotel().isChinhSachHuy());
                    data = item.getDataHotel().getTienNghiKS();

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
                            Intent intent = new Intent(HotelActivity.this, RoomInfoActivity.class);
                            intent.putExtra(AppConstant.ROOM_EXTRA, o.get_id());
                            intent.putExtra(AppConstant.ROOM_AGE_CHILDREN, ageChildren);
                            intent.putExtra(AppConstant.ROOM_CANCEL_BOOKING, cancelBooking);
                            startActivity(intent);
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showMakerAndText(item.getDataHotel());
                        }
                    }, 2000);
                }
            }
        });
        binding.btnShowFeedback.setOnClickListener(v -> {
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

        Button login = (Button) dialog.findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent checkLogin = new Intent(this, LoginActivity.class);
            checkLogin.putExtra(AppConstant.CHECK_LOGIN_TOKEN_NULL, "checkNotSignIn");
            startActivity(checkLogin);
            dialog.dismiss();
        });
        binding.btMesseger.setOnClickListener(v -> {
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

        binding.btnRentNow.setOnClickListener(v -> {
            int positionScroll = binding.contentCancellationPolicy.getHeight();
            int positionScroll2 = binding.contentHost.getHeight();
            int positionScroll3 = binding.contentConvenient.getHeight();
            int positionScroll4 = binding.contentImageHotel.getHeight();
            int positionScroll5 = binding.contentLocation.getHeight();
            int positionScroll6 = binding.contentFeedback.getHeight();
            int positionScroll7 = binding.contentOpenAndEndingHotel.getHeight();
            int positionScroll8 = binding.contentPolicy.getHeight();
            int positionScroll9 = binding.contentCancelBooking.getHeight();
            int positionScroll10 = binding.contentMedican.getHeight();
            int sum = positionScroll + positionScroll2 + positionScroll3 + positionScroll4 + positionScroll5 + positionScroll6 + positionScroll7 + positionScroll8 + positionScroll9 + positionScroll10;
            binding.scrollView.post(new Runnable() {
                @Override
                public void run() {
                    binding.scrollView.scrollTo(0, sum);
                }
            });
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
        bookmarkRepository.getBookmarkByIdUserAndIdHouse(UserClient.getInstance().getId(), idHotel, new CallbackGetBookmark() {
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.iconbookmak) {
            if (isClickSpeed) {
                item.setIcon(R.drawable.ic_baseline_bookmark_24_white_full);
                bookmarkRepository.addBookMark(new PostIDUserAndIdHouse(UserClient.getInstance().getId(), idHotel), new InterfacePostBookmark() {
                    @Override
                    public void onResponse(BookmarkResponse bookmarkResponse) {
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                isClickSpeed = false;
            } else {
                menuItem.setIcon(R.drawable.ic_baseline_bookmark_border_24_menu_toolbar);
                bookmarkRepository.deleteBookmark(UserClient.getInstance().getId(), idHotel, new InterfacePostBookmark() {
                    @Override
                    public void onResponse(BookmarkResponse bookmarkResponse) {
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                isClickSpeed = true;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hotelInfoViewModel.getHotelById(idHotel);
        sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HotelActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showMakerAndText(Hotel hotel) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(hotel.getLocation().getCoordinates().get(1), hotel.getLocation().getCoordinates().get(0)), 13));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(hotel.getLocation().getCoordinates().get(1), hotel.getLocation().getCoordinates().get(0))).zoom(10).bearing(0).tilt(40).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // vẽ maker
        LatLng myLocation = new LatLng(hotel.getLocation().getCoordinates().get(1), hotel.getLocation().getCoordinates().get(0));
        markerOptions = new MarkerOptions().position(myLocation).title(hotel.getName()).snippet(hotel.getSonha() + ", " + hotel.getXa() + ", " + hotel.getHuyen() + ", " + hotel.getTinh()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUser = mMap.addMarker(markerOptions);
        currentUser.setTag(false);
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
}