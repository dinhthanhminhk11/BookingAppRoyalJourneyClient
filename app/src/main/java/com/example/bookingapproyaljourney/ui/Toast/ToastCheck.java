package com.example.bookingapproyaljourney.ui.Toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ToastCheck extends BottomSheetDialog {
    public ToastCheck(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.toast_check);
    }
}
