package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.model.house.Feedback;
import com.example.bookingapproyaljourney.model.house.Gallery;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.house.Room;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.FeedbackAdapter;
import com.example.bookingapproyaljourney.ui.adapter.GalleryAdapter;
import com.example.bookingapproyaljourney.ui.adapter.RoomAdapter;
import com.example.bookingapproyaljourney.view_model.DetailProductViewModel;
import com.example.bookingapproyaljourney.view_model.MapActivityNearByFromYouViewModel;
import com.example.librarycireleimage.CircleImageView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
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
    private Button btnRentNow;
    private MenuItem menuItem;
    FeedbackAdapter feedbackAdapter;
    RoomAdapter roomAdapter;
    GalleryAdapter galleryAdapter;
    ConvenientAdapter convenientAdapter;
    private DetailProductViewModel detailProductViewModel;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailproduct);

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
        imgStar2 = (ImageView) findViewById(R.id.imgStar2);
        rcvFeedback = (RecyclerView) findViewById(R.id.rcvFeedback);
        opening = (TextView) findViewById(R.id.opening);
        ending = (TextView) findViewById(R.id.ending);
        GiaMoPhong = (TextView) findViewById(R.id.GiaMoPhong);
        btnRentNow = (Button) findViewById(R.id.btnRentNow);
        legalHouse = (TextView) findViewById(R.id.legalHouse);

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
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);




//        rcvFeedback.setHasFixedSize(true);
//        rcvFeedback.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        FeedbackAdapter feedbackAdapter = new FeedbackAdapter(this, getListFeedback());
//        rcvFeedback.setAdapter(feedbackAdapter);



        detailProductViewModel.getHouseById("635ab27ed3de0abfd7e69f22").observe(this, item -> {
            tvAddress.setText(item.getNameLocation());
            tvNameHotel.setText(item.getName());
            tvAmountBedRoom.setText(item.getSleepingPlaces().size()+" ");
            tvAmountBedroom2.setText(item.getBathrooms().size()+ "");
            ContentHouse.setText(item.getContent());
            legalHouse.setText(item.getLegal());

            Glide.with(this).load(item.getImages().get(0)).apply(options).into(ivimgHotel);

            rcvGallery.setHasFixedSize(true);
            rcvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            GalleryAdapter galleryAdapter = new GalleryAdapter(this, item.getImages());
            rcvGallery.setAdapter(galleryAdapter);

            rcvRoom.setHasFixedSize(true);
            rcvRoom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            RoomAdapter roomAdapter = new RoomAdapter(this, item.getSleepingPlaces());
            rcvRoom.setAdapter(roomAdapter);

            rcvConvenient.setHasFixedSize(true);
            rcvConvenient.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ConvenientAdapter convenientAdapter = new ConvenientAdapter(item.getSupplement(), this);
            rcvConvenient.setAdapter(convenientAdapter);

            Glide.with(this).load(item.getHostResponse().getImage()).apply(options).into(imgManage);
            NameManage.setText(item.getHostResponse().getName());

            opening.setText(item.getOpening());
            ending.setText(item.getEnding());




            Log.e("DUy", item.toString());
        });
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

    private List<Convenient> getListConvenient() {
        List<Convenient> listconvenient = new ArrayList<>();
//        listconvenient.add(new Convenient(1,"Kitchen",R.drawable.ic_item_convenien));
//        listconvenient.add(new Convenient(2,"Kitchen",R.drawable.ic_item_convenien));
//        listconvenient.add(new Convenient(3,"Kitchen",R.drawable.ic_item_convenien));
//        listconvenient.add(new Convenient(4,"Kitchen",R.drawable.ic_item_convenien));
        return listconvenient;
    }

    private List<Room> getListRoom() {
        List<Room> listroom = new ArrayList<>();
//        listroom.add(new Room(R.drawable.ic_room_detailproduct,"Room1","1 bed big"));
//        listroom.add(new Room(R.drawable.ic_room_detailproduct,"Room1","1 bed big"));
//        listroom.add(new Room(R.drawable.ic_room_detailproduct,"Room1","1 bed big"));
//        listroom.add(new Room(R.drawable.ic_room_detailproduct,"Room1","1 bed big"));
//        listroom.add(new Room(R.drawable.ic_room_detailproduct,"Room1","1 bed big"));
//        listroom.add(new Room(R.drawable.ic_room_detailproduct,"Room1","1 bed big"));
        return listroom;
    }

    private List<Feedback> getListFeedback() {
        List<Feedback> list = new ArrayList<>();
//        list.add(new Feedback("","","","",R.drawable.ic_launcher_background));
//        list.add(new Feedback("","","","",R.drawable.ic_launcher_background));
//        list.add(new Feedback("","","","",R.drawable.ic_launcher_background));
//        list.add(new Feedback("","","","",R.drawable.ic_launcher_background));
//        list.add(new Feedback("","","","",R.drawable.ic_launcher_background));

        return list;
    }

    private List<Gallery> getListGallery() {
        List<Gallery> list = new ArrayList<>();
//        list.add(new Gallery(R.drawable.imagetest, 5));
//        list.add(new Gallery(R.drawable.imagetest, 4));
//        list.add(new Gallery(R.drawable.imagetest, 3));
//        list.add(new Gallery(R.drawable.imagetest, 2));
//        list.add(new Gallery(R.drawable.imagetest, 1));
        return list;
    }

}