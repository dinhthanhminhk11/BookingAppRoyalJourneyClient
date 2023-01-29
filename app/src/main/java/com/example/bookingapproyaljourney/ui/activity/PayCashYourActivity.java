package com.example.bookingapproyaljourney.ui.activity;

import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
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

public class PayCashYourActivity extends BaseActivity {

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

//        thay đổi Theme
        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme2 = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme2 == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
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
        binding.toolBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.toolBar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24);
            binding.toolBar.setTitleTextColor(Color.WHITE);
            binding.layoutPayCashYour.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));

            binding.contentCancellationPolicy.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.contentAddMoney.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.contentBiometric.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.contentHistoryPayment.setBackgroundResource(R.drawable.background_setting_item_dark);

            binding.text1.setTextColor(Color.WHITE);
            binding.contentPayOfflineLine.setBackgroundColor(Color.WHITE);
            binding.sourcePayment.setTextColor(Color.WHITE);
            binding.textCancel.setTextColor(Color.WHITE);

            binding.iconChangePassLast2.setColorFilter(getResources().getColor(R.color.white));
            binding.textAddMoney.setTextColor(Color.WHITE);
            binding.contentTextAddMoney.setTextColor(Color.WHITE);

            binding.textAddMoney2.setTextColor(Color.WHITE);
            binding.contentTextAddMoney2.setTextColor(Color.WHITE);

            binding.text2.setTextColor(Color.WHITE);
            binding.text4.setTextColor(Color.WHITE);
            binding.contentPayOfflineLine2.setBackgroundColor(Color.WHITE);

        } else {
            binding.toolBar.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
            binding.toolBar.setTitleTextColor(Color.BLACK);
            binding.layoutPayCashYour.setBackgroundColor(this.getResources().getColor(R.color.color_EBEBEB));

            binding.contentCancellationPolicy.setBackgroundColor(Color.WHITE);
            binding.contentAddMoney.setBackgroundColor(Color.WHITE);
            binding.contentBiometric.setBackgroundColor(Color.WHITE);
            binding.contentHistoryPayment.setBackgroundColor(Color.WHITE);

            binding.text1.setTextColor(Color.BLACK);
            binding.contentPayOfflineLine.setBackgroundColor(Color.BLACK);
            binding.sourcePayment.setTextColor(Color.BLACK);
            binding.textCancel.setTextColor(Color.BLACK);
            binding.iconChangepass.setColorFilter(getResources().getColor(R.color.black));

            binding.iconChangepass2.setColorFilter(getResources().getColor(R.color.black));
            binding.iconChangePassLast2.setColorFilter(getResources().getColor(R.color.black));
            binding.textAddMoney.setTextColor(Color.BLACK);
            binding.contentTextAddMoney.setTextColor(Color.BLACK);

            binding.iconChangepass3.setColorFilter(getResources().getColor(R.color.black));
            binding.textAddMoney2.setTextColor(Color.BLACK);
            binding.contentTextAddMoney2.setTextColor(Color.BLACK);

            binding.text2.setTextColor(Color.BLACK);
            binding.text4.setTextColor(Color.BLACK);
            binding.contentPayOfflineLine2.setBackgroundColor(Color.BLACK);
        }
    }


}