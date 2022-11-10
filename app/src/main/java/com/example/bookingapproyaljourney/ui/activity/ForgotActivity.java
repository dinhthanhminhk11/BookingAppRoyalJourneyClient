package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.R;


public class ForgotActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private float horizontalAxis1, horizontalAxis2, verticalAxis1, verticalAxis2;
    private static int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        EditText edEmailForgot = (EditText) findViewById(R.id.edEmailForgot);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        TextView tvSignUp = (TextView) findViewById(R.id.tvSignUp);

        this.gestureDetector = new GestureDetector(ForgotActivity.this, this);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotActivity.this, RegisterActivity.class));
            }
        });

        btnSend.setOnClickListener(v -> {
            startActivity(new Intent(ForgotActivity.this, OTPPasswordActivity.class));
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                horizontalAxis1 = event.getX();
                verticalAxis1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                horizontalAxis2 = event.getX();
                verticalAxis2 = event.getY();

                float valueX = horizontalAxis2 - horizontalAxis1;

                if (Math.abs(valueX) > MIN_DISTANCE) {
                    if (horizontalAxis2 > horizontalAxis1) {
                        onBackPressed();
                    } else {
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}