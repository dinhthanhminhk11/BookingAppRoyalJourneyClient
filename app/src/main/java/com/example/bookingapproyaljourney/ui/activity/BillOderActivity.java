package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityBillOderBinding;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetConvenient;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPayment;

public class BillOderActivity extends AppCompatActivity {

    private ActivityBillOderBinding binding;
    private BottomSheetPayment bottomSheetPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillOderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBar.setTitle("Xác nhận và thanh toán");
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addPayment.setOnClickListener(v->{
            showDialog();
        });
    }

    private void showDialog() {
        bottomSheetPayment = new BottomSheetPayment(BillOderActivity.this, R.style.MaterialDialogSheet, new BottomSheetPayment.CallBack() {
            @Override
            public void onCLickCLose() {

            }
        });
        bottomSheetPayment.show();
        bottomSheetPayment.setCanceledOnTouchOutside(false);
    }
}