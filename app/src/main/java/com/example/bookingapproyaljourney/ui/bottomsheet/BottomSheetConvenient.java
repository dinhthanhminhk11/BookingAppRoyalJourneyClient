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
import com.example.bookingapproyaljourney.model.hotel.TienNghiK;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientAdapter;
import com.example.bookingapproyaljourney.ui.adapter.ConvenientListAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetConvenient extends BottomSheetDialog {
    private LinearLayout convenientList;
    private ImageView close;
    private TextView reset;
    private RecyclerView rcvConvenientList;
    private ArrayList<TienNghiK> data;
    private Context context;
    private CallBack callBack;

    public BottomSheetConvenient(@NonNull Context context, int theme, ArrayList<TienNghiK> data, CallBack callback) {
        super(context, theme);
        this.data = data;
        this.context = context;
        this.callBack = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.item_convenientlist);
        initView();
    }

    private void initView() {
        convenientList = (LinearLayout) findViewById(R.id.convenientList);
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        rcvConvenientList = (RecyclerView) findViewById(R.id.rcvConvenientList);
        initData();
    }

    private void initData() {
        rcvConvenientList.setHasFixedSize(true);
        rcvConvenientList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ConvenientListAdapter convenientListAdapter = new ConvenientListAdapter(context);
        convenientListAdapter.setConvenientTestList(data);
        rcvConvenientList.setAdapter(convenientListAdapter);
        close.setOnClickListener(v -> {
            callBack.onCLickCLose();
        });
    }

    public interface CallBack {
        void onCLickCLose();
    }

}
