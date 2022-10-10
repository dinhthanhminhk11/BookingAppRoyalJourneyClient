package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;
import com.example.librarycireleimage.CircleImageView;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailProductActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private CardView contenTOp;
    private MaterialToolbar toolBar;
    private ImageView ivimgHotel;
    private TextView tvNameHotel;
    private TextView tvAddress;
    private TextView tvAmountBedRoom;
    private TextView tvAmountBedroom;
    private ImageView imgStar1;
    private CircleImageView imgManage;
    private TextView NameManage;
    private ImageButton btPhone;
    private ImageButton btMesseger;
    private RecyclerView rcvRoom;
    private RecyclerView rcvConvenient;
    private RecyclerView rcvGallery;
    private ImageView imgStar2;
    private RecyclerView rcvFeedback;
    private TextView GiaMoPhong;
    private Button btnRentNow;
    private MenuItem menuItem;

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
        tvAmountBedroom = (TextView) findViewById(R.id.tvAmountBedroom);
        imgStar1 = (ImageView) findViewById(R.id.imgStar1);
        imgManage = (CircleImageView) findViewById(R.id.imgManage);
        NameManage = (TextView) findViewById(R.id.NameManage);
        btPhone = (ImageButton) findViewById(R.id.btPhone);
        btMesseger = (ImageButton) findViewById(R.id.btMesseger);
        rcvRoom = (RecyclerView) findViewById(R.id.rcvRoom);
        rcvConvenient = (RecyclerView) findViewById(R.id.rcvConvenient);
        rcvGallery = (RecyclerView) findViewById(R.id.rcvGallery);
        imgStar2 = (ImageView) findViewById(R.id.imgStar2);
        rcvFeedback = (RecyclerView) findViewById(R.id.rcvFeedback);
        GiaMoPhong = (TextView) findViewById(R.id.GiaMoPhong);
        btnRentNow = (Button) findViewById(R.id.btnRentNow);

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