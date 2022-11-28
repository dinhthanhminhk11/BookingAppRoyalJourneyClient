package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetCancellationPolicy extends BottomSheetDialog {
    private ImageView close;
    private TextView dateStart;
    private TextView startTime;
    private TextView dateStartVer2;
    private TextView startTimeVer2;
    private TextView btnCancellationPolicy;
    private CallbackOnClickBottomSheetCancellationPolicy callbackOnClickBottomSheetCancellationPolicy;
    private HouseDetailResponse houseDetailResponse1;
    public BottomSheetCancellationPolicy(@NonNull Context context, int theme, CallbackOnClickBottomSheetCancellationPolicy callback, HouseDetailResponse houseDetailResponse) {
        super(context, theme);
        this.callbackOnClickBottomSheetCancellationPolicy = callback;
        this.houseDetailResponse1 = houseDetailResponse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.fragmentcancelllationpolicy);
        getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        initView();
    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        dateStart = (TextView) findViewById(R.id.dateStart);
        startTime = (TextView) findViewById(R.id.startTime);
        dateStartVer2 = (TextView) findViewById(R.id.dateStartVer2);
        startTimeVer2 = (TextView) findViewById(R.id.startTimeVer2);
        btnCancellationPolicy = (TextView) findViewById(R.id.btnCancellationPolicy);
        btnCancellationPolicy.setPaintFlags(btnCancellationPolicy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        dateStart.setText(houseDetailResponse1.getStartDate());
        dateStartVer2.setText("Sau "+houseDetailResponse1.getStartDate());
        startTimeVer2.setText(houseDetailResponse1.getOpening());
        btnCancellationPolicy.setOnClickListener(v -> {
            callbackOnClickBottomSheetCancellationPolicy.onclickBtn();
        });

        close.setOnClickListener(v->{
            callbackOnClickBottomSheetCancellationPolicy.onClose();
        });
    }

    public interface CallbackOnClickBottomSheetCancellationPolicy {
        void onclickBtn();
        void onClose();
    }
}
