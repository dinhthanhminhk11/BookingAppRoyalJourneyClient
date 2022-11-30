package com.example.bookingapproyaljourney.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityBillOderBinding;
import com.example.bookingapproyaljourney.model.order.OrderCreate;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.order.OrderResponse;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetCancellationPolicy;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetEditPerson;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPayment;
import com.example.bookingapproyaljourney.view_model.DetailProductViewModel;
import com.example.bookingapproyaljourney.view_model.OrderViewModel;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BillOderActivity extends AppCompatActivity implements BottomSheetEditPerson.CallBack {

    private ActivityBillOderBinding binding;
    private BottomSheetPayment bottomSheetPayment;
    private BottomSheetEditPerson bottomSheetEditPerson;

    private int TYPE_PAYMENT = 1;
    private OrderViewModel orderViewModel;
    private DetailProductViewModel detailProductViewModel;
    private String idHouse = "";
    private NumberFormat fm = new DecimalFormat("#,###");
    @SuppressLint("NewApi")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private HouseDetailResponse houseDetailResponse;
    private long sumAll;
    private long sumAllPercent;
    private PaymentSheet paymentSheet;
    private PaymentSheet paymentSheet2;
    private BottomSheetCancellationPolicy bottomSheetCancellationPolicy;
    private String customerID;
    private String EpericalKey;
    private String ClientSecret;
    private int payday;
    private final int random = new Random().nextInt(100000) + 2000000;
    private String startDateStringPrivate;
    private String endDateStringPrivate;
    private int personLimitPrivate;
    private String phonePrivate;

    private String checkStartDate;
    private String checkEndDate;
    private String checkStartDateResponse;
    private String checkEndDateResponse;
    private long daysDiff = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillOderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBar.setTitle("Xác nhận và thanh toán");
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.editPerson.setPaintFlags(binding.editPerson.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        bottomSheetEditPerson = new BottomSheetEditPerson(BillOderActivity.this, R.style.MaterialDialogSheet, this);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);

        idHouse = getIntent().getStringExtra(AppConstant.HOUSE_EXTRA);

        Log.e("IdUser", UserClient.getInstance().getId());

        PaymentConfiguration.init(BillOderActivity.this, AppConstant.PUBLISHABLE_KEY);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        paymentSheet2 = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult2(paymentSheetResult);
        });

        initData(idHouse);

        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.addPayment.setOnClickListener(v -> {
            if (binding.startDate.getText().toString().equals("")) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.dialogstartdate))
                        .setMessage(this.getString(R.string.dialogcontentnomal))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000).setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                return;
            } else {
                showDialog();
            }
        });

        binding.addPhone.setOnClickListener(v -> {

            binding.edPhone.setVisibility(View.VISIBLE);
            binding.btnComfirmPhone.setVisibility(View.VISIBLE);

        });

        binding.btnComfirmPhone.setOnClickListener(v -> {
            String phone = binding.edPhone.getText().toString();
            phonePrivate = binding.edPhone.getText().toString();

//            binding.edPhone.setVisibility(View.GONE);
//            binding.btnComfirmPhone.setVisibility(View.GONE);
            validateinfo(phone);
        });

        if (binding.payOnline.isChecked() || binding.payOfflinePercent.isChecked()) {
            binding.contentPayment.setVisibility(View.VISIBLE);
        } else {
            binding.contentPayment.setVisibility(View.GONE);
        }

        binding.contentPayOnline.setOnClickListener(v -> {
            TYPE_PAYMENT = 1;
            binding.contentPayment.setVisibility(View.VISIBLE);
            binding.payOffline.setChecked(false);
            binding.payOfflinePercent.setChecked(false);
            binding.payOnline.setChecked(true);
            binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
        });

        binding.contentPayOffline.setOnClickListener(v -> {
            if (UserClient.getInstance().getCountBooking() < 4) {
                CookieBar.build(BillOderActivity.this)
                        .setTitle(this.getString(R.string.BillOder_add_uy_tin))
                        .setMessage(this.getString(R.string.BillOder_add_uy_tin_5))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000).setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                return;
            }
            TYPE_PAYMENT = 2;
            binding.contentPayment.setVisibility(View.GONE);
            binding.payOnline.setChecked(false);
            binding.payOfflinePercent.setChecked(false);
            binding.payOffline.setChecked(true);
            binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
        });

        binding.contentPayOnlinePercent.setOnClickListener(v -> {
            TYPE_PAYMENT = 3;
            binding.contentPayment.setVisibility(View.VISIBLE);
            binding.payOnline.setChecked(false);
            binding.payOfflinePercent.setChecked(true);
            binding.payOffline.setChecked(false);
            sumAllPercent = (long) (sumAll * 0.32);
            binding.priceAll.setText("$" + fm.format(sumAllPercent));
        });

        binding.payOfflinePercent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    TYPE_PAYMENT = 3;
                    binding.contentPayment.setVisibility(View.VISIBLE);
                    binding.payOnline.setChecked(false);
                    binding.payOffline.setChecked(false);
                    sumAllPercent = (long) (sumAll * 0.32);
                    binding.priceAll.setText("$" + fm.format(sumAllPercent));

                } else {

                }
            }
        });

        binding.payOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TYPE_PAYMENT = 1;
                    binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                    binding.contentPayment.setVisibility(View.VISIBLE);
                    binding.payOffline.setChecked(false);
                    binding.payOfflinePercent.setChecked(false);
                } else {

                }
            }
        });

        binding.payOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (UserClient.getInstance().getCountBooking() < 4) {
                        CookieBar.build(BillOderActivity.this)
                                .setTitle(BillOderActivity.this.getString(R.string.BillOder_add_uy_tin))
                                .setMessage(BillOderActivity.this.getString(R.string.BillOder_add_uy_tin_5))
                                .setIcon(R.drawable.ic_warning_icon_check)
                                .setTitleColor(R.color.black)
                                .setMessageColor(R.color.black)
                                .setDuration(3000).setSwipeToDismiss(false)
                                .setBackgroundRes(R.drawable.background_toast)
                                .setCookiePosition(CookieBar.BOTTOM)
                                .show();
                        binding.payOffline.setChecked(false);
                        return;
                    }
                    TYPE_PAYMENT = 2;
                    binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                    binding.contentPayment.setVisibility(View.GONE);
                    binding.payOnline.setChecked(false);
                    binding.payOfflinePercent.setChecked(false);
                    binding.textCancel.setText(BillOderActivity.this.getString(R.string.Billoder_date_cancel) + houseDetailResponse.getCancellatioDate() + BillOderActivity.this.getString(R.string.Billoder_date_cancel_1));
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
                }

                @Override
                public void onStart(@NonNull LifecycleOwner owner) {
                    View root = materialDatePicker.requireView();
                }

                @Override
                public void onResume(@NonNull LifecycleOwner owner) {

                }

                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    materialDatePicker.getLifecycle().removeObserver(this);
                }
            });
            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                @SuppressLint("NewApi")
                @Override
                public void onPositiveButtonClick(Pair<Long, Long> selection) {
                    Long startDate = selection.first;
                    Long endDate = selection.second;

                    String startDateString = DateFormat.format("EEE, dd-MM", new Date(startDate)).toString();
                    String endDateString = DateFormat.format("EEE, dd-MM", new Date(endDate)).toString();

                    checkStartDate = DateFormat.format("dd/MM/yyyy", new Date(startDate)).toString();
                    checkEndDate = DateFormat.format("dd/MM/yyyy", new Date(endDate)).toString();

                    try {
                        if (sdf.parse(checkStartDate).before((sdf.parse(checkStartDateResponse)))) {
                            CookieBar.build(BillOderActivity.this)
                                    .setTitle(BillOderActivity.this.getString(R.string.BillOder_host_received_date) + checkStartDateResponse)
                                    .setMessage(BillOderActivity.this.getString(R.string.dialogcontentnomal))
                                    .setIcon(R.drawable.ic_warning_icon_check)
                                    .setTitleColor(R.color.black)
                                    .setMessageColor(R.color.black)
                                    .setDuration(5000)
                                    .setSwipeToDismiss(false)
                                    .setBackgroundRes(R.drawable.background_toast)
                                    .setCookiePosition(CookieBar.BOTTOM)
                                    .show();
                            return;
                        } else if (sdf.parse(checkEndDate).after((sdf.parse(checkEndDateResponse)))) {
                            CookieBar.build(BillOderActivity.this)
                                    .setTitle(BillOderActivity.this.getString(R.string.BillOder_add_pay_day) + checkEndDateResponse)
                                    .setMessage(BillOderActivity.this.getString(R.string.dialogcontentnomal))
                                    .setIcon(R.drawable.ic_warning_icon_check)
                                    .setTitleColor(R.color.black)
                                    .setMessageColor(R.color.black)
                                    .setDuration(5000).setSwipeToDismiss(false)
                                    .setBackgroundRes(R.drawable.background_toast)
                                    .setCookiePosition(CookieBar.BOTTOM)
                                    .show();
                            return;
                        } else {

                            long msDiff = endDate - startDate;
                            daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                            payday = Integer.parseInt(String.valueOf(daysDiff));
                            binding.payDay.setText(daysDiff + "");

                            startDateStringPrivate = startDateString;
                            endDateStringPrivate = endDateString;

                            binding.startDate.setText(startDateString);
                            binding.endDate.setText(endDateString);

                            if (TYPE_PAYMENT == 1 || TYPE_PAYMENT == 2) {
                                sumAll = houseDetailResponse.getPrice() * daysDiff;
                                binding.priceAndCount.setText("$" + fm.format(houseDetailResponse.getPrice()) + " x " + daysDiff + " đêm");
                                binding.sumPrice.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                                binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                            } else if (TYPE_PAYMENT == 3) {
                                binding.priceAndCount.setText("$" + fm.format(houseDetailResponse.getPrice()) + " x " + daysDiff + " đêm");
                                binding.sumPrice.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                                sumAllPercent = (long) (sumAll * 0.32);
                                binding.priceAll.setText("$" + fm.format(sumAllPercent * daysDiff));
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        binding.btnPay.setOnClickListener(v -> {
            if (binding.startDate.getText().toString().equals("")) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.dialogstartdate))
                        .setMessage(this.getString(R.string.dialogcontentnomal))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000).setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                return;
            } else if (binding.person.getText().toString().equals(this.getString(R.string.limitperson))) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.BillOder_add_amount))
                        .setMessage(this.getString(R.string.dialogcontentnomal))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000).setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                return;
            } else if (binding.textPayment.getText().toString().equals(this.getString(R.string.textpayment)) && TYPE_PAYMENT == 1) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.BillOder_add_card))
                        .setMessage(this.getString(R.string.dialogcontentnomal))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000).setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            } else if (binding.phone.getText().toString().equals(this.getString(R.string.nullphone))) {
                CookieBar.build(this)
                        .setTitle(this.getString(R.string.BillOder_add_phone_number))
                        .setMessage(this.getString(R.string.dialogcontentnomal))
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(5000).setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                return;
            } else {
                if (TYPE_PAYMENT == 1) {
                    paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("RoyalJourney Company", new PaymentSheet.CustomerConfiguration(customerID, EpericalKey)));
                } else if (TYPE_PAYMENT == 3) {
                    paymentSheet2.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("RoyalJourney Company", new PaymentSheet.CustomerConfiguration(customerID, EpericalKey)));
                } else {

                    orderViewModel.postOrder(new OrderCreate(
                            "RJ" + random,
                            houseDetailResponse.getHostResponse().get_id(),
                            houseDetailResponse.get_id(),
                            UserClient.getInstance().getId(),
                            payday,
                            String.valueOf(sumAll),
                            "",
                            true,
                            false,
                            false,
                            startDateStringPrivate,
                            endDateStringPrivate,
                            personLimitPrivate,
                            phonePrivate
                    ));
                }
            }
        });

        binding.contentCancellationPolicy.setOnClickListener(v -> {
            bottomSheetCancellationPolicy = new BottomSheetCancellationPolicy(this, R.style.MaterialDialogSheet, new BottomSheetCancellationPolicy.CallbackOnClickBottomSheetCancellationPolicy() {
                @Override
                public void onclickBtn() {
                    startActivity(new Intent(BillOderActivity.this, CancellationPolicyActivity.class));
                }

                @Override
                public void onClose() {
                    bottomSheetCancellationPolicy.dismiss();
                }
            }, houseDetailResponse);
            bottomSheetCancellationPolicy.show();
            bottomSheetCancellationPolicy.setCanceledOnTouchOutside(false);
        });

        orderViewModel.getOrderResponseMutableLiveData().observe(this, new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {
                if (orderResponse.isMessege()) {
                    Intent intent = new Intent(BillOderActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("CheckSuccess", "1111111111111");
                    startActivity(intent);
                } else {
                    ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Thất bại", getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
                }
            }
        });

        orderViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });


    }

    private boolean validateinfo(String phone) {
        if (phone.length() == 0) {
            binding.edPhone.requestFocus();
            binding.edPhone.setError(this.getString(R.string.BillOder_add_phone_number_check));
        } else if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
            binding.edPhone.requestFocus();
            binding.edPhone.setError(this.getString(R.string.BillOder_add_phone_number_check_1));
            return false;
        } else {
            binding.phone.setText(binding.edPhone.getText().toString().trim());
            binding.edPhone.setVisibility(View.GONE);
            binding.btnComfirmPhone.setVisibility(View.GONE);
            return true;
        }
        return true;
    }

    private void initData(String idHouse) {
        detailProductViewModel.getHouseById(idHouse).observe(this, item -> {
            houseDetailResponse = item;
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(this).load(item.getImages().get(0)).apply(options).into(binding.imageHouse);
            binding.nameHouse.setText(item.getName());
            binding.tvTimeNhanPhong.setText(item.getOpening());
            binding.tvTimeTra.setText(item.getEnding());
            binding.personLimitHouse.setText("Tối đa " + item.getLimitPerson() + " khách");
            binding.priceAndCount.setText("$" + fm.format(item.getPrice()) + " x 1 đêm");
            binding.sumPrice.setText("$" + fm.format(item.getPrice()));
            binding.priceAll.setText("$" + fm.format(item.getPrice()));
            binding.startDateAndEndDate.setText(item.getStartDate() + " - " + item.getEndDate());
            sumAll = item.getPrice();
            checkStartDateResponse = item.getStartDate();
            checkEndDateResponse = item.getEndDate();

            String textCancel = "Nếu bạn hủy trước ngày " + houseDetailResponse.getCancellatioDate() + " bạn sẽ được hoàn lại một phần tiền. Tìm hiểu thêm";

            Spannable wordtoSpan = new SpannableString(textCancel);

            wordtoSpan.setSpan(new UnderlineSpan(), 23, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 23, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 23, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            wordtoSpan.setSpan(new UnderlineSpan(), 70, 83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 70, 83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 70, 83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            binding.textCancel.setText(wordtoSpan);
        });


    }

    private void getEpericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            EpericalKey = object.getString("id");
                            Log.e("MinhEpericalKey", EpericalKey);
                            getClientSeretEpericalKey(customerID, TYPE_PAYMENT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + AppConstant.SECRET_KEY);
                header.put("Stripe-Version", "2022-08-01");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BillOderActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getClientSeretEpericalKey(String customerID, int type_pay) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            Log.e("MinhClientSecret", ClientSecret);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + AppConstant.SECRET_KEY);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", String.valueOf(sumAll));
                params.put("currency", "VND");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            orderViewModel.postOrder(new OrderCreate(
                    "RJ" + random,
                    houseDetailResponse.getHostResponse().get_id(),
                    houseDetailResponse.get_id(),
                    UserClient.getInstance().getId(),
                    payday,
                    String.valueOf(sumAll),
                    "",
                    false,
                    true,
                    false,
                    startDateStringPrivate,
                    endDateStringPrivate,
                    personLimitPrivate,
                    phonePrivate
            ));
        }
    }

    private void onPaymentResult2(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            orderViewModel.postOrder(new OrderCreate(
                    "RJ" + random,
                    houseDetailResponse.getHostResponse().get_id(),
                    houseDetailResponse.get_id(),
                    UserClient.getInstance().getId(),
                    payday,
                    String.valueOf(sumAll),
                    String.valueOf(sumAllPercent),
                    false,
                    false,
                    true,
                    startDateStringPrivate,
                    endDateStringPrivate,
                    personLimitPrivate,
                    phonePrivate
            ));
        }
    }

    private void showDialog() {
        bottomSheetPayment = new BottomSheetPayment(BillOderActivity.this, R.style.MaterialDialogSheet, new BottomSheetPayment.CallBack() {
            @Override
            public void onCLickCLose() {
                bottomSheetPayment.dismiss();
            }

            @Override
            public void onClickPayment() {
                bottomSheetPayment.dismiss();
                binding.imageGooglePlay.setVisibility(View.GONE);
                binding.imagePaypal.setVisibility(View.GONE);
                binding.imageMatercard.setVisibility(View.GONE);
                binding.textPayment.setText("Thẻ VISA (VISA card)");


                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "https://api.stripe.com/v1/customers",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    customerID = object.getString("id");
                                    Log.e("MinhcustomerID", customerID);
                                    getEpericalKey(customerID);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<>();
                        header.put("Authorization", "Bearer " + AppConstant.SECRET_KEY);
                        return header;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(BillOderActivity.this);
                requestQueue.add(stringRequest);
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
        if (sum > houseDetailResponse.getLimitPerson()) {
            CookieBar.build(this)
                    .setTitle(this.getString(R.string.BillOder_host_received_max))
                    .setMessage(this.getString(R.string.dialogcontentnomal))
                    .setIcon(R.drawable.ic_warning_icon_check)
                    .setTitleColor(R.color.black)
                    .setMessageColor(R.color.black)
                    .setDuration(5000).setSwipeToDismiss(false)
                    .setBackgroundRes(R.drawable.background_toast)
                    .setCookiePosition(CookieBar.BOTTOM)
                    .show();
            return;
        } else {
            personLimitPrivate = sum;
            binding.person.setText(sum + " khách");
        }
    }
}