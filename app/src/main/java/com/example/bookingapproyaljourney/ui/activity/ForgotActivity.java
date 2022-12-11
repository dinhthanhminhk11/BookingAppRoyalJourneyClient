package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityForgotBinding;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.view_model.ForgotPassViewModel;
import com.example.librarytoastcustom.CookieBar;


public class ForgotActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private ActivityForgotBinding binding;
    private float horizontalAxis1, horizontalAxis2, verticalAxis1, verticalAxis2;
    private static int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;
    private ForgotPassViewModel forgotPassViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.gestureDetector = new GestureDetector(ForgotActivity.this, this);
        forgotPassViewModel = new ViewModelProvider(this).get(ForgotPassViewModel.class);

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotActivity.this, RegisterActivity.class));
            }
        });

        binding.btnSend.setOnClickListener(v -> {
            String Email = binding.edEmailForgot.getText().toString();
            validateinfo(Email);

        });

        forgotPassViewModel.getTestResponseMutableLiveData().observe(this, new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    Intent intent = new Intent(ForgotActivity.this, OTPPasswordActivity.class);
                    intent.putExtra(AppConstant.EMAIL_USER, binding.edEmailForgot.getText().toString().trim());
                    startActivity(intent);
                } else {
                    CookieBar.build(ForgotActivity.this)
                            .setTitle(R.string.Notify)
                            .setMessage(testResponse.getMessage())
                            .setIcon(R.drawable.ic_warning_icon_check)
                            .setTitleColor(R.color.black)
                            .setMessageColor(R.color.black)
                            .setDuration(3000)
                            .setBackgroundRes(R.drawable.background_toast)
                            .setCookiePosition(CookieBar.BOTTOM)
                            .show();
                }
            }
        });

        forgotPassViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    private boolean validateinfo(String email) {
        if (email.length() == 0){
            binding.edEmailForgot.requestFocus();
            binding.edEmailForgot.setError(getString(R.string.enterMail));
        }else if (!email.matches("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$")){
            binding.edEmailForgot.requestFocus();
            binding.edEmailForgot.setError(getString(R.string.enterMailFaild));
            return false;
        }else {
            forgotPassViewModel.checkMail(new Email(binding.edEmailForgot.getText().toString()));
        }
        return true;
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

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imageView.setImageResource(R.drawable.ic_shape_login_dark);
            binding.textView.setTextColor(Color.WHITE);
            binding.textView3.setTextColor(Color.WHITE);
            binding.textView4.setTextColor(Color.WHITE);
            binding.textView5.setTextColor(Color.WHITE);
        } else {
            binding.contentView.setBackgroundColor(this.getResources().getColor(R.color.color_F6F6F6));
            binding.imageView.setImageResource(R.drawable.ic_shape_login);
            binding.textView.setTextColor(Color.BLACK);
            binding.textView3.setTextColor(Color.BLACK);
            binding.textView4.setTextColor(Color.BLACK);
            binding.textView5.setTextColor(Color.BLACK);
        }
    }
}