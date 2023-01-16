package com.example.bookingapproyaljourney.ui.activity;

import static com.example.bookingapproyaljourney.constants.AppConstant.CheckSuccess;
import static com.example.bookingapproyaljourney.constants.AppConstant.deleteOrderResponse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityStatusBillBinding;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.bill.StatusBillResponse;
import com.example.bookingapproyaljourney.response.order.OrderListResponse;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;
import com.example.bookingapproyaljourney.ui.activity.Hotel.HotelActivity;
import com.example.bookingapproyaljourney.ui.activity.feedback.FeedBackActivity;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetCancellationPolicy;
import com.example.bookingapproyaljourney.view_model.StatusOrderViewModel;
import com.example.librarytoastcustom.CookieBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StatusBillActivity extends AppCompatActivity {

    private ActivityStatusBillBinding binding;
    private OrderListResponse orderListResponse;
    private String idBill;
    private StatusOrderViewModel statusOrderViewModel;
    private NumberFormat fm = new DecimalFormat("#,###");
    private String dateCancel;
    private boolean checkIsBacking;
    private String imageHost;
    private ImageView close;
    private TextView text;
    private TextView btnCancel;
    private Button login;
    private boolean checkSeem;
    private boolean isSuccess;
    private String textReasonUser;
    private String img_boss = "";
    private String id_House = "";
    private String name_boss = "";
    private BottomSheetCancellationPolicy bottomSheetCancellationPolicy;
    private HouseDetailResponse houseDetailResponse1;
    private String dataCreate = "";
    private StatusBillResponse statusBillResponse;

    public StatusBillActivity() {
    }

    @SuppressLint("StringFormatInvalid")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolBar.setTitle(this.getString(R.string.trip_details));
        binding.toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        binding.text9CountRoom.setPaintFlags(binding.text9CountRoom.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        idBill = getIntent().getStringExtra(AppConstant.ID_ORDER);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        statusOrderViewModel = new ViewModelProvider(this).get(StatusOrderViewModel.class);

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        initData();
        final Dialog dialogLogOut = new Dialog(this);
        dialogLogOut.setContentView(R.layout.dia_log_comfirm_logout_ver2);
        Window window2 = dialogLogOut.getWindow();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialogLogOut.getWindow() != null) {
            dialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        close = (ImageView) dialogLogOut.findViewById(R.id.close);
        text = (TextView) dialogLogOut.findViewById(R.id.text);
        btnCancel = (TextView) dialogLogOut.findViewById(R.id.btnCancel);
        btnCancel.setPaintFlags(btnCancel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        login = (Button) dialogLogOut.findViewById(R.id.login);

        text.setText(this.getString(R.string.trip_details));
        login.setText(this.getString(R.string.Confirm));

        close.setOnClickListener(v -> {
            dialogLogOut.cancel();
        });

        btnCancel.setOnClickListener(v -> {
            dialogLogOut.cancel();
        });



        statusOrderViewModel.getmProgressMutableData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        statusOrderViewModel.getStatusBillResponseMutableLiveData().observe(this, new Observer<StatusBillResponse>() {
            @Override
            public void onChanged(StatusBillResponse item) {
                if (item instanceof StatusBillResponse) {
                    id_House = item.getIdHotel();
                    name_boss = item.getNameHost();
                    img_boss = item.getImageHost();
                    statusBillResponse = item;
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.img)
                            .error(R.drawable.img);
                    Glide.with(StatusBillActivity.this).load(item.getImageHotel()).apply(options).into(binding.imageHouse);
                    binding.nameHouse.setText(item.getNameHouse());
                    binding.tvSoGiuong.setText(item.getBedroom().get(0).getName());
                    binding.nameRoom.setText(item.getNameRoom());
                    binding.startDate.setText(item.getStartDate());
                    binding.endDate.setText(item.getEndDate());


                    if (item.isPolicyHotel()) {
                        binding.contentCancel.setText("Hoàn huỷ miễn phí, bạn sẽ được hoàn tiền 100% , số tiền sẽ được chuyển vào ví RoyalJourneySuper");
                    } else {
                        binding.contentCancel.setText("Không hỗ trợ hoàn huỷ, nếu bạn huỷ trước ngày " + item.getCheckDataCancel() + " sẽ được hoàn tiền 100%, sau ngày " + item.getCheckDataCancel() + " không hỗ trợ hoàn huỷ");
                    }
                    binding.text9CountRoom.setText(item.getCountRoom() + " phòng");
                    binding.person.setText(item.getNumberGuests());
                    binding.phone.setText(item.getPhone() + "");
                    binding.payDay.setText(item.getPayDay() + "");
                    binding.priceAll.setText(fm.format(item.getPriceAll()) + " ₫");
                    binding.sumPrice.setText(fm.format(item.getPriceRoom()) + " ₫");
                    binding.priceSupperLine.setText(fm.format(item.getPriceAll() * 0.1) + " ₫");
                    binding.sumPrice1.setText(fm.format(item.getPriceAll()) + " ₫");
                    binding.priceAndCount.setText("1 phòng ");
                    binding.priceAndCount1.setText(item.getCountRoom() + " phòng x " + item.getPayDay() + " ngày");
                    binding.textMore.setText(item.getSpecialRequirements());
                    if (item.isBanking()) {
                        binding.iconPay.setVisibility(View.VISIBLE);
                        binding.textPayment.setText("Ví RoyalJourneySuper Account");
                    }
                    if (item.isCashMoney()) {
                        binding.textPayment.setText("Thanh toán sau khi trả phòng");
                    }
                    binding.textContentMore.setText("Trạng thái: " + item.getStatus());
                    binding.textContentMore1.setText("Ngày đặt: " + item.getDateCreate());
                    binding.textContentMore2.setText("Giờ đặt: " + item.getTimeCreate());
                    if (item.getStatus().equals("Khách huỷ") || item.getStatus().equals("Chủ huỷ")) {
                        binding.btnPay.setVisibility(View.GONE);
                    } else if (item.getStatus().equals("Đã trả phòng")) {
                        binding.btnFeedback.setVisibility(View.VISIBLE);
                        binding.btnPay.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.btnFeedback.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, FeedBackActivity.class);
            intent1.putExtra("ID_HOUSE", id_House);
            intent1.putExtra("IMG_BOSS", img_boss);
            intent1.putExtra("NAME_BOSS", name_boss);
            startActivity(intent1);
        });

//        login.setOnClickListener(v -> {
//            if (isSuccess) {
//                statusOrderViewModel.updateOrderByUser(new OrderRequest(
//                        idOrder,
//                        AppConstant.Đa_xac_nhan,
//                        "",
//                        ""
//                ));
//            } else {
//                statusOrderViewModel.editOrderByUserUpdateOrderByIdNotSeem(new OrderRequest(
//                        idOrder,
//                        AppConstant.Dang_cho,
//                        "",
//                        ""
//                ));
//            }
//            dialogLogOut.cancel();
//        });

        binding.cancelRequest.setOnClickListener(v -> {
            dialogLogOut.show();
        });

        statusOrderViewModel.getHouseDetailResponseMutableLiveData().observe(StatusBillActivity.this, new Observer<HouseDetailResponse>() {
            @Override
            public void onChanged(HouseDetailResponse houseDetailResponse) {
                houseDetailResponse1 = houseDetailResponse;
                binding.nameHouse.setText(houseDetailResponse.getName());
                binding.priceAndCount.setText(fm.format(houseDetailResponse.getPrice()) + " x " + binding.payDay.getText().toString() + " đêm");
                binding.tvTimeNhanPhong.setText(houseDetailResponse.getOpening());
                binding.tvTimeTra.setText(houseDetailResponse.getEnding());

                String textCancel = String.format(getResources().getString(R.string.Cancel_before_date3), houseDetailResponse.getCancellatioDate());

//                Spannable wordtoSpan = new SpannableString(textCancel);
//
//                wordtoSpan.setSpan(new UnderlineSpan(), 23, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 23, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 23, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                wordtoSpan.setSpan(new UnderlineSpan(), 70, 83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 70, 83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), 70, 83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                binding.contentCancel.setText(textCancel);

                dateCancel = houseDetailResponse.getCancellatioDate();
                imageHost = houseDetailResponse.getHostResponse().getImage();
            }
        });

        statusOrderViewModel.getOrderStatusResponseMutableLiveData().observe(this, new Observer<OrderStatusResponse>() {
            @Override
            public void onChanged(OrderStatusResponse orderStatusResponse) {
                if (orderStatusResponse.isMessege()) {
                    Intent intent = new Intent(StatusBillActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(CheckSuccess, AppConstant.CancelBookingActivityByAccess);
                    startActivity(intent);
                } else {

                }
            }
        });

        binding.btnPay.setOnClickListener(v -> {
            if (UserClient.getInstance().getCountBooking() < -5) {
                CookieBar.build(this)
                        .setTitle(R.string.Your_reputation_is_very_low)
                        .setMessage(R.string.By_canceling_too_many_rooms_you_will_not_be_able_to_continue_to_cancel)
                        .setIcon(R.drawable.ic_warning_icon_check)
                        .setTitleColor(R.color.black)
                        .setMessageColor(R.color.black)
                        .setDuration(3000)
                        .setSwipeToDismiss(false)
                        .setBackgroundRes(R.drawable.background_toast)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
                return;
            }
            Intent intent = new Intent(StatusBillActivity.this, CancelBookingActivity.class);
            intent.putExtra(AppConstant.STATUS_BILL, statusBillResponse.getIdOrder());
            startActivity(intent);
        });

        binding.contentCancelLayout.setOnClickListener(v -> {
//            bottomSheetCancellationPolicy = new BottomSheetCancellationPolicy(this, R.style.MaterialDialogSheet, new BottomSheetCancellationPolicy.CallbackOnClickBottomSheetCancellationPolicy() {
//                @Override
//                public void onclickBtn() {
//                    startActivity(new Intent(StatusBillActivity.this, CancellationPolicyActivity.class));
//                }
//
//                @Override
//                public void onClose() {
//                    bottomSheetCancellationPolicy.dismiss();
//                }
//            }, houseDetailResponse1);
//            bottomSheetCancellationPolicy.show();
//            bottomSheetCancellationPolicy.setCanceledOnTouchOutside(false);
        });

        statusOrderViewModel.getDeleteOrderResponse().observe(this, new Observer<OrderStatusResponse>() {
            @Override
            public void onChanged(OrderStatusResponse orderStatusResponse) {
                if (orderStatusResponse.isMessege()) {
                    Intent intent = new Intent(StatusBillActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(CheckSuccess, deleteOrderResponse);
                    startActivity(intent);
                } else {

                }
            }
        });

    }

    private void initData() {
        statusOrderViewModel.getStatusBill(idBill);
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.toolBar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            binding.toolBar.setTitleTextColor(Color.WHITE);

            binding.contentBackground1.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.contentPayDayNight.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.contentEditPerson.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.contentBackground2.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.contentPayment.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.contentPhone.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.contentBackground3.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
//            binding.contentCancellationPolicy.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
//            binding.contentBackground4.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));

            binding.nameHouse.setTextColor(Color.WHITE);
            binding.startDate.setTextColor(Color.WHITE);
            binding.endDate.setTextColor(Color.WHITE);
            binding.personLimitHouse.setTextColor(Color.WHITE);
            binding.person.setTextColor(Color.WHITE);
            binding.text1.setTextColor(Color.WHITE);
            binding.text2.setTextColor(Color.WHITE);
            binding.text4.setTextColor(Color.WHITE);
            binding.text6.setTextColor(Color.WHITE);
            binding.text7.setTextColor(Color.WHITE);
            binding.textPayment.setTextColor(Color.WHITE);
            binding.phone.setTextColor(Color.WHITE);
            binding.priceAndCount.setTextColor(Color.WHITE);
            binding.sumPrice.setTextColor(Color.WHITE);
            binding.priceAll.setTextColor(Color.WHITE);

            binding.edPhone.setBackgroundResource(R.drawable.textview_border_ver2_dark);
            binding.edPhone.setTextColor(Color.WHITE);
            binding.edPhone.setHintTextColor(Color.WHITE);
            binding.textConfirm.setTextColor(Color.WHITE);

            binding.btnComfirmPhone.setBackgroundResource(R.drawable.textview_border_ver2_dark);
            binding.btnComfirmPhone.setTextColor(Color.WHITE);
        } else {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.color_EBEBEB));
            binding.toolBar.setBackgroundColor(Color.WHITE);
            binding.toolBar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

            binding.contentBackground1.setBackgroundColor(Color.WHITE);
            binding.contentPayDayNight.setBackgroundColor(Color.WHITE);
            binding.contentEditPerson.setBackgroundColor(Color.WHITE);
            binding.contentBackground2.setBackgroundColor(Color.WHITE);
            binding.contentPayment.setBackgroundColor(Color.WHITE);
            binding.contentPhone.setBackgroundColor(Color.WHITE);
            binding.contentBackground3.setBackgroundColor(Color.WHITE);

        }
    }
}