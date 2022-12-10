package com.example.bookingapproyaljourney.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityCancelBookingBinding;
import com.example.bookingapproyaljourney.response.order.OrderRequest;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;
import com.example.bookingapproyaljourney.view_model.CancelBookingViewModel;
import com.example.librarytoastcustom.CookieBar;

import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CancelBookingActivity extends AppCompatActivity {

    private ActivityCancelBookingBinding binding;
    private String dateCancel;
    private boolean checkBanking;
    private boolean checkSeem;
    private String imageHost;
    private CancelBookingViewModel cancelBookingViewModel;
    private String idOrder;
    private ImageView close;
    private TextView text;
    private TextView btnCancel;
    private Button login;
    private String currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @SuppressLint("StringFormatInvalid")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCancelBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dateCancel = getIntent().getStringExtra("dateCancel");
        checkBanking = Boolean.parseBoolean(getIntent().getStringExtra("checkIsbacking"));
        checkSeem = Boolean.parseBoolean(getIntent().getStringExtra("checkSeem"));
        imageHost = getIntent().getStringExtra("imageHost");
        idOrder = getIntent().getStringExtra("idOrder");

        Log.e("MinhCheck", String.valueOf(checkBanking));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.soap)
                .error(R.drawable.soap);
        Glide.with(this).load(imageHost).apply(options).into(binding.imgBoss);

        if (checkBanking) {
            binding.textView8.setText(this.getString(R.string.Ly_do_huy));
        } else {
            binding.textView8.setText(String.format(getResources().getString(R.string.Billoder_date_cancel), dateCancel, dateCancel));
        }

        cancelBookingViewModel = new ViewModelProvider(this).get(CancelBookingViewModel.class);

        binding.imgBackFB.setOnClickListener(v -> {
            onBackPressed();
        });

        //ngày hôm nay
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        try {
            if ((sdf.parse(dateCancel).before((sdf.parse(currentDate)))) && !checkBanking) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.Notify))
                        .setMessage(this.getString(R.string.expired))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000)
                        .setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (currentDate.equals(dateCancel) && !checkBanking) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.Notify))
                        .setMessage(this.getString(R.string.dealine_date))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000)
                        .setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            }
        } catch (Exception e) {

        }


        final Dialog dialogLogOut = new Dialog(this);
        dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
        Window window2 = dialogLogOut.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogLogOut.getWindow() != null) {
            dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        close = (ImageView) dialogLogOut.findViewById(R.id.close);
        text = (TextView) dialogLogOut.findViewById(R.id.text);
        btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
        login = (Button) dialogLogOut.findViewById(R.id.login);
        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        text.setText(this.getString(R.string.cancel_room));
        login.setText(this.getString(R.string.Confirm));


        binding.btnSubmit.setOnClickListener(v -> {
            dialogLogOut.show();
        });

        Log.e("MInhCheckSeem", String.valueOf(checkSeem));

        login.setOnClickListener(v -> {


            dialogLogOut.cancel();

            cancelBookingViewModel.updateOrderByUser(new OrderRequest(
                    idOrder,
                    "Khách huỷ",
                    binding.edComment.getText().toString(),
                    currentDate
            ));
        });

        close.setOnClickListener(v -> {
            dialogLogOut.cancel();
        });

        btnCancel.setOnClickListener(v -> {
            dialogLogOut.cancel();
        });

        cancelBookingViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        cancelBookingViewModel.getOrderStatusResponseMutableLiveData().observe(this, new Observer<OrderStatusResponse>() {
            @Override
            public void onChanged(OrderStatusResponse orderStatusResponse) {
                if (orderStatusResponse.isMessege()) {
                    Intent intent = new Intent(CancelBookingActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("CheckSuccess", "CancelBookingActivity");
                    startActivity(intent);
                } else {

                }
            }
        });

    }
}