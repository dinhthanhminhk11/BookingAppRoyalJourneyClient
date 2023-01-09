package com.example.bookingapproyaljourney.ui.bottomsheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.ui.custom.edittext.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetPassPayment extends BottomSheetDialog {
    private ImageView close;
    private TextView reset;
    private CallBack callBack;
    private PinView secondPinView;
    private PinView pinView;

    private LinearLayout layoutBottomsheetPaymentPass;
    private TextView tvTextpinpass;
    private View viewLine;
    private TextView forgotPass;


    public BottomSheetPassPayment(@NonNull Context context, int theme, CallBack callBack) {
        super(context, theme);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.bottomsheet_payment_pass);
        pinView = ((PinView) findViewById(R.id.secondPinView));
        close = (ImageView) findViewById(R.id.close);
        reset = (TextView) findViewById(R.id.reset);
        pinView.setAnimationEnable(true);

        layoutBottomsheetPaymentPass = (LinearLayout) findViewById(R.id.layoutBottomsheet_payment_pass);
        close = (ImageView) findViewById(R.id.close);
        tvTextpinpass = (TextView) findViewById(R.id.tvTextpinpass);
        reset = (TextView) findViewById(R.id.reset);
        viewLine = (View) findViewById(R.id.viewLine);
        secondPinView = (PinView) findViewById(R.id.secondPinView);
        forgotPass = (TextView) findViewById(R.id.forgot_pass);

        close.setOnClickListener(v -> {
            dismiss();
        });

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 6) {
                    callBack.onClickPayment(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //        thay đổi Theme
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferencesTheme = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, Context.MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);
        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

    }

    public interface CallBack {
        void onCLickCLose();

        void onClickPayment(String s);
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            layoutBottomsheetPaymentPass.setBackgroundColor(getContext().getResources().getColor(R.color.dark_212332));
            close.setImageResource(R.drawable.ic_exitwhite_editprofile);
            tvTextpinpass.setTextColor(Color.WHITE);
            viewLine.setBackgroundColor(Color.WHITE);
            pinView.setTextColor(Color.WHITE);
            forgotPass.setTextColor(Color.WHITE);
        } else {
            layoutBottomsheetPaymentPass.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            close.setImageResource(R.drawable.ic_exit_edit_profile);
            tvTextpinpass.setTextColor(Color.BLACK);
            viewLine.setBackgroundColor(Color.BLACK);
            pinView.setTextColor(Color.BLACK);
            forgotPass.setTextColor(Color.BLACK);
        }
    }

}
