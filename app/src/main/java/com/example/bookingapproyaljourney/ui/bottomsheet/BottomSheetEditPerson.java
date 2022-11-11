package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
    private int countPerson;
    private int countPersonChildren;

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
        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        countPerson = Integer.parseInt(person.getText().toString().trim());
        countPersonChildren = Integer.parseInt(personChildren.getText().toString().trim());

        if (countPerson == 0 || countPersonChildren == 0) {
            downPerson.setAlpha(0.4f);
            downPersonChildren.setAlpha(0.4f);
            downPerson.setEnabled(false);
            downPersonChildren.setEnabled(false);
        } else {
            downPerson.setAlpha(1f);
            downPersonChildren.setAlpha(1f);
            downPerson.setEnabled(true);
            downPersonChildren.setEnabled(true);
        }

        close.setOnClickListener(v -> {
            callBack.onCLickCLose();
        });

        login.setOnClickListener(v -> {
            int sumPerson = 0;
            int countPerson = Integer.parseInt(person.getText().toString().trim());
            int countPersonChildren = Integer.parseInt(personChildren.getText().toString().trim());
            sumPerson = countPerson + countPersonChildren;
            callBack.onCLickSum(sumPerson);
            callBack.onCLickCLose();
        });

        downPerson.setOnClickListener(v -> {
            countPerson -= 1;
            person.setText(countPerson + "");
            if (countPerson == 0) {
                downPerson.setAlpha(0.4f);
                downPerson.setEnabled(false);
            } else {
                downPerson.setAlpha(1f);
                downPerson.setEnabled(true);
            }
        });

        downPersonChildren.setOnClickListener(v -> {
            countPersonChildren -= 1;
            personChildren.setText(countPersonChildren + "");
            if (countPersonChildren == 0) {
                downPersonChildren.setAlpha(0.4f);
                downPersonChildren.setEnabled(false);
            } else {
                downPersonChildren.setAlpha(1f);
                downPersonChildren.setEnabled(true);
            }
        });

        upPerson.setOnClickListener(v -> {
            countPerson += 1;
            person.setText(countPerson + "");
            if (countPerson == 0) {
                downPerson.setAlpha(0.4f);
                downPerson.setEnabled(false);
            } else {
                downPerson.setAlpha(1f);
                downPerson.setEnabled(true);
            }
        });

        upPersonChildren.setOnClickListener(v -> {
            countPersonChildren += 1;
            personChildren.setText(countPersonChildren + "");
            if (countPersonChildren == 0) {
                downPersonChildren.setAlpha(0.4f);
                downPersonChildren.setEnabled(false);
            } else {
                downPersonChildren.setAlpha(1f);
                downPersonChildren.setEnabled(true);
            }
        });

        btnCancel.setOnClickListener(v->{
            callBack.onCLickCLose();
        });


    }

    public interface CallBack {
        void onCLickCLose();

        void onCLickSum(int sum);
    }
}
