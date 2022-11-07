package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Bathroom;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.ui.adapter.BathdRoomAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class BottomSheetBathRoom extends BottomSheetDialog {
    private LinearLayout bathRoomList;
    private ImageView close;
    private TextView reset;
    private RecyclerView rcvBathdRoomList;
    private List<Bathroom> data;
    private Context context;


    public BottomSheetBathRoom(@NonNull Context context, int theme, List<Bathroom> data) {
        super(context);
        this.data = data;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.item_convenientlist);
        initView();
    }

    private void initView() {
        bathRoomList = (LinearLayout) findViewById(R.id.bathRoomList);
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        rcvBathdRoomList = (RecyclerView) findViewById(R.id.rcvBathdRoomList);
        initData();
    }

    private void initData() {
        rcvBathdRoomList.setHasFixedSize(true);
        rcvBathdRoomList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        BathdRoomAdapter bathdRoomAdapter = new BathdRoomAdapter(data,context);
        rcvBathdRoomList.setAdapter(bathdRoomAdapter);
    }
}