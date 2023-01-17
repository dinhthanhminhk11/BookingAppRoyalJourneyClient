package com.example.bookingapproyaljourney.ui.activity.Hotel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityBookingBinding;
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.request.BillRequest;
import com.example.bookingapproyaljourney.response.bill.BillResponse;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.ui.activity.AddPassPinActivity;
import com.example.bookingapproyaljourney.ui.activity.PayCashYourActivity;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetEditPerson;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPassPayment;
import com.example.bookingapproyaljourney.view_model.BookingViewModel;
import com.example.librarytoastcustom.CookieBar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class BookingActivity extends BaseActivity implements BottomSheetEditPerson.CallBack {
    private ActivityBookingBinding binding;
    private String phonePrivate, yeuCauThemPrivate;
    private int TYPE_PAYMENT = 1;
    private BottomSheetEditPerson bottomSheetEditPerson;
    private int personLimitPrivate;
    private BookingViewModel bookingViewModel;
    private String idRoom;
    private long daysDiff = 1;
    private int payday;
    private NumberFormat fm = new DecimalFormat("#,###");
    private HotelBillResponse hotelBillResponse;
    private int countRoomLocal = 1;
    private String startDatePrivate;
    private String endDatePrivate;
    private long pricePrivate;
    private BottomSheetPassPayment bottomSheetPassPayment;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    private int priceUser;
    private String passCashUser;
    private int theme2;

    private SharedPreferences.Editor editorCheckPassCash;
    private boolean checkPassCash;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initView();
    }

    private void initData() {
        bookingViewModel.getHotelAndRoomByIdRoom(idRoom, UserClient.getInstance().getId());
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // dùng mật khẩu
                if (passCashUser.equals("")) {
                    startActivity(new Intent(BookingActivity.this, AddPassPinActivity.class));
                } else {
                    bottomSheetPassPayment = new BottomSheetPassPayment(BookingActivity.this, R.style.MaterialDialogSheet, new BottomSheetPassPayment.CallBack() {
                        @Override
                        public void onCLickCLose() {

                        }

                        @Override
                        public void onClickPayment(String s) {
                            if (passCashUser.equals(s)) {
                                bottomSheetPassPayment.dismiss();
                                // xác thực đúng
//                                payment();
//                                startActivity(new Intent(BookingActivity.this, PayCashYourActivity.class));
                                if (pricePrivate > priceUser) {
                                    CookieBar.build(BookingActivity.this)
                                            .setTitle("Thông báo")
                                            .setMessage("Số dư trong ví hiện tại không đủ")
                                            .setIcon(R.drawable.ic_warning_icon_check)
                                            .setTitleColor(R.color.black)
                                            .setMessageColor(R.color.black)
                                            .setDuration(5000).setSwipeToDismiss(false)
                                            .setBackgroundRes(R.drawable.background_toast)
                                            .setCookiePosition(CookieBar.BOTTOM)
                                            .show();
                                } else {
                                    bookingViewModel.createBooking(new BillRequest(
                                            hotelBillResponse.getIdHost(),
                                            UserClient.getInstance().getId(),
                                            hotelBillResponse.getIdHotel(),
                                            hotelBillResponse.getIdRoom(),
                                            startDatePrivate,
                                            endDatePrivate,
                                            payday,
                                            countRoomLocal,
                                            binding.person.getText().toString(),
                                            binding.phone.getText().toString(),
                                            binding.textMore.getText().toString(),
                                            Integer.parseInt(String.valueOf(pricePrivate)),
                                            false,
                                            true,
                                            hotelBillResponse.getDateCancel()
                                    ));
                                }

                            } else {
                                CookieBar.build(BookingActivity.this)
                                        .setTitle("Thông báo")
                                        .setMessage("Mã pin không chính xác")
                                        .setIcon(R.drawable.ic_warning_icon_check)
                                        .setTitleColor(R.color.black)
                                        .setMessageColor(R.color.black)
                                        .setDuration(5000).setSwipeToDismiss(false)
                                        .setBackgroundRes(R.drawable.background_toast)
                                        .setCookiePosition(CookieBar.BOTTOM)
                                        .show();
                            }
                        }
                    });
                    bottomSheetPassPayment.show();
                    bottomSheetPassPayment.setCanceledOnTouchOutside(false);
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                if (pricePrivate > priceUser) {
                    String textCancel = "Số dư trong ví hiện tại không đủ. Nạp thêm tiền ngay!";
                    Spannable wordtoSpan = new SpannableString(textCancel);

                    wordtoSpan.setSpan(new UnderlineSpan(), 34, textCancel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 34, textCancel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 34, textCancel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    new ToastCheck(BookingActivity.this, R.style.StyleToast, "Thông báo", wordtoSpan, R.drawable.ic_warning_icon_check).setConsumer(o -> {
                        if (o instanceof Boolean) {
                            startActivity(new Intent(BookingActivity.this, PayCashYourActivity.class));
                        }
                    });

//                    CookieBar.build(BookingActivity.this)
//                            .setTitle("Thông báo")
//                            .setMessage("Số dư trong ví hiện tại không đủ")
//                            .setIcon(R.drawable.ic_warning_icon_check)
//                            .setTitleColor(R.color.black)
//                            .setMessageColor(R.color.black)
//                            .setDuration(5000).setSwipeToDismiss(false)
//                            .setBackgroundRes(R.drawable.background_toast)
//                            .setCookiePosition(CookieBar.BOTTOM)
//                            .show();
                } else {
                    bookingViewModel.createBooking(new BillRequest(
                            hotelBillResponse.getIdHost(),
                            UserClient.getInstance().getId(),
                            hotelBillResponse.getIdHotel(),
                            hotelBillResponse.getIdRoom(),
                            startDatePrivate,
                            endDatePrivate,
                            payday,
                            countRoomLocal,
                            binding.person.getText().toString(),
                            binding.phone.getText().toString(),
                            binding.textMore.getText().toString(),
                            Integer.parseInt(String.valueOf(pricePrivate)),
                            false,
                            true,
                            hotelBillResponse.getDateCancel()
                    ));
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        SharedPreferences sharedPreferences2 = this.getSharedPreferences(AppConstant.SHAREDPREFERENCES_PASS, MODE_PRIVATE);
        theme2 = sharedPreferences2.getInt(AppConstant.SHAREDPREFERENCES_PASS, 0);
        SharedPreferences sharedPreferencesCheckPassCash = getSharedPreferences(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, MODE_PRIVATE);
        editorCheckPassCash = sharedPreferencesCheckPassCash.edit();
        checkPassCash = sharedPreferencesCheckPassCash.getBoolean(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, true);
    }

    private void initView() {
        executor = ContextCompat.getMainExecutor(this);

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Xác thực vân tay").setSubtitle("Uỷ quyền thông tin Sinh trắc").setNegativeButtonText("Dùng mật khẩu ví").build();

        idRoom = getIntent().getStringExtra(AppConstant.ROOM_EXTRA);
        bookingViewModel = new ViewModelProvider(this).get(BookingViewModel.class);
        binding.payOnline.setChecked(true);

        binding.editPerson.setPaintFlags(binding.editPerson.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.editCountRoom.setPaintFlags(binding.editCountRoom.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.text9CountRoom.setPaintFlags(binding.text9CountRoom.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
                    TYPE_PAYMENT = 2;
                    binding.contentPayment.setVisibility(View.GONE);
                    binding.payOnline.setChecked(false);
                }
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
                    hotelBillResponse = item;
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
                    binding.text9CountRoom.setText(countRoomLocal + " phòng");
                    binding.priceAndCount.setText(fm.format(item.getPriceRoom()) + " ₫ " + BookingActivity.this.getString(R.string.one_night));
                    binding.sumPrice.setText(fm.format(item.getPriceRoom()) + " ₫");
                    binding.priceAll.setText(fm.format(item.getPriceRoom()) + " ₫");
                    binding.priceSupperLine.setText(fm.format(item.getPriceRoom() * 0.1) + " ₫");
                    pricePrivate = item.getPriceRoom();
                    binding.editTextPrice.setEnabled(false);
                    binding.editTextPrice.setText(fm.format(Integer.parseInt(item.getPriceCashFlow())) + " ₫");
                    priceUser = Integer.parseInt(item.getPriceCashFlow());
                    passCashUser = item.getPassCashFlow();
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
                    String startDateString1 = DateFormat.format("EEE, dd-MM", new Date(startDate)).toString();
                    String endDateString2 = DateFormat.format("EEE, dd-MM", new Date(endDate)).toString();
                    String startDateString = DateFormat.format("EEE, dd-MM-yyyy", new Date(startDate)).toString();
                    String endDateString = DateFormat.format("EEE, dd-MM-yyyy", new Date(endDate)).toString();


                    long msDiff = endDate - startDate;

                    daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                    payday = Integer.parseInt(String.valueOf(daysDiff));
                    binding.payDay.setText(daysDiff + "");

                    startDatePrivate = startDateString;
                    endDatePrivate = endDateString;

                    binding.startDate.setText(startDateString1);
                    binding.endDate.setText(endDateString2);
                    loadData();
                }
            });
        });

        countDownTimer = new CountDownTimer(300000, 1000 / 100) {
            @Override
            public void onTick(long l) {
                // call when timer start
            }

            @Override
            public void onFinish() {
                editorCheckPassCash.putBoolean(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, true);
                editorCheckPassCash.commit();
            }
        };

        binding.btnPay.setOnClickListener(v -> {
            if (binding.startDate.getText().toString().equals("")) {
                CookieBar.build(this).setTitle(this.getString(R.string.dialogstartdate)).setMessage(this.getString(R.string.dialogcontentnomal)).setIcon(R.drawable.ic_warning_icon_check).setTitleColor(R.color.black).setMessageColor(R.color.black).setDuration(5000).setSwipeToDismiss(false).setBackgroundRes(R.drawable.background_toast).setCookiePosition(CookieBar.BOTTOM).show();
                return;
            } else if (binding.person.getText().toString().equals(this.getString(R.string.limitperson))) {
                CookieBar.build(this).setTitle(this.getString(R.string.BillOder_add_amount)).setMessage(this.getString(R.string.dialogcontentnomal)).setIcon(R.drawable.ic_warning_icon_check).setTitleColor(R.color.black).setMessageColor(R.color.black).setDuration(5000).setSwipeToDismiss(false).setBackgroundRes(R.drawable.background_toast).setCookiePosition(CookieBar.BOTTOM).show();
                return;
            } else if (binding.phone.getText().toString().equals(this.getString(R.string.nullphone))) {
                CookieBar.build(this).setTitle(this.getString(R.string.BillOder_add_phone_number)).setMessage(this.getString(R.string.dialogcontentnomal)).setIcon(R.drawable.ic_warning_icon_check).setTitleColor(R.color.black).setMessageColor(R.color.black).setDuration(5000).setSwipeToDismiss(false).setBackgroundRes(R.drawable.background_toast).setCookiePosition(CookieBar.BOTTOM).show();
                return;
            } else if (!binding.payOnline.isChecked() && !binding.payOffline.isChecked()) {
                CookieBar.build(this).setTitle("Thêm hình thức thanh toán").setMessage(this.getString(R.string.dialogcontentnomal)).setIcon(R.drawable.ic_warning_icon_check).setTitleColor(R.color.black).setMessageColor(R.color.black).setDuration(5000).setSwipeToDismiss(false).setBackgroundRes(R.drawable.background_toast).setCookiePosition(CookieBar.BOTTOM).show();
//                new ToastCheck(this, R.style.StyleToast, "Hãy đóng góp ý kiến của bạn", this.getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
            } else if (passCashUser.equals("")) {
                startActivity(new Intent(this, AddPassPinActivity.class));
            } else {
                switch (TYPE_PAYMENT) {
                    case 1:
                        if (checkPassCash) {
                            if (theme2 == AppConstant.POS_VANTAY) {
                                biometricPrompt.authenticate(promptInfo);
                            } else {
                                bottomSheetPassPayment = new BottomSheetPassPayment(this, R.style.MaterialDialogSheet, new BottomSheetPassPayment.CallBack() {
                                    @Override
                                    public void onCLickCLose() {

                                    }

                                    @Override
                                    public void onClickPayment(String s) {
                                        if (passCashUser.equals(s)) {
                                            bottomSheetPassPayment.dismiss();
//                                payment();
//                                startActivity(new Intent(, PayCashYourActivity.class));
                                            bookingViewModel.createBooking(new BillRequest(
                                                    hotelBillResponse.getIdHost(),
                                                    UserClient.getInstance().getId(),
                                                    hotelBillResponse.getIdHotel(),
                                                    hotelBillResponse.getIdRoom(),
                                                    startDatePrivate,
                                                    endDatePrivate,
                                                    payday,
                                                    countRoomLocal,
                                                    binding.person.getText().toString(),
                                                    binding.phone.getText().toString(),
                                                    binding.textMore.getText().toString(),
                                                    Integer.parseInt(String.valueOf(pricePrivate)),
                                                    false,
                                                    true,
                                                    hotelBillResponse.getDateCancel()
                                            ));
                                        } else {
                                            CookieBar.build(BookingActivity.this)
                                                    .setTitle(BookingActivity.this.getString(R.string.BillOder_add_phone_number))
                                                    .setMessage(BookingActivity.this.getString(R.string.dialogcontentnomal))
                                                    .setIcon(R.drawable.ic_warning_icon_check)
                                                    .setTitleColor(R.color.black)
                                                    .setMessageColor(R.color.black)
                                                    .setDuration(5000).setSwipeToDismiss(false)
                                                    .setBackgroundRes(R.drawable.background_toast)
                                                    .setCookiePosition(CookieBar.BOTTOM)
                                                    .show();
                                        }
                                    }
                                });
                                bottomSheetPassPayment.show();
                                bottomSheetPassPayment.setCanceledOnTouchOutside(false);
                            }
                            editorCheckPassCash.putBoolean(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, false);
                            editorCheckPassCash.commit();
                            countDownTimer.start();
                        } else {
                            if (pricePrivate > priceUser) {
                                String textCancel = "Số dư trong ví hiện tại không đủ. Nạp thêm tiền ngay!";
                                Spannable wordtoSpan = new SpannableString(textCancel);

                                wordtoSpan.setSpan(new UnderlineSpan(), 34, textCancel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 34, textCancel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 34, textCancel.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                new ToastCheck(BookingActivity.this, R.style.StyleToast, "Thông báo", wordtoSpan, R.drawable.ic_warning_icon_check).setConsumer(o -> {
                                    if (o instanceof Boolean) {
                                        startActivity(new Intent(BookingActivity.this, PayCashYourActivity.class));
                                    }
                                });
                            } else {
                                bookingViewModel.createBooking(new BillRequest(
                                        hotelBillResponse.getIdHost(),
                                        UserClient.getInstance().getId(),
                                        hotelBillResponse.getIdHotel(),
                                        hotelBillResponse.getIdRoom(),
                                        startDatePrivate,
                                        endDatePrivate,
                                        payday,
                                        countRoomLocal,
                                        binding.person.getText().toString(),
                                        binding.phone.getText().toString(),
                                        binding.textMore.getText().toString(),
                                        Integer.parseInt(String.valueOf(pricePrivate)),
                                        false,
                                        true,
                                        hotelBillResponse.getDateCancel()
                                ));
                            }
                        }
                        break;
                    case 2:
                        bookingViewModel.createBooking(new BillRequest(
                                hotelBillResponse.getIdHost(),
                                UserClient.getInstance().getId(),
                                hotelBillResponse.getIdHotel(),
                                hotelBillResponse.getIdRoom(),
                                startDatePrivate,
                                endDatePrivate,
                                payday,
                                countRoomLocal,
                                binding.person.getText().toString(),
                                binding.phone.getText().toString(),
                                binding.textMore.getText().toString(),
                                Integer.parseInt(String.valueOf(pricePrivate)),
                                true,
                                false,
                                hotelBillResponse.getDateCancel()
                        ));
                        break;
                }

            }
        });

        bookingViewModel.getBillResponseMutableLiveData().observe(this, new Observer<BillResponse>() {
            @Override
            public void onChanged(BillResponse billResponse) {
                if (billResponse instanceof BillResponse) {
                    if (billResponse.isMessage()) {
                        Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("CheckSuccess", "1111111111111");
                        startActivity(intent);
                    } else {
                        ToastCheck toastCheck = new ToastCheck(BookingActivity.this, R.style.StyleToast, getString(R.string.failure), getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
                    }
                }
            }
        });
    }

    public void loadData() {
        binding.text9CountRoom.setText(countRoomLocal + " phòng");
        binding.priceAndCount.setText(fm.format(hotelBillResponse.getPriceRoom()) + " ₫ x " + daysDiff + " đêm");
        binding.sumPrice.setText(fm.format(hotelBillResponse.getPriceRoom() * daysDiff) + "  ₫");
        binding.priceAll.setText(fm.format(hotelBillResponse.getPriceRoom() * daysDiff * countRoomLocal) + "  ₫");
        binding.priceSupperLine.setText(fm.format(hotelBillResponse.getPriceRoom() * daysDiff * countRoomLocal * 0.1) + " ₫");
        pricePrivate = hotelBillResponse.getPriceRoom() * daysDiff * countRoomLocal;
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }

    @Override
    public void onCLickCLose() {
        bottomSheetEditPerson.dismiss();
    }

    @Override
    public void onCLickSum(int person, int children, int countRoom) {
        personLimitPrivate = person;
        this.countRoomLocal = countRoom;
        binding.person.setText(person + " người lớn, " + children + " trẻ em");
        binding.textCountRoom.setText(countRoom + " phòng");
        loadData();
    }
}