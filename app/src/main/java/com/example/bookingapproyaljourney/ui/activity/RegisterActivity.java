package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.api.LoginClient;
import com.example.bookingapproyaljourney.model.user.User;

import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {
    private float horizontalAxis1, horizontalAxis2, verticalAxis1, verticalAxis2;
    private static int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText edNameRegister = (EditText) findViewById(R.id.edNameRegister);
        EditText edMailRegister = (EditText) findViewById(R.id.edMailRegister);
        EditText edPassRegister = (EditText) findViewById(R.id.edPassRegister);
        EditText edCfPassRegister = (EditText) findViewById(R.id.edCfPassRegister);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        TextView tvSignIn = (TextView) findViewById(R.id.tvSignIn);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
    public void User( User user){
//        Call<User> userCall = LoginClient.getLognin()
    }
}