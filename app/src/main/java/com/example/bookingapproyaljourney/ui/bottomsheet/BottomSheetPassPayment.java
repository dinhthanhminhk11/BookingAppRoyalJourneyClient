package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.ui.custom.edittext.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetPassPayment extends BottomSheetDialog {
    private ImageView close;
    private TextView reset;
    private CallBack callBack;
    private PinView secondPinView;
    private PinView pinView;

    public BottomSheetPassPayment(@NonNull Context context, int theme, CallBack callBack) {
        super(context, theme);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_payment_pass);
        pinView = ((PinView) findViewById(R.id.secondPinView));
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        pinView.setAnimationEnable(true);
        close.setOnClickListener(v -> {
            dismiss();
        });

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    callBack.onClickPayment(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public interface CallBack {
        void onCLickCLose();

        void onClickPayment(String s);
    }
}
