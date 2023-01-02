package com.example.bookingapproyaljourney.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ActivityAddMoneyBinding;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPayment;

import java.text.DecimalFormat;
import java.text.ParseException;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddMoneyBinding binding;
    private String gia;
    private BottomSheetPayment bottomSheetPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initView();

        binding.number1.setText("100.000");
        binding.number2.setText("200.000");
        binding.number3.setText("500.000");

        binding.editInputMoney.addTextChangedListener(new NumberTextWatcher(binding.editInputMoney));

        final View activityRootView = findViewById(R.id.contentBackground);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if ((heightDiff > dpToPx(AddMoneyActivity.this, 200)) && (binding.editInputMoney.getText().toString().length() < 7)) { // if more than 200 dp, it's probably a keyboard...
                    binding.contentLayoutMoney.setVisibility(View.VISIBLE);
                } else {
                    binding.contentLayoutMoney.setVisibility(View.GONE);
                }
            }
        });

      checkBtnClick();
    }

    private void checkBtnClick() {
        if (binding.editInputMoney.getText().toString().isEmpty() || !binding.textPayment.getText().toString().trim().equals("Thẻ VISA (VISA card)")){
            binding.btnPay.setAlpha(0.4f);
            binding.btnPay.setEnabled(false);
        }else {
            binding.btnPay.setAlpha(1);
            binding.btnPay.setEnabled(true);
        }
    }

    private void initView() {
        binding.btnPay.setOnClickListener(this);
        binding.number1.setOnClickListener(this);
        binding.number2.setOnClickListener(this);
        binding.number3.setOnClickListener(this);
        binding.contentPayment.setOnClickListener(this);
    }

    private void initToolbar() {
        binding.toolBar.setTitle(R.string.add_money_to_card);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPay:
                String textString = binding.editInputMoney.getText().toString().replace(".", "");
                Toast.makeText(this, "toast " + textString, Toast.LENGTH_SHORT).show();
                break;

            case R.id.number1:
                binding.editInputMoney.setText(binding.number1.getText().toString());
                break;
            case R.id.number2:
                binding.editInputMoney.setText(binding.number2.getText().toString());
                break;
            case R.id.number3:
                binding.editInputMoney.setText(binding.number3.getText().toString());
                break;

            case R.id.contentPayment:
                showDialog();
                break;
        }
    }

    class NumberTextWatcher implements TextWatcher {
        private DecimalFormat df;
        private DecimalFormat dfnd;
        private boolean hasFractionalPart;
        private EditText et;

        public NumberTextWatcher(EditText et) {
            df = new DecimalFormat("#,###.##");
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat("#,###");
            this.et = et;
            hasFractionalPart = false;
        }

        @SuppressWarnings("unused")
        private static final String TAG = "NumberTextWatcher";

        public void afterTextChanged(Editable s) {
            et.removeTextChangedListener(this);
            if (binding.editInputMoney.getText().toString().isEmpty() || binding.editInputMoney.getText().toString().length() == 0 || binding.editInputMoney.getText().toString().trim().equals("0") || !binding.textPayment.getText().toString().trim().equals("Thẻ VISA (VISA card)")) {
                binding.sumPrice.setText("0 ₫");
                binding.priceAll.setText("0 ₫");

                binding.number1.setText(dfnd.format(100000));
                binding.number2.setText(dfnd.format(200000));
                binding.number3.setText(dfnd.format(500000));
                binding.btnPay.setAlpha(0.4f);
                binding.btnPay.setEnabled(false);
            } else {
                binding.btnPay.setAlpha(1);
                binding.btnPay.setEnabled(true);

            }

            if (binding.editInputMoney.getText().toString().length() == 1 && !binding.editInputMoney.getText().toString().trim().equals("0")) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 10000;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 100000;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 1000000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 2) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 1000;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 10000;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 100000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 3) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 100;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 1000;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().trim()) * 10000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 4) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().replace(".", "").trim()) * 10;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().replace(".", "").trim()) * 100;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().replace(".", "").trim()) * 1000;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            } else if (binding.editInputMoney.getText().toString().length() == 6) {
                int sum1 = Integer.parseInt(binding.editInputMoney.getText().toString().replace(".", "").trim()) * 1;
                int sum2 = Integer.parseInt(binding.editInputMoney.getText().toString().replace(".", "").trim()) * 10;
                int sum3 = Integer.parseInt(binding.editInputMoney.getText().toString().replace(".", "").trim()) * 100;
                binding.number1.setText(String.valueOf(dfnd.format(sum1)));
                binding.number2.setText(String.valueOf(dfnd.format(sum2)));
                binding.number3.setText(String.valueOf(dfnd.format(sum3)));
            }
            try {
                int inilen, endlen;
                inilen = et.getText().length();
                String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                Number n = df.parse(v);
                int cp = et.getSelectionStart();
                if (hasFractionalPart) {
                    et.setText(df.format(n));

                    binding.sumPrice.setText(df.format(n) + " ₫");
                    binding.priceAll.setText(df.format(n) + " ₫");
                } else {
                    et.setText(dfnd.format(n));

                    binding.sumPrice.setText(dfnd.format(n) + " ₫");
                    binding.priceAll.setText(dfnd.format(n) + " ₫");
                }
                endlen = et.getText().length();
                int sel = (cp + (endlen - inilen));
                if (sel > 0 && sel <= et.getText().length()) {
                    et.setSelection(sel);
                } else {
                    et.setSelection(et.getText().length() - 1);

                }
            } catch (NumberFormatException nfe) {
            } catch (ParseException e) {
            }

            et.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()))) {
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }
        }
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    private void showDialog() {
        bottomSheetPayment = new BottomSheetPayment(AddMoneyActivity.this, R.style.MaterialDialogSheet, new BottomSheetPayment.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetPayment.dismiss();
            }

            @Override
            public void onClickPayment() {
                bottomSheetPayment.dismiss();
                binding.textPayment.setText("Thẻ VISA (VISA card)");
                checkBtnClick();
            }
        });
        bottomSheetPayment.show();
        bottomSheetPayment.setCanceledOnTouchOutside(false);
    }
}