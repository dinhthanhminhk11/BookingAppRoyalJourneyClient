package com.example.bookingapproyaljourney.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.bookingapproyaljourney.R;
import com.example.librarycireleimage.CircleImageView;
import com.google.android.material.appbar.MaterialToolbar;

public class EditProfile extends AppCompatActivity {

    private MaterialToolbar toolBar;
    private CircleImageView avtEditProfile;
    private ImageView cameraEditProfile;
    private LinearLayout linearLayout3;
    private EditText nameEditProfile;
    private EditText phoneEditProfile;
    private EditText addressEditProfile;
    private EditText idCard;
    private AppCompatButton saveEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        avtEditProfile = (CircleImageView) findViewById(R.id.avtEditProfile);
        cameraEditProfile = (ImageView) findViewById(R.id.cameraEditProfile);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        nameEditProfile = (EditText) findViewById(R.id.nameEditProfile);
        phoneEditProfile = (EditText) findViewById(R.id.phoneEditProfile);
        addressEditProfile = (EditText) findViewById(R.id.addressEditProfile);
        idCard = (EditText) findViewById(R.id.idCard);
        saveEditProfile = (AppCompatButton) findViewById(R.id.saveEditProfile);

        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolBar.setTitle("Edit Profile");
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

    }
}