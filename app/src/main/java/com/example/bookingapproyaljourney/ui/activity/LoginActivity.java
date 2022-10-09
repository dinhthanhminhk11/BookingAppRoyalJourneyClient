package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.librarycireleimage.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edEmail = (EditText) findViewById(R.id.edEmail);
        EditText edPass = (EditText) findViewById(R.id.edPass);
        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        TextView tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        TextView tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });

        btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}