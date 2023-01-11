package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetEditPerson extends BottomSheetDialog implements View.OnClickListener {
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
    private int countPersonText;
    private int countPersonChildrenText;
    private int countRoomText;
    private TextView textCountRoom;
    private ImageView downCountRoom;
    private TextView countRoom;
    private TextView checkPerson;
    private TextView checkChildren;
    private TextView textBottomSheetPerson;
    private ImageView upCountRoom;
    private HotelBillResponse hotelBillResponse;

    private String maxPerson;
    private String textMore;
    private String textMore2;
    private int countRoomResponse;
    private int countPersonResponse;
    private int countChildrenResponse;
    private int sumPerson;
    private int sumChildren;

    public BottomSheetEditPerson(@NonNull Context context, int theme, CallBack callBack, HotelBillResponse hotelBillResponse) {
        super(context, theme);
        this.callBack = callBack;
        this.hotelBillResponse = hotelBillResponse;
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


        textCountRoom = (TextView) findViewById(R.id.textCountRoom);
        downCountRoom = (ImageView) findViewById(R.id.downCountRoom);
        countRoom = (TextView) findViewById(R.id.countRoom);
        checkPerson = (TextView) findViewById(R.id.checkPerson);
        checkChildren = (TextView) findViewById(R.id.checkChildren);
        textBottomSheetPerson = (TextView) findViewById(R.id.textBottomSheetPerson);
        upCountRoom = (ImageView) findViewById(R.id.upCountRoom);

        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        checkPerson.setText((hotelBillResponse.getAgeChildren() + 1) + " tuổi trở lên");
        checkChildren.setText("Độ tuổi 1 - " + hotelBillResponse.getAgeChildren());
        textCountRoom.setText("Còn " + hotelBillResponse.getCountRoom() + " phòng như thế này");

        maxPerson = (hotelBillResponse.getMaxPeople() + hotelBillResponse.getMaxChildren()) + " người";

        textMore = hotelBillResponse.getMaxPeople() + " người lớn";

        textMore2 = hotelBillResponse.getMaxChildren() + " trẻ em";

        textBottomSheetPerson.setText("Chỗ này cho phép tối đa " + maxPerson + ", bao gồm " + textMore + " và " + textMore2 + ", đối với 1 phòng, nếu có thú cưng vui lòng thông báo với quản lí khách sạn");

        countPersonText = Integer.parseInt(person.getText().toString());
        countRoomText = Integer.parseInt(countRoom.getText().toString());
        countPersonChildrenText = Integer.parseInt(personChildren.getText().toString());

        countRoomResponse = hotelBillResponse.getCountRoom();
        countPersonResponse = hotelBillResponse.getMaxPeople();
        countChildrenResponse = hotelBillResponse.getMaxChildren();

        sumPerson = countPersonResponse * 1;
        sumChildren = countChildrenResponse * 1;

        if (countPersonText == 1 || countPersonChildrenText == 0 || countRoomText == 1) {
            downPersonChildren.setAlpha(0.4f);
            downCountRoom.setAlpha(0.4f);
            downPersonChildren.setEnabled(false);
            downCountRoom.setEnabled(false);
            downPerson.setAlpha(0.4f);
            downPerson.setEnabled(false);
        } else {
            downPerson.setAlpha(1f);
            downPersonChildren.setAlpha(1f);
            downCountRoom.setAlpha(1f);
            downPerson.setEnabled(true);
            downPersonChildren.setEnabled(true);
            downCountRoom.setEnabled(true);
        }

        close.setOnClickListener(v -> {
            callBack.onCLickCLose();
        });

        login.setOnClickListener(v -> {
            int sumPerson = 0;
            int countPerson = Integer.parseInt(person.getText().toString().trim());
            int countPersonChildren = Integer.parseInt(personChildren.getText().toString().trim());
            sumPerson = countPerson + countPersonChildren;

            int countRoomCallback = Integer.parseInt(countRoom.getText().toString().trim());
            callBack.onCLickSum(countPerson, countPersonChildren, countRoomCallback);
            callBack.onCLickCLose();
        });

        downPerson.setOnClickListener(this);
        downPersonChildren.setOnClickListener(this);
        downCountRoom.setOnClickListener(this);
        upPerson.setOnClickListener(this);
        upPersonChildren.setOnClickListener(this);
        upCountRoom.setOnClickListener(this);

        btnCancel.setOnClickListener(v -> {
            callBack.onCLickCLose();
        });


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
        }
    }

    public interface CallBack {
        void onCLickCLose();

        void onCLickSum(int person , int children, int countRoom);
    }

    public void checkCountPerson() {
        if (countPersonText > 1) {
            downPerson.setAlpha(1f);
            downPerson.setEnabled(true);
        } else {
            downPerson.setAlpha(0.4f);
            downPerson.setEnabled(false);
        }

        if (countPersonText > countPersonResponse - 1) {
            upPerson.setAlpha(0.4f);
            upPerson.setEnabled(false);
        } else {
            upPerson.setAlpha(1f);
            upPerson.setEnabled(true);
        }

        if (countPersonText > sumPerson - 1) {
            upPerson.setAlpha(0.4f);
            upPerson.setEnabled(false);
        } else {
            upPerson.setAlpha(1f);
            upPerson.setEnabled(true);
        }
    }

    public void checkCountChildren() {
        if (countPersonChildrenText > 0) {
            downPersonChildren.setAlpha(1f);
            downPersonChildren.setEnabled(true);
        } else {
            downPersonChildren.setAlpha(0.4f);
            downPersonChildren.setEnabled(false);
        }

        if (countPersonChildrenText > countChildrenResponse - 1) {
            upPersonChildren.setAlpha(0.4f);
            upPersonChildren.setEnabled(false);
        } else {
            upPersonChildren.setAlpha(1f);
            upPersonChildren.setEnabled(true);
        }

        if (countPersonChildrenText > sumChildren - 1) {
            upPersonChildren.setAlpha(0.4f);
            upPersonChildren.setEnabled(false);
        } else {
            upPersonChildren.setAlpha(1f);
            upPersonChildren.setEnabled(true);
        }

    }

    public void checkCountRoom() {
        maxPerson = ((hotelBillResponse.getMaxPeople() * countRoomText) + (hotelBillResponse.getMaxChildren() * countRoomText)) + " người";

        textMore = (hotelBillResponse.getMaxPeople() * countRoomText) + " người lớn";

        textMore2 = (hotelBillResponse.getMaxChildren() * countRoomText) + " trẻ em";

        textBottomSheetPerson.setText("Chỗ này cho phép tối đa " + maxPerson + ", bao gồm " + textMore + " và " + textMore2 + ", đối với " + countRoomText + " phòng, nếu có thú cưng vui lòng thông báo với quản lí khách sạn");

        sumPerson = countPersonResponse * countRoomText;
        sumChildren = countChildrenResponse * countRoomText;


        if (countRoomText > 1) {
            downCountRoom.setAlpha(1f);
            downCountRoom.setEnabled(true);
        } else {
            downCountRoom.setAlpha(0.4f);
            downCountRoom.setEnabled(false);
        }
        if (countRoomText > countRoomResponse - 1) {
            upCountRoom.setAlpha(0.4f);
            upCountRoom.setEnabled(false);
        } else {
            upCountRoom.setAlpha(1f);
            upCountRoom.setEnabled(true);
        }

        if (countPersonText <= sumPerson || countPersonChildrenText <= sumChildren) {
            upPerson.setAlpha(1f);
            upPerson.setEnabled(true);
            upPersonChildren.setAlpha(1f);
            upPersonChildren.setEnabled(true);
        } else {
            upPerson.setAlpha(0.4f);
            upPerson.setEnabled(false);
            upPersonChildren.setAlpha(0.4f);
            upPersonChildren.setEnabled(false);
        }

        if (countPersonText >= sumPerson || countPersonChildrenText >= sumChildren) {
            countPersonText = sumPerson;
            countPersonChildrenText = sumChildren;
            upPerson.setAlpha(0.4f);
            upPerson.setEnabled(false);
            upPersonChildren.setAlpha(0.4f);
            upPersonChildren.setEnabled(false);
            person.setText(sumPerson + "");
            personChildren.setText(sumChildren + "");
        } else {

        }
    }
}
