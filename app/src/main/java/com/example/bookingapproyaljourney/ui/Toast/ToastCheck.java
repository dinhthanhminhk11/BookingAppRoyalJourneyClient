package com.example.bookingapproyaljourney.ui.Toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ToastCheck extends BottomSheetDialog {
    private TextView title;
    private TextView content;
    private ImageView close;
    private String titleDiaLog;
    private String contentDiaLog;
    private ImageView icon;
    private int iconRec;

    public ToastCheck(@NonNull Context context, int theme, String titleDiaLog, String contentDiaLog, int icon) {
        super(context, theme);
        this.contentDiaLog = contentDiaLog;
        this.titleDiaLog = titleDiaLog;
        this.iconRec = icon;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.toast_check);
        initView();
    }


    private void initView() {
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        close = (ImageView) findViewById(R.id.close);

        icon = (ImageView) findViewById(R.id.icon);
        icon.setImageResource(iconRec);
        title.setText(titleDiaLog);
        content.setText(contentDiaLog);

        close.setOnClickListener(v -> {
            dismiss();
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 5000);
    }
}
