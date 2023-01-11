package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.annotation.SuppressLint;
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
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityBookingBinding;
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetEditPerson;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPayment;
import com.example.bookingapproyaljourney.view_model.BookingViewModel;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BookingActivity extends AppCompatActivity implements BottomSheetEditPerson.CallBack {
    private ActivityBookingBinding binding;
    private String phonePrivate, yeuCauThemPrivate;
    private int TYPE_PAYMENT = 1;
    private BottomSheetEditPerson bottomSheetEditPerson;
    private int personLimitPrivate;
    private BookingViewModel bookingViewModel;
    private String idRoom;
    private long daysDiff = 1;
    private int payday;
    private BottomSheetPayment bottomSheetPayment;
    private PaymentSheet paymentSheet;

    private String customerID;
    private String EpericalKey;
    private String ClientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initView();
    }

    private void initView() {

        PaymentConfiguration.init(BookingActivity.this, AppConstant.PUBLISHABLE_KEY);

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });
        idRoom = getIntent().getStringExtra(AppConstant.ROOM_EXTRA);
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);


        binding.editPerson.setPaintFlags(binding.editPerson.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.editCountRoom.setPaintFlags(binding.editCountRoom.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.addPhone.setOnClickListener(v -> {
            binding.edPhone.setVisibility(View.VISIBLE);
            binding.btnComfirmPhone.setVisibility(View.VISIBLE);
        });
        binding.addYeuCauThem.setOnClickListener(v -> {
            binding.edYeuCauThem.setVisibility(View.VISIBLE);
            binding.btnComfirmYeuCauThem.setVisibility(View.VISIBLE);
        });

        binding.btnComfirmPhone.setOnClickListener(v -> {
            String phone = binding.edPhone.getText().toString();
            phonePrivate = binding.edPhone.getText().toString();
            validateinfo(phone);
        });
        binding.btnComfirmYeuCauThem.setOnClickListener(v -> {
            String yeuCauThem = binding.edYeuCauThem.getText().toString();
            yeuCauThemPrivate = binding.edYeuCauThem.getText().toString();

            if (binding.edYeuCauThem.getText().toString().equals("")) {
                binding.textMore.setText("Không có yêu cầu gì thêm");
                binding.edYeuCauThem.setVisibility(View.GONE);
                binding.btnComfirmYeuCauThem.setVisibility(View.GONE);
            } else {
                binding.textMore.setText(binding.edYeuCauThem.getText().toString().trim());
                binding.edYeuCauThem.setVisibility(View.GONE);
                binding.btnComfirmYeuCauThem.setVisibility(View.GONE);
            }
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
//            if (UserClient.getInstance().getCountBooking() < 4) {
//                CookieBar.build(BillOderActivity.this)
//                        .setTitle(this.getString(R.string.BillOder_add_uy_tin))
//                        .setMessage(this.getString(R.string.BillOder_add_uy_tin_5))
//                        .setIcon(R.drawable.ic_warning_icon_check)
//                        .setTitleColor(R.color.black)
//                        .setMessageColor(R.color.black)
//                        .setDuration(3000).setSwipeToDismiss(false)
//                        .setBackgroundRes(R.drawable.background_toast)
//                        .setCookiePosition(CookieBar.BOTTOM)
//                        .show();
//                return;
//            }
            TYPE_PAYMENT = 2;
            binding.contentPayment.setVisibility(View.GONE);
            binding.payOnline.setChecked(false);
            binding.payOffline.setChecked(true);
        });

        binding.payOnline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                    TYPE_PAYMENT = 1;
                    binding.contentPayment.setVisibility(View.VISIBLE);
                    binding.payOffline.setChecked(false);
                    binding.payOnline.setChecked(true);
                }
            }
        });

        binding.payOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    if (UserClient.getInstance().getCountBooking() < 4) {
//                        CookieBar.build(BillOderActivity.this)
//                                .setTitle(BillOderActivity.this.getString(R.string.BillOder_add_uy_tin))
//                                .setMessage(BillOderActivity.this.getString(R.string.BillOder_add_uy_tin_5))
//                                .setIcon(R.drawable.ic_warning_icon_check)
//                                .setTitleColor(R.color.black)
//                                .setMessageColor(R.color.black)
//                                .setDuration(3000).setSwipeToDismiss(false)
//                                .setBackgroundRes(R.drawable.background_toast)
//                                .setCookiePosition(CookieBar.BOTTOM)
//                                .show();
//                        binding.payOffline.setChecked(false);
//                        return;
//                    }
                    TYPE_PAYMENT = 2;
//                    binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
                    binding.contentPayment.setVisibility(View.GONE);
                    binding.payOnline.setChecked(false);
//                    binding.textCancel.setText(BillOderActivity.this.getString(R.string.Billoder_date_cancel) + houseDetailResponse.getCancellatioDate() + BillOderActivity.this.getString(R.string.Billoder_date_cancel_1));
                }
