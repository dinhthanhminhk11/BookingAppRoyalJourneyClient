package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetPersonHome extends BottomSheetDialog implements View.OnClickListener {
    private CallBack callBack;
    private ImageView close;
    private TextView reset;
    private ImageView downPerson;
    private TextView person;
    private ImageView upPerson;
    private ImageView downPersonChildren;
    private TextView personChildren;
    private ImageView upPersonChildren;
    private ImageView downCountRoom;
    private TextView countRoom;
    private ImageView upCountRoom;
    private ImageView downAge;
    private TextView countAge;
    private ImageView upAge;
    private TextView btnCancel;
    private Button login;

    private int countPersonText = 1;
    private int countPersonChildrenText;
    private int countRoomText = 1;
    private int ageText;

    public BottomSheetPersonHome(@NonNull Context context, int theme, CallBack callBack) {
        super(context, theme);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_edit_person_home);

        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        downPerson = (ImageView) findViewById(R.id.downPerson);
        person = (TextView) findViewById(R.id.person);
        upPerson = (ImageView) findViewById(R.id.upPerson);
        downPersonChildren = (ImageView) findViewById(R.id.downPersonChildren);
        personChildren = (TextView) findViewById(R.id.personChildren);
        upPersonChildren = (ImageView) findViewById(R.id.upPersonChildren);
        downCountRoom = (ImageView) findViewById(R.id.downCountRoom);
        countRoom = (TextView) findViewById(R.id.countRoom);
        upCountRoom = (ImageView) findViewById(R.id.upCountRoom);
        downAge = (ImageView) findViewById(R.id.downAge);
        countAge = (TextView) findViewById(R.id.countAge);
        upAge = (ImageView) findViewById(R.id.upAge);
        btnCancel = (TextView) findViewById(R.id.btnCancel);
        login = (Button) findViewById(R.id.login);

        close.setOnClickListener(v -> {
            dismiss();
        });

        login.setOnClickListener(v -> {
            int countPerson = Integer.parseInt(person.getText().toString().trim());
            int countPersonChildren = Integer.parseInt(personChildren.getText().toString().trim());
            int age = Integer.parseInt(countAge.getText().toString().trim());
            int countRoomCallback = Integer.parseInt(countRoom.getText().toString().trim());
            callBack.onCLickSum(countPerson, countPersonChildren, countRoomCallback, age);
            dismiss();
        });

        downPersonChildren.setAlpha(0.4f);
        downPersonChildren.setEnabled(false);
        downAge.setAlpha(0.4f);
        downAge.setEnabled(false);

        downPerson.setOnClickListener(this);
        downPersonChildren.setOnClickListener(this);
        downCountRoom.setOnClickListener(this);
        downAge.setOnClickListener(this);
        upPerson.setOnClickListener(this);
        upPersonChildren.setOnClickListener(this);
        upCountRoom.setOnClickListener(this);
        upAge.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upPerson:
                countPersonText += 1;
                person.setText(countPersonText + "");
                checkCountPerson();
                break;


            case R.id.downPerson:
                countPersonText -= 1;
                person.setText(countPersonText + "");
                checkCountPerson();
                break;


            case R.id.upPersonChildren:
                countPersonChildrenText += 1;
                personChildren.setText(countPersonChildrenText + "");
                checkCountChildren();
                break;


            case R.id.downPersonChildren:
                countPersonChildrenText -= 1;
                personChildren.setText(countPersonChildrenText + "");
                checkCountChildren();
                break;


            case R.id.upCountRoom:
                countRoomText += 1;
                countRoom.setText(countRoomText + "");
                checkCountRoom();
                break;


            case R.id.downCountRoom:
                countRoomText -= 1;
                countRoom.setText(countRoomText + "");
                checkCountRoom();
                break;

            case R.id.downAge:
                ageText -= 1;
                countAge.setText(ageText + "");
                checkAge();
                break;
            case R.id.upAge:
                ageText += 1;
                countAge.setText(ageText + "");
                checkAge();
                break;
        }
    }

    private void checkAge() {
        if (ageText > 0) {
            downAge.setAlpha(1f);
            downAge.setEnabled(true);
        } else {
            downAge.setAlpha(0.4f);
            downAge.setEnabled(false);
        }
    }

    private void checkCountRoom() {
        if (countRoomText > 1) {
            downCountRoom.setAlpha(1f);
            downCountRoom.setEnabled(true);
        } else {
            downCountRoom.setAlpha(0.4f);
            downCountRoom.setEnabled(false);
        }
    }

    private void checkCountChildren() {
        if (countPersonChildrenText > 0) {
            downPersonChildren.setAlpha(1f);
            downPersonChildren.setEnabled(true);
        } else {
            downPersonChildren.setAlpha(0.4f);
            downPersonChildren.setEnabled(false);
        }
    }

    private void checkCountPerson() {
        if (countPersonText > 1) {
            downPerson.setAlpha(1f);
            downPerson.setEnabled(true);
        } else {
            downPerson.setAlpha(0.4f);
            downPerson.setEnabled(false);
        }
    }

    public interface CallBack {
        void onCLickCLose();

        void onCLickSum(int person, int children, int countRoom, int age);
    }
}
