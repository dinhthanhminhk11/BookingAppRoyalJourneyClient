package com.example.bookingapproyaljourney.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.ui.custom.mutilfragment.Slidr;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBackCustom();
    }

    public void onBackCustom() {
        Slidr.attach(this);
    }
}
