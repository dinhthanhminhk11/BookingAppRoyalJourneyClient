package com.example.bookingapproyaljourney.ui.activity;

import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityPayCashYourBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.user.CashFolwResponse;
import com.example.bookingapproyaljourney.ui.adapter.CashFolwAdapter;
import com.example.bookingapproyaljourney.view_model.CashPayViewModel;
import com.example.librarytoastcustom.CookieBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class PayCashYourActivity extends AppCompatActivity {

    private ActivityPayCashYourBinding binding;
    private CashPayViewModel cashPayViewModel;
    private NumberFormat fm = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayCashYourBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_PASS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_PASS, 2655);
        initToolbar();

        if (theme == AppConstant.POS_VANTAY) {
            binding.switchComparTheme.setChecked(true);
        } else {
            binding.switchComparTheme.setChecked(false);
        }

        binding.contentAddMoney.setOnClickListener(v -> {
            startActivity(new Intent(this, AddMoneyActivity.class));
        });

        cashPayViewModel = new ViewModelProvider(this).get(CashPayViewModel.class);

        cashPayViewModel.getGetPrice().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.sourcePayment.setText(fm.format(Integer.parseInt(s)) + " đ");
            }
        });

        cashPayViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        cashPayViewModel.getListMutableLiveDataListCashPayFolw().observe(this, new Observer<List<CashFolwResponse>>() {
            @Override
            public void onChanged(List<CashFolwResponse> list) {
                if (list.size() == 0) {
                    binding.text4.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                } else {
                    binding.text4.setVisibility(View.GONE);
                    CashFolwAdapter cashFolwAdapter = new CashFolwAdapter(list);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(PayCashYourActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.recyclerView.setAdapter(cashFolwAdapter);
                }
            }
        });

        String check = getIntent().getStringExtra(CheckSuccess);
        if (!(check == null)) {
            if (check.equals(AppConstant.CHECK_SUCCESS_ADD_MONEY)) {
                CookieBar.build(this).setTitle(R.string.Notify).setMessage(R.string.textSuccessAddMoney).setIcon(R.drawable.ic_complete_order).setTitleColor(R.color.black).setMessageColor(R.color.black).setDuration(5000).setBackgroundRes(R.drawable.background_toast).setCookiePosition(CookieBar.BOTTOM).show();
            }
        }

        binding.switchComparTheme.setOnClickListener(v -> {
            if (binding.switchComparTheme.isChecked()) {
                editor.putInt(AppConstant.SHAREDPREFERENCES_PASS, AppConstant.POS_VANTAY);
                editor.commit();
            } else {
                editor.putInt(AppConstant.SHAREDPREFERENCES_PASS, AppConstant.POS_PASS);
                editor.commit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String id = UserClient.getInstance().getId();
        cashPayViewModel.getPricePayCashByUser(id);
        cashPayViewModel.getListPayCash(id);
    }

    private void initToolbar() {
        binding.toolBar.setTitle(R.string.yourpayment);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }
}