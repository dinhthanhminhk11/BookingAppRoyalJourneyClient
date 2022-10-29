package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.HiredProfileAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private HiredProfileAdapter hiredProfileAdapter;
    private List<House> dataHouse;
    private RecyclerView recyclerViewHiredProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerViewHiredProfile = (RecyclerView) findViewById(R.id.recycleView_profile);
        FakeData();
    }

    private void FakeData()
    {
        dataHouse = new ArrayList<>();
        dataHouse.add(new House(1, "Ha Noi Prime Center", "Ha Noi", 1, "1.5", 15000, 2, 2, 5));
        dataHouse.add(new House(2, "Hai Phong Prime Center", "Hai Phong", 2, "2.7", 13000, 1, 2, 4));
        dataHouse.add(new House(3, "Thai Binh Prime Center", "Thai Binh", 3, "3", 10000, 2, 1, 3));
        dataHouse.add(new House(4, "HCM Prime Center", "Tp.Ho Chi Minh", 4, "4.5", 17000, 2, 2, 2));
        dataHouse.add(new House(5, "Hung Yen Prime Center", "Hung Yen", 5, "4", 12000, 1, 1, 1));


        hiredProfileAdapter = new HiredProfileAdapter(dataHouse);
        recyclerViewHiredProfile.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewHiredProfile.setAdapter(hiredProfileAdapter);
    }
}