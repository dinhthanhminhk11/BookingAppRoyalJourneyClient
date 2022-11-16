package com.example.bookingapproyaljourney.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityBillOderBinding;
import com.example.bookingapproyaljourney.databinding.ActivityStatusBillBinding;
import com.example.bookingapproyaljourney.response.order.OrderListResponse;

public class StatusBillActivity extends AppCompatActivity {

    private ActivityStatusBillBinding binding;
    private TextView tvNameHouse, tvDateNhanPhong, tvDateTraPhong, tvTimeNhanPhong, tvTimeTraPhong,
            tvNight, tvPerson, tvBed, tvPartial, tvNameUser, tvEmailUser, tvSDTUser, tvPriceHouse, tvPriceService,
            tvPaymentMethod, tvStatusPayment;
    private OrderListResponse orderListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderListResponse = (OrderListResponse) getIntent().getSerializableExtra("OrderListResponse");
        IntitData();
    }

    private void IntitData()
    {
        binding.nameHouseStatusBill.setText(orderListResponse.getNamePro());
        binding.dateNhanPhongStatusBill.setText(orderListResponse.getStartDate());
        binding.dateTraPhongStatusBill.setText(orderListResponse.getEndDate());
        binding.timeNhanPhongStatusBill.setText(orderListResponse.getTime());
        binding.timeTraPhongStatusBill.setText(orderListResponse.getTime());
        binding.nightStatusbill.setText(orderListResponse.getDay()+"");
        binding.personStatusBill.setText(orderListResponse.getPerson()+"");
        binding.tvNameUserStatusBill.setText(orderListResponse.getNameUser());
        binding.tvUserNumberPhoneStatusBill.setText(orderListResponse.getPhone());
    }
}