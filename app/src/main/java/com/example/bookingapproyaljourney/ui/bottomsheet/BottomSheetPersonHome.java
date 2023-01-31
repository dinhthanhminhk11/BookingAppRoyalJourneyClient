package com.example.bookingapproyaljourney.ui.bottomsheet;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.bookingapproyaljourney.constants.AppConstant;
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

    private int countPersonText;
    private int countPersonChildrenText;
    private int countRoomText;
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
        downCountRoom.setAlpha(0.4f);
        downCountRoom.setEnabled(false);
        downPerson.setAlpha(0.4f);
        downPerson.setEnabled(false);

        downPerson.setOnClickListener(this);
        downPersonChildren.setOnClickListener(this);
        downCountRoom.setOnClickListener(this);
        downAge.setOnClickListener(this);
        upPerson.setOnClickListener(this);
        upPersonChildren.setOnClickListener(this);
        upCountRoom.setOnClickListener(this);
        upAge.setOnClickListener(this);

        initDataCache();

    }

    @SuppressLint("SetTextI18n")
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
        if (ageText > 1) {
            downAge.setAlpha(1f);
            downAge.setEnabled(true);
        } else {
            downAge.setAlpha(0.4f);
            downAge.setEnabled(false);
        }

        if (ageText < 17) {
            upAge.setAlpha(1f);
            upAge.setEnabled(true);
        } else {
            upAge.setAlpha(0.4f);
            upAge.setEnabled(false);
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

        if (countRoomText < 16) {
            upCountRoom.setAlpha(1f);
            upCountRoom.setEnabled(true);
        } else {
            upCountRoom.setAlpha(0.4f);
            upCountRoom.setEnabled(false);
        }

        if (Integer.parseInt(countRoom.getText().toString()) > Integer.parseInt(person.getText().toString())) {
            countPersonText = Integer.parseInt(countRoom.getText().toString());
            person.setText(countPersonText + "");
            checkCountPerson();
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

        if (countPersonChildrenText < 50) {
            upPersonChildren.setAlpha(1f);
            upPersonChildren.setEnabled(true);
        } else {
            upPersonChildren.setAlpha(0.4f);
            upPersonChildren.setEnabled(false);
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

        if (Integer.parseInt(countRoom.getText().toString()) > Integer.parseInt(person.getText().toString())) {
            countRoomText = Integer.parseInt(person.getText().toString());
            countRoom.setText(countRoomText + "");
            checkCountRoom();
        }
        if (countPersonText < 50) {
            upPerson.setAlpha(1f);
            upPerson.setEnabled(true);
        } else {
            upPerson.setAlpha(0.4f);
            upPerson.setEnabled(false);
        }
    }

    public interface CallBack {
        void onCLickCLose();

        void onCLickSum(int person, int children, int countRoom, int age);
    }

    public void initDataCache() {
        SharedPreferences sharedPreferences_user_count_room = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, MODE_PRIVATE);
        int countRoomCache = sharedPreferences_user_count_room.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, 2);
        countRoomText = countRoomCache;

        SharedPreferences sharedPreferences_user_count_person = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, MODE_PRIVATE);
        int countPersonCache = sharedPreferences_user_count_person.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, 2);
        countPersonText = countPersonCache;

        SharedPreferences sharedPreferences_user_count_children = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, MODE_PRIVATE);
        int countChildrenCache = sharedPreferences_user_count_children.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, 2);
        countPersonChildrenText = countChildrenCache;

        SharedPreferences sharedPreferences_user_age_children = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, MODE_PRIVATE);
        int ageChildrenCache = sharedPreferences_user_age_children.getInt(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, 1);
        ageText = ageChildrenCache;

        countRoom.setText(countRoomCache + "");
        person.setText(countPersonCache + "");
        personChildren.setText(countChildrenCache + "");
        countAge.setText(ageChildrenCache + "");

        checkAge();
        checkCountPerson();
        checkCountRoom();
        checkCountChildren();
    }
}
