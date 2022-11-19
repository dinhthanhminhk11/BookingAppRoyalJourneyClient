package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityStatusBillBinding;
import com.example.bookingapproyaljourney.model.order.OrderBill;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.order.OrderListResponse;
import com.example.bookingapproyaljourney.view_model.StatusOrderViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StatusBillActivity extends AppCompatActivity {

    private ActivityStatusBillBinding binding;
    private OrderListResponse orderListResponse;
    private String idOrder;
    private StatusOrderViewModel statusOrderViewModel;
    private NumberFormat fm = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolBar.setTitle("Chi tiết chuyến đi của bạn");
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        idOrder = getIntent().getStringExtra(AppConstant.ID_ORDER);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        statusOrderViewModel = new ViewModelProvider(this).get(StatusOrderViewModel.class);

        initData();

        statusOrderViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        statusOrderViewModel.getOrderResponseMutableLiveData().observe(this, new Observer<OrderBill>() {
            @Override
            public void onChanged(OrderBill orderResponse) {
                statusOrderViewModel.getDetailHouseById(orderResponse.getIdPro());
                binding.startDate.setText(orderResponse.getStartDate());
                binding.endDate.setText(orderResponse.getEndDate());
                binding.person.setText(orderResponse.getPerson() + " khách");
                binding.phone.setText(orderResponse.getPhone() + "");
                binding.payDay.setText(orderResponse.getPayDay() + "");
                binding.priceAll.setText(fm.format(Integer.parseInt(orderResponse.getPrice())) + " Vnd");
                if (orderResponse.isBanking()) {
                    binding.textPayment.setText("Thanh toán bằng Thẻ VISA (VISA card) (Đã thanh toán)");
                    binding.imageMatercard.setVisibility(View.GONE);
                    binding.imagePaypal.setVisibility(View.GONE);
                    binding.imageGooglePlay.setVisibility(View.GONE);
                } else {
                    binding.textPayment.setText("Thanh toán sau khi hoàn tất thủ tục trả phòng");
                    binding.imageMatercard.setVisibility(View.GONE);
                    binding.imagePaypal.setVisibility(View.GONE);
                    binding.imageGooglePlay.setVisibility(View.GONE);
                    binding.imageVISA.setVisibility(View.GONE);
                }
            }
        });

        statusOrderViewModel.getHouseDetailResponseMutableLiveData().observe(StatusBillActivity.this, new Observer<HouseDetailResponse>() {
            @Override
            public void onChanged(HouseDetailResponse houseDetailResponse) {
                binding.nameHouse.setText(houseDetailResponse.getName());
                binding.priceAndCount.setText(fm.format(houseDetailResponse.getPrice()) + " x " + binding.payDay.getText().toString() + " đêm");
                binding.tvTimeNhanPhong.setText(houseDetailResponse.getOpening());
                binding.tvTimeTra.setText(houseDetailResponse.getEnding());
            }
        });

        binding.btnPay.setOnClickListener(v -> {
            startActivity(new Intent(StatusBillActivity.this, CancelBookingActivity.class));
        });

    }

    private void initData() {
        statusOrderViewModel.getOrderById(idOrder);
    }

}