//                else {
//
//                }
            }
        });

        binding.contentEditPerson.setOnClickListener(v -> {
            showDiaLogEditPerson();
        });

        binding.contentLayoutCountRoom.setOnClickListener(v -> {
            showDiaLogEditPerson();
        });

        bookingViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        bookingViewModel.getHotelBillResponseMutableLiveData().observe(this, new Observer<HotelBillResponse>() {
            @Override
            public void onChanged(HotelBillResponse item) {
                if (item instanceof HotelBillResponse) {
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.img)
                            .error(R.drawable.img);
                    Glide.with(BookingActivity.this).load(item.getImageHotel()).apply(options).into(binding.imageHouse);
                    bottomSheetEditPerson = new BottomSheetEditPerson(BookingActivity.this, R.style.MaterialDialogSheet, BookingActivity.this, item);
                    binding.nameHouse.setText(item.getNameHotel());
                    binding.nameRoom.setText(item.getNameRoom());
                    binding.address.setText(item.getAddressHotel());
                    binding.tvSoGiuong.setText(item.getBedroom().get(0).getName());
                    if (item.isPolicyHotel()) {
                        binding.textCancel.setText("Hoàn huỷ miễn phí, bạn sẽ được hoàn tiền 100% , số tiền sẽ được chuyển vào ví RoyalJourneySuper");
                    } else {
                        binding.textCancel.setText("Không hỗ trợ hoàn huỷ, nếu bạn huỷ trước ngày " + item.getDateCancel() + " sẽ được hoàn tiền 100%, sau ngày " + item.getDateCancel() + " không hỗ trợ hoàn huỷ");
                    }
                }
            }
        });

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        builder.setCalendarConstraints(constraintsBuilder.build());
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker);
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder
                .setTitleText(BookingActivity.this.getString(R.string.Select_a_date))
                .setPositiveButtonText(BookingActivity.this.getString(R.string.SAVE))
                .setNegativeButtonText(BookingActivity.this.getString(R.string.Thoat))
                .setSelection(new Pair<>(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                .setCalendarConstraints(constraintBuilder.build())
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


                    long msDiff = endDate - startDate;

                    daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                    payday = Integer.parseInt(String.valueOf(daysDiff));
                    binding.payDay.setText(daysDiff + "");

//                    startDateStringPrivate = startDateString;
//                    endDateStringPrivate = endDateString;

                    binding.startDate.setText(startDateString);
                    binding.endDate.setText(endDateString);

//                    if (TYPE_PAYMENT == 1 || TYPE_PAYMENT == 2) {
//                        sumAll = houseDetailResponse.getPrice() * daysDiff;
//                        binding.priceAndCount.setText("$" + fm.format(houseDetailResponse.getPrice()) + " x " + daysDiff + " đêm");
//                        binding.sumPrice.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
//                        binding.priceAll.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
//                    } else if (TYPE_PAYMENT == 3) {
//                        binding.priceAndCount.setText("$" + fm.format(houseDetailResponse.getPrice()) + " x " + daysDiff + " đêm");
//                        binding.sumPrice.setText("$" + fm.format(houseDetailResponse.getPrice() * daysDiff));
//                        sumAllPercent = (long) (sumAll * 0.32);
//                        binding.priceAll.setText("$" + fm.format(sumAllPercent * daysDiff));
//                    }

                }
            });
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

        RequestQueue requestQueue = Volley.newRequestQueue(BookingActivity.this);
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
                params.put("amount", "100000");
                params.put("currency", "VND");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDialog() {
        bottomSheetPayment = new BottomSheetPayment(BookingActivity.this, R.style.MaterialDialogSheet, new BottomSheetPayment.CallBack() {
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

                RequestQueue requestQueue = Volley.newRequestQueue(BookingActivity.this);
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

    private void initToolbar() {
        binding.toolBar.setTitle(R.string.Confirmation_and_payment);
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
//        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
//            orderViewModel.postOrder(new OrderCreate(
//                    "RJ" + random,
//                    houseDetailResponse.getHostResponse().get_id(),
//                    houseDetailResponse.get_id(),
//                    UserClient.getInstance().getId(),
//                    payday,
//                    String.valueOf(sumAll),
//                    "",
//                    false,
//                    true,
//                    false,
//                    startDateStringPrivate,
//                    endDateStringPrivate,
//                    personLimitPrivate,
//                    phonePrivate
//            ));
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookingViewModel.getHotelAndRoomByIdRoom(idRoom);
    }

    @Override
    public void onCLickCLose() {
        bottomSheetEditPerson.dismiss();
    }

    @Override
    public void onCLickSum(int person, int children, int countRoom) {
        personLimitPrivate = person;
        binding.person.setText(person + " " + this.getString(R.string.Guest) + ", " + children + " trẻ em");
        binding.textCountRoom.setText(countRoom + " phòng");
    }
}