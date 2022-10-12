package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.ui.adapter.FacilitiesAdapter;
import com.example.bookingapproyaljourney.ui.adapter.SortedByAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFilterMap extends BottomSheetDialog implements View.OnClickListener{
    private LinearLayout contentLayout;
    private ImageView close;
    private TextView reset;
    private RecyclerView recyclerViewCategory;
    private RangeSlider settingsMissionChangeShakeDifSlider;
    private Button starPrice;
    private Button endPrice;
    private RelativeLayout rating;
    private ImageView imageStar1;
    private ImageView imageStar2;
    private ImageView imageStar3;
    private ImageView imageStar4;
    private ImageView imageStar5;
    private TextView countStar;
    private RecyclerView recyclerViewConvenient;
    private SortedByAdapter sortedByAdapter;
    private Context context;
    private Callback callback;
    private FacilitiesAdapter facilitiesAdapter;

    public BottomSheetFilterMap(@NonNull Context context, int theme, Callback callback) {
        super(context, theme);
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.fragment_filter_map);
        initView();
    }

    private void initView() {

        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        recyclerViewCategory = (RecyclerView) findViewById(R.id.recycler_view_category);
        settingsMissionChangeShakeDifSlider = (RangeSlider) findViewById(R.id.settingsMission_changeShakeDif_slider);
        starPrice = (Button) findViewById(R.id.starPrice);
        endPrice = (Button) findViewById(R.id.endPrice);
        rating = (RelativeLayout) findViewById(R.id.rating);
        imageStar1 = (ImageView) findViewById(R.id.imageStar1);
        imageStar2 = (ImageView) findViewById(R.id.imageStar2);
        imageStar3 = (ImageView) findViewById(R.id.imageStar3);
        imageStar4 = (ImageView) findViewById(R.id.imageStar4);
        imageStar5 = (ImageView) findViewById(R.id.imageStar5);
        countStar = (TextView) findViewById(R.id.countStar);
        recyclerViewConvenient = (RecyclerView) findViewById(R.id.recycler_view_convenient);
        countStar.setText("5.0+");
        initOnClick();
        initData();
    }

    private void initData() {
        List<Category> list = new ArrayList<>();
        list.add(new Category("1", "All"));
        list.add(new Category("1", "Vila"));
        list.add(new Category("1", "Hotel"));
        list.add(new Category("1", "House"));
        list.add(new Category("1", "Apartment"));

        List<Convenient> list2 = new ArrayList<>();
        list2.add(new Convenient(1, "Restoran"));
        list2.add(new Convenient(1, "Parking"));
        list2.add(new Convenient(1, "Room Service"));
        list2.add(new Convenient(1, "Fitness center"));
        list2.add(new Convenient(1, "Wi-Fi"));
        sortedByAdapter = new SortedByAdapter(list, new SortedByAdapter.Callback() {
            @Override
            public void onClick() {

            }
        });

        facilitiesAdapter = new FacilitiesAdapter(list2);

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(sortedByAdapter);

        recyclerViewConvenient.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewConvenient.setAdapter(facilitiesAdapter);
    }

    private void initOnClick() {
        imageStar1.setOnClickListener(this);
        imageStar2.setOnClickListener(this);
        imageStar3.setOnClickListener(this);
        imageStar4.setOnClickListener(this);
        imageStar5.setOnClickListener(this);
        close.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imageStar1) {
            imageStar1.setImageResource(R.drawable.ic_star_1);
            imageStar2.setImageResource(R.drawable.ic_star_aphal);
            imageStar3.setImageResource(R.drawable.ic_star_aphal);
            imageStar4.setImageResource(R.drawable.ic_star_aphal);
            imageStar5.setImageResource(R.drawable.ic_star_aphal);
            countStar.setText("1.0+");
        } else if (id == R.id.imageStar2) {
            imageStar1.setImageResource(R.drawable.ic_star_1);
            imageStar2.setImageResource(R.drawable.ic_star_1);
            imageStar3.setImageResource(R.drawable.ic_star_aphal);
            imageStar4.setImageResource(R.drawable.ic_star_aphal);
            imageStar5.setImageResource(R.drawable.ic_star_aphal);
            countStar.setText("2.0+");
        } else if (id == R.id.imageStar3) {
            imageStar1.setImageResource(R.drawable.ic_star_1);
            imageStar2.setImageResource(R.drawable.ic_star_1);
            imageStar3.setImageResource(R.drawable.ic_star_1);
            imageStar4.setImageResource(R.drawable.ic_star_aphal);
            imageStar5.setImageResource(R.drawable.ic_star_aphal);
            countStar.setText("3.0+");
        } else if (id == R.id.imageStar4) {
            imageStar1.setImageResource(R.drawable.ic_star_1);
            imageStar2.setImageResource(R.drawable.ic_star_1);
            imageStar3.setImageResource(R.drawable.ic_star_1);
            imageStar4.setImageResource(R.drawable.ic_star_1);
            imageStar5.setImageResource(R.drawable.ic_star_aphal);
            countStar.setText("4.0+");
        } else if (id == R.id.imageStar5) {
            imageStar1.setImageResource(R.drawable.ic_star_1);
            imageStar2.setImageResource(R.drawable.ic_star_1);
            imageStar3.setImageResource(R.drawable.ic_star_1);
            imageStar4.setImageResource(R.drawable.ic_star_1);
            imageStar5.setImageResource(R.drawable.ic_star_1);
            countStar.setText("5.0+");
        } else if (id == R.id.close) {
            Log.e("Minh", "Click");
            callback.onCLickCLose();
        }
    }

    public interface Callback {
        void onCLickCLose();
    }
}
