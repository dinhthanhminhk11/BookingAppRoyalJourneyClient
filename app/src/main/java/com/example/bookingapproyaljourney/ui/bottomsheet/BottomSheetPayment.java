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

import com.example.bookingapproyaljourney.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetPayment extends BottomSheetDialog {
    private CallBack callBack;
    private ImageView close;
    private TextView reset;
    private LinearLayout payGoogle;
    private LinearLayout payVisa;
    private LinearLayout payMaterCard;
    private LinearLayout payPayPal;

    public BottomSheetPayment(@NonNull Context context, int theme, CallBack callBack) {
        super(context, theme);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_payment);
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        payGoogle = (LinearLayout) findViewById(R.id.payGoogle);
        payVisa = (LinearLayout) findViewById(R.id.payVisa);
        payMaterCard = (LinearLayout) findViewById(R.id.payMaterCard);
        payPayPal = (LinearLayout) findViewById(R.id.payPayPal);

        close.setOnClickListener(v -> {
            callBack.onCLickCLose();
        });

        payGoogle.setOnClickListener(v -> {

        });

        payPayPal.setOnClickListener(v -> {

        });

        payMaterCard.setOnClickListener(v -> {

        });

        payVisa.setOnClickListener(v -> {
            callBack.onClickPayment();
        });
    }

    public interface CallBack {
        void onCLickCLose();

        void onClickPayment();
    }
}
