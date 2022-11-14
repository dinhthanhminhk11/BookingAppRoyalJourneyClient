package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetEditPerson;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPayment;
import com.example.bookingapproyaljourney.view_model.DetailProductViewModel;
import com.example.bookingapproyaljourney.view_model.OrderViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private HouseDetailResponse houseDetailResponse;
    private double sumAll;
    private PaymentSheet paymentSheet;

    private String customerID;
    private String EpericalKey;
    private String ClientSecret;
    private int payday;
    private final int random = new Random().nextInt(100000) + 2000000;
    private String startDateStringPrivate;
    private String endDateStringPrivate;
    private int personLimitPrivate;
    private String phonePrivate;

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

        PaymentConfiguration.init(BillOderActivity.this, AppConstant.PUBLISHABLE_KEY);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        initData(idHouse);

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
            phonePrivate = binding.edPhone.getText().toString();
            binding.phone.setText(binding.edPhone.getText().toString().trim());
            binding.edPhone.setVisibility(View.GONE);
            binding.btnComfirmPhone.setVisibility(View.GONE);
        });

        if (binding.payOnline.isChecked()) {
            binding.contentPayment.setVisibility(View.VISIBLE);
        } else {
            binding.contentPayment.setVisibility(View.GONE);
        }

        binding.contentPayOnline.setOnClickListener(v -> {
            TYPE_PAYMENT = 1;
            binding.contentPayment.setVisibility(View.VISIBLE);
            binding.payOffline.setChecked(false);
            binding.payOnline.setChecked(true);
        });

        binding.contentPayOffline.setOnClickListener(v -> {
            TYPE_PAYMENT = 2;
            binding.contentPayment.setVisibility(View.GONE);
            binding.payOnline.setChecked(false);
            binding.payOffline.setChecked(true);
        });

        binding.payOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TYPE_PAYMENT = 1;
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
                    TYPE_PAYMENT = 2;
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
                    payday = Integer.parseInt(String.valueOf(daysDiff));
                    binding.payDay.setText(daysDiff + "");

                    String startDateString = DateFormat.format("EEE, dd-MM", new Date(startDate)).toString();
                    String endDateString = DateFormat.format("EEE, dd-MM", new Date(endDate)).toString();

                    startDateStringPrivate = startDateString;
                    endDateStringPrivate = endDateString;

                    binding.startDate.setText(startDateString);
                    binding.endDate.setText(endDateString);
                    sumAll = houseDetailResponse.getPrice() * daysDiff;
                    binding.priceAndCount.setText("$" + fm.format(houseDetailResponse.getPrice()) + " x " + daysDiff + " đêm");
                    binding.sumPrice.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                    binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                    //Do something...
                }
            });
        });

        binding.btnPay.setOnClickListener(v -> {
            if (binding.startDate.getText().toString().equals("")) {
                ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, this.getString(R.string.dialogstartdate), this.getString(R.string.dialogcontentnomal));
                return;
            } else if (binding.person.getText().toString().equals(this.getString(R.string.limitperson))) {
                ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Thêm số lượng khách thuê của bạn để tiếp tục", this.getString(R.string.dialogcontentnomal));
                return;
            } else if (binding.textPayment.getText().toString().equals(this.getString(R.string.textpayment)) && TYPE_PAYMENT == 1) {
                ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Thêm thẻ thanh toán của bạn để tiếp tục", this.getString(R.string.dialogcontentnomal));
            } else if (binding.phone.getText().toString().equals(this.getString(R.string.nullphone))) {
                ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Thêm số điện thoại của bạn để tiếp tục", this.getString(R.string.dialogcontentnomal));
                return;
            } else {
                if (TYPE_PAYMENT == 1) {
                    paymentSheet.presentWithPaymentIntent(ClientSecret, new PaymentSheet.Configuration("ABC Company", new PaymentSheet.CustomerConfiguration(customerID, EpericalKey)));
                } else {
                    orderViewModel.postOrder(new OrderCreate(
                            "RJ" + random,
                            houseDetailResponse.getHostResponse().get_id(),
                            houseDetailResponse.get_id(),
                            UserClient.getInstance().getId(),
                            payday,
                            String.valueOf(sumAll),
                            true,
                            false,
                            startDateStringPrivate,
                            endDateStringPrivate,
                            personLimitPrivate,
                            phonePrivate
                    ));
                }
            }
        });

        orderViewModel.getOrderResponseMutableLiveData().observe(this, new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {
                if (orderResponse.isMessege()) {
                    Intent intent = new Intent(BillOderActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Thất bại", getString(R.string.dialogcontentnomal));
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
            sumAll = item.getPrice();
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
                            getClientSeretEpericalKey(customerID, EpericalKey);
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

    private void getClientSeretEpericalKey(String customerID, String epericalKey) {
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
                params.put("amount", "1000000");
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
                    false,
                    true,
                    startDateStringPrivate,
                    endDateStringPrivate,
                    personLimitPrivate,
                    phonePrivate
            ));
            ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Thanh toán thành công", this.getString(R.string.dialogcontentnomal));
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
            ToastCheck toastCheck = new ToastCheck(BillOderActivity.this, R.style.StyleToast, "Số lượng khách không được quá cho phép", this.getString(R.string.dialogcontentnomal));
            return;
        } else {
            personLimitPrivate = sum;
            binding.person.setText(sum + " khách");
        }
    }
}