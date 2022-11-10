package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityOtppasswordBinding;

public class OTPPasswordActivity extends AppCompatActivity {

    private ActivityOtppasswordBinding binding;
    private ImageView close;
    private TextView btnCancel;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtppasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.sendAgain.setPaintFlags(binding.sendAgain.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.close.setOnClickListener(v -> {
            final Dialog dialogLogOut = new Dialog(this);
            dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
            Window window2 = dialogLogOut.getWindow();
            window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialogLogOut != null && dialogLogOut.getWindow() != null) {
                dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            close = (ImageView) dialogLogOut.findViewById(R.id.close);
            btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
            btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            logOut = (Button) dialogLogOut.findViewById(R.id.login);
            logOut.setText("Thoát");

            close.setOnClickListener(v1 -> {
                dialogLogOut.dismiss();
            });
            btnCancel.setOnClickListener(v1 -> {
                dialogLogOut.dismiss();
            });
            logOut.setOnClickListener(v2 -> {
                onBackPressed();
            });
            dialogLogOut.show();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(OTPPasswordActivity.this, NewPasswordActivity.class));
        });
    }
}