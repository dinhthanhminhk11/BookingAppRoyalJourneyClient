package com.example.bookingapproyaljourney.ui.bottomsheet;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.Loai;
import com.example.bookingapproyaljourney.ui.adapter.LoaiAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BottomSheetFilterHome extends BottomSheetDialog implements View.OnClickListener, LoaiAdapter.EventClick {
    private LinearLayout contentLayout;
    private ImageView imgCancel;
    private TextView tvReset;
    private RangeSlider rangeFilter;
    private TextView starPrice;
    private TextView endPrice;
    private RelativeLayout rating;
    private ImageView imgStar1;
    private ImageView imgStar2;
    private ImageView imgStar3;
    private ImageView imgStar4;
    private ImageView imgStar5;
    private TextView tvCountStart;
    private RecyclerView rcvLoaiPhong;
    private Button btnFilter;
    private Context context;
    private NumberFormat fm = new DecimalFormat("#,###");
    private int giaBd = 120000;
    private int giaKt = 5000000;
    private int sao = 5;
    private List<String> idList = new ArrayList<>();
    private EventClick eventClick;
    private String tk = "63437724ee6dd920372f306a,6343772cee6dd920372f306c,63437732ee6dd920372f306e,63437738ee6dd920372f3070,63437740ee6dd920372f3072";


    public BottomSheetFilterHome(@NonNull Context context, int theme, EventClick eventClick) {
        super(context, theme);
        this.context = context;
        this.eventClick = eventClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.fragment_filter_home);
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        initView();
    }

    private void initView() {
        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        tvReset = (TextView) findViewById(R.id.tvReset);
        rangeFilter = (RangeSlider) findViewById(R.id.rangeFilter);
        starPrice = (TextView) findViewById(R.id.starPrice);
        endPrice = (TextView) findViewById(R.id.endPrice);
        rating = (RelativeLayout) findViewById(R.id.rating);
        imgStar1 = (ImageView) findViewById(R.id.imgStar1);
        imgStar2 = (ImageView) findViewById(R.id.imgStar2);
        imgStar3 = (ImageView) findViewById(R.id.imgStar3);
        imgStar4 = (ImageView) findViewById(R.id.imgStar4);
        imgStar5 = (ImageView) findViewById(R.id.imgStar5);
        tvCountStart = (TextView) findViewById(R.id.tvCountStart);
        rcvLoaiPhong = (RecyclerView) findViewById(R.id.rcvLoaiPhong);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        initOnClick();
        initData();
        List<String> a = new ArrayList<>();
    }

    private void initData() {

        SharedPreferences sharedPreferences_user_data_start_price = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_START_PRICE, MODE_PRIVATE);
        giaBd = sharedPreferences_user_data_start_price.getInt(AppConstant.SHAREDPREFERENCES_USER_START_PRICE, 120000);

        SharedPreferences sharedPreferences_user_data_end_price = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_END_PRICE, MODE_PRIVATE);
        giaKt = sharedPreferences_user_data_end_price.getInt(AppConstant.SHAREDPREFERENCES_USER_END_PRICE, 5000000);

        SharedPreferences sharedPreferences_user_data_star = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_STAR, MODE_PRIVATE);
        sao = sharedPreferences_user_data_star.getInt(AppConstant.SHAREDPREFERENCES_USER_STAR, 5);
        dataStar();

        starPrice.setText(fm.format(giaBd) + " đ");
        endPrice.setText(fm.format(giaKt) + " đ");

        rangeFilter.setValues((float) giaBd, (float) giaKt);

        rangeFilter.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                giaBd = (int) slider.getValues().get(0).floatValue();
                giaKt = (int) slider.getValues().get(1).floatValue();
            }
        });
        List<Loai> list = new ArrayList<>();
        list.add(new Loai("63437724ee6dd920372f306a", getContext().getString(R.string.House), R.drawable.house + ""));
        list.add(new Loai("6343772cee6dd920372f306c", getContext().getString(R.string.Apartment), R.drawable.resort + ""));
        list.add(new Loai("63437732ee6dd920372f306e", getContext().getString(R.string.Hotel), R.drawable.hotels + ""));
        list.add(new Loai("63437738ee6dd920372f3070", getContext().getString(R.string.Villa), R.drawable.villa + ""));
        list.add(new Loai("63437740ee6dd920372f3072", getContext().getString(R.string.Cottage), R.drawable.cabin + ""));
        rcvLoaiPhong.setAdapter(new LoaiAdapter(list, this));
        rcvLoaiPhong.setLayoutManager(new GridLayoutManager(context, 2));

    }

    private void initOnClick() {
        imgStar1.setOnClickListener(this);
        imgStar2.setOnClickListener(this);
        imgStar3.setOnClickListener(this);
        imgStar4.setOnClickListener(this);
        imgStar5.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        rangeFilter.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                starPrice.setText(fm.format(slider.getValues().get(0).floatValue()) + " đ");
                endPrice.setText(fm.format(slider.getValues().get(1).floatValue()) + " đ");
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imgStar1) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_aphal);
            imgStar3.setImageResource(R.drawable.ic_star_aphal);
            imgStar4.setImageResource(R.drawable.ic_star_aphal);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("1.0+");
            sao = 1;
        } else if (id == R.id.imgStar2) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_aphal);
            imgStar4.setImageResource(R.drawable.ic_star_aphal);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("2.0+");
            sao = 2;
        } else if (id == R.id.imgStar3) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_1);
            imgStar4.setImageResource(R.drawable.ic_star_aphal);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("3.0+");
            sao = 3;
        } else if (id == R.id.imgStar4) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_1);
            imgStar4.setImageResource(R.drawable.ic_star_1);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("4.0+");
            sao = 4;
        } else if (id == R.id.imgStar5) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_1);
            imgStar4.setImageResource(R.drawable.ic_star_1);
            imgStar5.setImageResource(R.drawable.ic_star_1);
            tvCountStart.setText("5.0+");
            sao = 5;
        } else if (id == R.id.tvReset) {
            onCreate(null);
            giaBd = 120000;
            giaKt = 5000000;
            sao = 5;
            resetData();
            idList.clear();

        } else if (id == R.id.imgCancel) {
            cancel();
        } else if (id == R.id.btnFilter) {
            Log.e("MinhFilter", giaBd + " d");
            Log.e("MinhFilter", giaKt + " d");
            Log.e("MinhFilter", sao + " sao");
            String idCategory = idList.size() > 0 ? (idList.toString().substring(1, idList.toString().length() - 1)).replace(" ", "") : tk;
            eventClick.onCLickFilter(giaBd + "", giaKt + "", sao + "", idCategory);
            saveCache();
            dismiss();
        }
    }

    @Override
    public void onClick(Loai loai) {
        idList.add(loai.getId());
    }

    @Override
    public void deleteOnClick(Loai loai) {
        idList.remove(loai.getId());
    }

    public interface EventClick {
        void onCLickFilter(String giaBd, String giaKt, String sao, String idLoai);
    }

    private void saveCache() {
        SharedPreferences sharedPreferences_user_start_price = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_START_PRICE, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_start_price = sharedPreferences_user_start_price.edit();
        editor_user_start_price.putInt(AppConstant.SHAREDPREFERENCES_USER_START_PRICE, giaBd);
        editor_user_start_price.commit();

        SharedPreferences sharedPreferences_user_end_price = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_END_PRICE, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_end_price = sharedPreferences_user_end_price.edit();
        editor_user_end_price.putInt(AppConstant.SHAREDPREFERENCES_USER_END_PRICE, giaKt);
        editor_user_end_price.commit();

        SharedPreferences sharedPreferences_user_star = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_STAR, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_start = sharedPreferences_user_star.edit();
        editor_user_start.putInt(AppConstant.SHAREDPREFERENCES_USER_STAR, sao);
        editor_user_start.commit();
    }

    private void resetData() {
        starPrice.setText(fm.format(giaBd) + " đ");
        endPrice.setText(fm.format(giaKt) + " đ");
        rangeFilter.setValues((float) giaBd, (float) giaKt);
        dataStar();
    }

    private void dataStar() {
        if (sao == 1) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_aphal);
            imgStar3.setImageResource(R.drawable.ic_star_aphal);
            imgStar4.setImageResource(R.drawable.ic_star_aphal);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("1.0+");
        } else if (sao == 2) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_aphal);
            imgStar4.setImageResource(R.drawable.ic_star_aphal);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("2.0+");
        } else if (sao == 3) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_1);
            imgStar4.setImageResource(R.drawable.ic_star_aphal);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("3.0+");
        } else if (sao == 4) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_1);
            imgStar4.setImageResource(R.drawable.ic_star_1);
            imgStar5.setImageResource(R.drawable.ic_star_aphal);
            tvCountStart.setText("4.0+");
            sao = 4;
        } else if (sao == 5) {
            imgStar1.setImageResource(R.drawable.ic_star_1);
            imgStar2.setImageResource(R.drawable.ic_star_1);
            imgStar3.setImageResource(R.drawable.ic_star_1);
            imgStar4.setImageResource(R.drawable.ic_star_1);
            imgStar5.setImageResource(R.drawable.ic_star_1);
            tvCountStart.setText("5.0+");
        }
    }
}
