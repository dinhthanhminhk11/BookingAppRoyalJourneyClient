package com.example.bookingapproyaljourney.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.google.android.material.appbar.MaterialToolbar;

public class ContactActivity extends AppCompatActivity {
    private RelativeLayout layoutAboutUs;
    private MaterialToolbar toolBar;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private TextView textView14;
    private TextView textView15;
    private TextView textView16;
    private TextView textView17;
    private TextView textView18;
    private TextView textView19;
    private TextView RoyalJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        layoutAboutUs = (RelativeLayout) findViewById(R.id.layoutAboutUs);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        textView1 = (TextView) findViewById(R.id.textView18);
        textView2 = (TextView) findViewById(R.id.textView6);
        textView3 = (TextView) findViewById(R.id.textView9);
        textView4 = (TextView) findViewById(R.id.textView11);
        textView5 = (TextView) findViewById(R.id.textView12);
        textView6 = (TextView) findViewById(R.id.textView13);
        textView7 = (TextView) findViewById(R.id.textView14);
        textView8 = (TextView) findViewById(R.id.textView15);
        textView9 = (TextView) findViewById(R.id.textView17);
        textView10 = (TextView) findViewById(R.id.textView16);
        textView11 = (TextView) findViewById(R.id.textView20);
        textView12 = (TextView) findViewById(R.id.textView19);
        textView13 = (TextView) findViewById(R.id.textView21);
        textView14 = (TextView) findViewById(R.id.textView22);
        textView15 = (TextView) findViewById(R.id.textView23);
        textView16 = (TextView) findViewById(R.id.textView24);
        textView17 = (TextView) findViewById(R.id.textView27);
        textView18 = (TextView) findViewById(R.id.textView25);
        textView19 = (TextView) findViewById(R.id.textView26);
        RoyalJourney = (TextView) findViewById(R.id.RoyalJourney);

        toolBar.setTitle(this.getString(R.string.veRoyalJourney));
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

//  thay đổi Theme
        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            toolBar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);
            toolBar.setTitleTextColor(Color.WHITE);
            layoutAboutUs.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            tv1.setTextColor(Color.WHITE);
            tv2.setTextColor(Color.WHITE);

            tv4.setTextColor(Color.WHITE);

            tv6.setTextColor(Color.WHITE);

            tv8.setTextColor(Color.WHITE);
            textView2.setTextColor(Color.WHITE);
            textView3.setTextColor(Color.WHITE);
            textView4.setTextColor(Color.WHITE);
            textView5.setTextColor(Color.WHITE);
            textView6.setTextColor(Color.WHITE);
            textView7.setTextColor(Color.WHITE);
            textView8.setTextColor(Color.WHITE);
            textView9.setTextColor(Color.WHITE);
            textView10.setTextColor(Color.WHITE);
            textView11.setTextColor(Color.WHITE);
            textView12.setTextColor(Color.WHITE);
            textView13.setTextColor(Color.WHITE);
            textView14.setTextColor(Color.WHITE);
            textView15.setTextColor(Color.WHITE);
            textView16.setTextColor(Color.WHITE);
            textView17.setTextColor(Color.WHITE);
            RoyalJourney.setTextColor(Color.WHITE);

        } else {
            toolBar.setBackgroundColor(this.getResources().getColor(R.color.white));
            toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
            toolBar.setTitleTextColor(Color.BLACK);
            layoutAboutUs.setBackgroundColor(this.getResources().getColor(R.color._F9F9F9));
            tv2.setTextColor(Color.BLACK);
            tv4.setTextColor(Color.BLACK);
            tv6.setTextColor(Color.BLACK);
            tv8.setTextColor(Color.BLACK);
            textView2.setTextColor(Color.BLACK);
            textView3.setTextColor(Color.BLACK);
            textView4.setTextColor(Color.BLACK);
            textView5.setTextColor(Color.BLACK);
            textView6.setTextColor(Color.BLACK);
            textView7.setTextColor(Color.BLACK);
            textView8.setTextColor(Color.BLACK);
            textView9.setTextColor(Color.BLACK);
            textView10.setTextColor(Color.BLACK);
            textView11.setTextColor(Color.BLACK);
            textView12.setTextColor(Color.BLACK);
            textView13.setTextColor(Color.BLACK);
            textView14.setTextColor(Color.BLACK);
            textView15.setTextColor(Color.BLACK);
            textView16.setTextColor(Color.BLACK);
            textView17.setTextColor(Color.BLACK);
            RoyalJourney.setTextColor(Color.BLACK);
        }
    }

}