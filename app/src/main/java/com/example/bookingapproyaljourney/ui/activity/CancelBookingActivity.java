package com.example.bookingapproyaljourney.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.bookingapproyaljourney.databinding.ActivityCancelBookingBinding;
import com.example.bookingapproyaljourney.response.order.OrderRequest;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.view_model.CancelBookingViewModel;

import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CancelBookingActivity extends AppCompatActivity {

    private ActivityCancelBookingBinding binding;
    private String dateCancel;
    private boolean checkBanking;
    private String imageHost;
    private CancelBookingViewModel cancelBookingViewModel;
    private String idOrder;
    private ImageView close;
    private TextView text;
    private TextView btnCancel;
    private Button login;
    private String currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCancelBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dateCancel = getIntent().getStringExtra("dateCancel");
        checkBanking = Boolean.parseBoolean(getIntent().getStringExtra("checkIsbacking"));
        imageHost = getIntent().getStringExtra("imageHost");
        idOrder = getIntent().getStringExtra("idOrder");

        Log.e("MinhCheck", String.valueOf(checkBanking));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);
        Glide.with(this).load(imageHost).apply(options).into(binding.imgBoss);

        if (checkBanking) {
            binding.textView8.setText("Hãy cho chúng tôi biết lí do bạn huỷ, ý kiển của bạn sẽ giúp chúng tôi hoàn thiện hơn");
        } else {
            binding.textView8.setText("Hủy trước ngày " + dateCancel + " để được hoàn lại tiền , nếu bạn hủy sau ngày " + dateCancel + " sẽ không hoàn lại tiền");
        }

        cancelBookingViewModel = new ViewModelProvider(this).get(CancelBookingViewModel.class);

        binding.imgBackFB.setOnClickListener(v -> {
            onBackPressed();
        });

        //ngày hôm nay
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        try {
            if ((sdf.parse(dateCancel).before((sdf.parse(currentDate)))) && !checkBanking) {
                ToastCheck toastCheck = new ToastCheck(CancelBookingActivity.this, R.style.StyleToast, "Thông báo", "Đã quá hạn huỷ bạn sẽ không nhận được khoản tiền nào trong ngày hôm nay", R.drawable.ic_warning_icon_check);
            } else if (currentDate.equals(dateCancel) && !checkBanking) {
                ToastCheck toastCheck = new ToastCheck(CancelBookingActivity.this, R.style.StyleToast, "Thông báo", "Hôm nay là hạn cuối để bạn huỷ phòng", R.drawable.ic_warning_icon_check);
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

        text.setText("Bạn có chắc muốn huỷ phòng");
        login.setText("Xác nhận");


        binding.btnSubmit.setOnClickListener(v -> {
            dialogLogOut.show();
        });

        login.setOnClickListener(v -> {
            dialogLogOut.cancel();
            cancelBookingViewModel.updateOrderByUser(new OrderRequest(
                    idOrder,
                    "Khách huỷ",
                    binding.edComment.getText().toString(),
                    currentDate
            ));
            dialogLogOut.cancel();
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