package com.example.bookingapproyaljourney.ui.activity;

import android.graphics.Paint;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityBillOderBinding;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetEditPerson;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPayment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BillOderActivity extends AppCompatActivity implements BottomSheetEditPerson.CallBack {

    private ActivityBillOderBinding binding;
    private BottomSheetPayment bottomSheetPayment;
    private BottomSheetEditPerson bottomSheetEditPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillOderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBar.setTitle("Xác nhận và thanh toán");
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.editPerson.setPaintFlags(binding.editPerson.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        bottomSheetEditPerson = new BottomSheetEditPerson(BillOderActivity.this, R.style.MaterialDialogSheet, this);

        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addPayment.setOnClickListener(v -> {
            showDialog();
        });

        binding.addPhone.setOnClickListener(v -> {
            binding.edPhone.setVisibility(View.VISIBLE);
            binding.btnComfirmPhone.setVisibility(View.VISIBLE);
        });

        binding.btnComfirmPhone.setOnClickListener(v -> {
            binding.phone.setText(binding.edPhone.getText().toString().trim());
            binding.edPhone.setVisibility(View.GONE);
            binding.btnComfirmPhone.setVisibility(View.GONE);
        });

        if (binding.payOnline.isChecked()) {
            binding.contentPayment.setVisibility(View.VISIBLE);
        } else {
            binding.contentPayment.setVisibility(View.GONE);
        }

        binding.payOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.contentPayment.setVisibility(View.VISIBLE);
                    binding.payOffline.setChecked(false);
                } else {

                }
            }
        });

        binding.payOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.contentPayment.setVisibility(View.GONE);
                    binding.payOnline.setChecked(false);
                } else {

                }
            }
        });

        binding.contentEditPerson.setOnClickListener(v -> {
            showDiaLogEditPerson();
        });


        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker);
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder
                .setTitleText("Chọn ngày")
                .setPositiveButtonText("Lưu")
                .setNegativeButtonText("không")
                .build();


        binding.contentPayDayNight.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            materialDatePicker.getLifecycle().addObserver(new DefaultLifecycleObserver() {
                @Override
                public void onCreate(@NonNull LifecycleOwner owner) {
                    Log.e("Minh", "onCreate");
                }

                @Override
                public void onStart(@NonNull LifecycleOwner owner) {
                    //in onStart of DialogFragment the View has been created so you can access the materialDatePicker.requireView()
                    View root = materialDatePicker.requireView();
                    Log.e("Minh", "onStart");
                }

                @Override
                public void onResume(@NonNull LifecycleOwner owner) {
                    Log.e("Minh", "onResume");
                }

                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    //remove Lifecycle Observer
                    materialDatePicker.getLifecycle().removeObserver(this);
                    Log.e("Minh", "onDestroy");
                }
            });
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                @Override
                public void onPositiveButtonClick(Pair<Long, Long> selection) {
                    Long startDate = selection.first;
                    Long endDate = selection.second;
                    Log.e("Minh1", "startDate " + startDate);
                    Log.e("Minh2", "onDestroy " + endDate);

                    long msDiff = endDate - startDate;
                    long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

                    binding.payDay.setText(daysDiff+"");

                    String startDateString = DateFormat.format("EEE, dd-MM", new Date(startDate)).toString();
                    String endDateString = DateFormat.format("EEE, dd-MM", new Date(endDate)).toString();

                    binding.startDate.setText(startDateString);
                    binding.endDate.setText(endDateString);
                    //Do something...
                }
            });
        });
    }

    private void showDialog() {
        bottomSheetPayment = new BottomSheetPayment(BillOderActivity.this, R.style.MaterialDialogSheet, new BottomSheetPayment.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetPayment.dismiss();
            }
        });
        bottomSheetPayment.show();
        bottomSheetPayment.setCanceledOnTouchOutside(false);
    }

    private void showDiaLogEditPerson() {
        bottomSheetEditPerson.show();
        bottomSheetEditPerson.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onCLickCLose() {
        bottomSheetEditPerson.dismiss();
    }

    @Override
    public void onCLickSum(int sum) {
        binding.person.setText(sum + " khách");
    }
}