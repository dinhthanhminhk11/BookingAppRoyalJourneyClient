package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetEditPerson extends BottomSheetDialog {
    private CallBack callBack;
    private ImageView close;
    private TextView reset;
    private ImageView downPerson;
    private TextView person;
    private ImageView upPerson;
    private ImageView downPersonChildren;
    private TextView personChildren;
    private ImageView upPersonChildren;
    private TextView btnCancel;
    private Button login;

    public BottomSheetEditPerson(@NonNull Context context, int theme, CallBack callBack) {
        super(context, theme);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_edit_person);

        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        downPerson = (ImageView) findViewById(R.id.downPerson);
        person = (TextView) findViewById(R.id.person);
        upPerson = (ImageView) findViewById(R.id.upPerson);
        downPersonChildren = (ImageView) findViewById(R.id.downPersonChildren);
        personChildren = (TextView) findViewById(R.id.personChildren);
        upPersonChildren = (ImageView) findViewById(R.id.upPersonChildren);
        btnCancel = (TextView) findViewById(R.id.btnCancel);
        login = (Button) findViewById(R.id.login);

        close.setOnClickListener(v->{
            callBack.onCLickCLose();
        });

        login.setOnClickListener(v->{

        });



    }

    public interface CallBack {
        void onCLickCLose();
    }
}
