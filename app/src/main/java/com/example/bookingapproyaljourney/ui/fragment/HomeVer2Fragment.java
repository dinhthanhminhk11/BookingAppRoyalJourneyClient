package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bookingapproyaljourney.ui.activity.Hotel.SearchHotelActivity.nameLocation;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentHomeVer2Binding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.ui.activity.Hotel.HotelActivity;
import com.example.bookingapproyaljourney.ui.activity.Hotel.ListFilterHotelActivity;
import com.example.bookingapproyaljourney.ui.activity.Hotel.SearchHotelActivity;
import com.example.bookingapproyaljourney.ui.activity.NearFromYouMapsActivity;
import com.example.bookingapproyaljourney.ui.activity.SeeMoreBestForYouActivity;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPersonHome;
import com.example.bookingapproyaljourney.view_model.HomeViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class HomeVer2Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FragmentHomeVer2Binding binding;
    private Location locationYouSelf;
    private HomeViewModel homeViewModel;
    private BestForYouAdapter bestForYouAdapter;
    private NearFromYouAdapter nearFromYouAdapter;
    private String checkStartDate;
    private String checkEndDate;

    private long daysDiff = 1;
    private int countRoom = 2;
    private int countPerson = 2;
    private int countChildren = 2;
    private int ageChildren = 1;
    private TextView contentText;
    private Button login;
    private BottomSheetPersonHome bottomSheetPersonHome;
    private Date currentTimeNow;
    private Date currentTimeTomorrow;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public HomeVer2Fragment(Location locationYouSelf) {
        this.locationYouSelf = locationYouSelf;
    }

    private long daysDiffPrivate = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeVer2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
//        change theme
        SharedPreferences sharedPreferencesTheme = getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.layoutFrgHome2.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.contentTop.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.contentPerson.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imgSearch.setColorFilter(getResources().getColor(R.color.white));
            binding.direct.setColorFilter(getResources().getColor(R.color.white));
            binding.startDate.setTextColor(Color.WHITE);
            binding.textSearch.setTextColor(Color.WHITE);
            binding.monthDate.setTextColor(Color.WHITE);
            binding.tvTimeNhanPhong.setTextColor(Color.WHITE);
            binding.endDate.setTextColor(Color.WHITE);
            binding.monthEnd.setTextColor(Color.WHITE);
            binding.dayEnd.setTextColor(Color.WHITE);
            binding.contentPayOfflineLine2.setBackgroundColor(Color.WHITE);
            binding.contentPayOfflineLine.setBackgroundColor(Color.WHITE);
            binding.imgOutPeople.setColorFilter(getResources().getColor(R.color.white));
            binding.countRoom.setTextColor(Color.WHITE);
            binding.dot1.setTextColor(Color.WHITE);
            binding.countPerson.setTextColor(Color.WHITE);
            binding.dot2.setTextColor(Color.WHITE);
            binding.countChildren.setTextColor(Color.WHITE);
            binding.contentCenter.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.contentTextNearFromYou.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.titleNearBy.setTextColor(Color.WHITE);
            binding.seeMoreNearFromYouHomeFragment.setTextColor(Color.WHITE);
            binding.contentBottom.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.contentBestForYouHomeFragment.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.titleBestYou.setTextColor(Color.WHITE);
            binding.seeMoreBestForYouHomeFragment.setTextColor(Color.WHITE);
            bestForYouAdapter.setColor(Color.WHITE, this.getResources().getColor(R.color._0A8ED9));

        } else {
            binding.layoutFrgHome2.setBackgroundColor(this.getResources().getColor(R.color.color_EBEBEB));
            binding.contentTop.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.contentPerson.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.imgSearch.setColorFilter(getResources().getColor(R.color.black));
            binding.direct.setColorFilter(getResources().getColor(R.color.black));
            binding.startDate.setTextColor(Color.BLACK);
            binding.textSearch.setTextColor(Color.BLACK);
            binding.monthDate.setTextColor(Color.BLACK);
            binding.tvTimeNhanPhong.setTextColor(Color.BLACK);
            binding.endDate.setTextColor(Color.BLACK);
            binding.monthEnd.setTextColor(Color.BLACK);
            binding.dayEnd.setTextColor(Color.BLACK);
            binding.contentPayOfflineLine2.setBackgroundColor(Color.BLACK);
            binding.contentPayOfflineLine.setBackgroundColor(Color.BLACK);
            binding.imgOutPeople.setColorFilter(getResources().getColor(R.color.black));
            binding.countRoom.setTextColor(Color.BLACK);
            binding.dot1.setTextColor(Color.BLACK);
            binding.countPerson.setTextColor(Color.BLACK);
            binding.dot2.setTextColor(Color.BLACK);
            binding.countChildren.setTextColor(Color.BLACK);
            binding.contentCenter.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.contentTextNearFromYou.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.titleNearBy.setTextColor(Color.BLACK);
            binding.seeMoreNearFromYouHomeFragment.setTextColor(Color.BLACK);
            binding.contentBottom.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.contentBestForYouHomeFragment.setBackgroundColor(this.getResources().getColor(R.color.white));
            binding.titleBestYou.setTextColor(Color.BLACK);
            binding.seeMoreBestForYouHomeFragment.setTextColor(Color.BLACK);
            bestForYouAdapter.setColor(Color.BLACK, this.getResources().getColor(R.color._0A8ED9));
        }

    }

    private void initView() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        builder.setCalendarConstraints(constraintsBuilder.build());
        builder.setTheme(R.style.ThemeOverlay_App_DatePicker);
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.setTitleText(getActivity().getString(R.string.Select_a_date)).setPositiveButtonText(getActivity().getString(R.string.SAVE)).setNegativeButtonText(getActivity().getString(R.string.Thoat)).setSelection(new Pair<>(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).setCalendarConstraints(constraintBuilder.build()).build();

        bestForYouAdapter = new BestForYouAdapter(o -> {
            if (o instanceof Hotel) {
                Intent intent = new Intent(getActivity(), HotelActivity.class);
                intent.putExtra(AppConstant.HOTEL_EXTRA, o.get_id());
                startActivity(intent);
            }
        });

        binding.recyclerviewBestForYouHomeFragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerviewNearFromYouHomeFragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding.contentSearch.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchHotelActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        });

        binding.direct.setOnClickListener(v -> {
            binding.textSearch.setText(getString(R.string.Nearest_Hotels));
        });

        binding.contentDate.setOnClickListener(v -> {
            materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");

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

                    String startDayString = DateFormat.format("EEE", new Date(startDate)).toString();
                    String endDayString = DateFormat.format("EEE", new Date(endDate)).toString();

                    String startDateString = DateFormat.format("dd", new Date(startDate)).toString();
                    String endDateString = DateFormat.format("dd", new Date(endDate)).toString();


                    String startMonthString = DateFormat.format("MM", new Date(startDate)).toString();
                    String endMonthString = DateFormat.format("MM", new Date(endDate)).toString();

                    checkStartDate = DateFormat.format("dd/MM/yyyy", new Date(startDate)).toString();
                    checkEndDate = DateFormat.format("dd/MM/yyyy", new Date(endDate)).toString();


                    long msDiff = endDate - startDate;

                    daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                    binding.payDay.setText(daysDiff + "");
                    daysDiffPrivate = daysDiff;
                    binding.startDate.setText(startDateString);
                    binding.endDate.setText(endDateString);

                    binding.monthDate.setText(getString(R.string.Month) + " " + startMonthString);
                    binding.monthEnd.setText(getString(R.string.Month) + " " + endMonthString);

                    binding.tvTimeNhanPhong.setText(startDayString);
                    binding.dayEnd.setText(endDayString);
                }
            });
        });

        binding.contentPerson.setOnClickListener(v -> {
            showDiaLogEditPerson();
        });

        binding.seeMoreBestForYouHomeFragment.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SeeMoreBestForYouActivity.class);
            startActivity(i);
        });

        binding.seeMoreNearFromYouHomeFragment.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), NearFromYouMapsActivity.class);
            startActivity(i);
        });

        binding.btnSearch.setOnClickListener(v -> {
            saveDataSearch();
            Intent intent = new Intent(getActivity(), ListFilterHotelActivity.class);
            intent.putExtra(AppConstant.LOCATION_YOUR_SELF_LONG, String.valueOf(locationYouSelf.getLongitude()));
            intent.putExtra(AppConstant.LOCATION_YOUR_SELF_LAT, String.valueOf(locationYouSelf.getLatitude()));
            startActivity(intent);
        });
    }

    private void initData() {


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        currentTimeNow = Calendar.getInstance().getTime();

        currentTimeTomorrow = calendar.getTime();

        binding.payDay.setText(daysDiffPrivate + "");

        String dayNow = DateFormat.format("EEE", currentTimeNow).toString();

        String dayTomorrow = DateFormat.format("EEE", currentTimeTomorrow).toString();

        String dateNow = DateFormat.format("dd", currentTimeNow).toString();
        String dateTomorrow = DateFormat.format("dd", currentTimeTomorrow).toString();


        String monthNow = DateFormat.format("MM", currentTimeNow).toString();
        String monthTomorrow = DateFormat.format("MM", currentTimeTomorrow).toString();

        binding.startDate.setText(dateNow + "");
        binding.monthDate.setText(getString(R.string.Month) + " " + monthNow);
        binding.tvTimeNhanPhong.setText(dayNow);

        binding.endDate.setText(dateTomorrow + "");
        binding.monthEnd.setText(getString(R.string.Month) + " " + monthTomorrow);
        binding.dayEnd.setText(dayTomorrow);

        SharedPreferences sharedPreferences_user_count_room = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, MODE_PRIVATE);
        countRoom = sharedPreferences_user_count_room.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, 2);

        SharedPreferences sharedPreferences_user_count_person = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, MODE_PRIVATE);
        countPerson = sharedPreferences_user_count_person.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, 2);

        SharedPreferences sharedPreferences_user_count_children = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, MODE_PRIVATE);
        countChildren = sharedPreferences_user_count_children.getInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, 2);

        SharedPreferences sharedPreferences_user_count_text_search = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH, MODE_PRIVATE);
        String textSearch = sharedPreferences_user_count_text_search.getString(AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH, "Khách sạn gần nhất");

        SharedPreferences sharedPreferences_user_age_children = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, MODE_PRIVATE);
        ageChildren = sharedPreferences_user_age_children.getInt(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, 1);

        binding.countRoom.setText(countRoom + " " + getString(R.string.Room));
        binding.countChildren.setText(countChildren + " " + getString(R.string.Children));
        binding.countPerson.setText(countPerson + " " + getString(R.string.Adult));
        binding.textSearch.setText(textSearch);

        Log.e("MinhDate", currentTimeNow + " now");
        Log.e("MinhDate", currentTimeTomorrow + " tomorrow");

        homeViewModel.getListAllHotel();
        homeViewModel.getHouseResponseByServer();
        if (locationYouSelf != null) {
            homeViewModel.getListHotelNearBy(new LocationNearByRequest(locationYouSelf.getLongitude(), locationYouSelf.getLatitude(), 10000));
        }

        homeViewModel.getHotelReponseMutableLiveData().observe(getActivity(), new Observer<HotelReponse>() {
            @Override
            public void onChanged(HotelReponse hotelReponse) {
                binding.contentBottom.setVisibility(View.VISIBLE);
                bestForYouAdapter.setDataHotel(hotelReponse.getDatapros());
                binding.recyclerviewBestForYouHomeFragment.setAdapter(bestForYouAdapter);
            }
        });

        homeViewModel.getHotelReponseMutableLiveDataNearBy().observe(getActivity(), new Observer<HotelReponseNearBy>() {
            @Override
            public void onChanged(HotelReponseNearBy hotelReponse) {
                if (hotelReponse instanceof HotelReponseNearBy) {
                    if (hotelReponse.getData().size() > 0) {
                        nearFromYouAdapter = new NearFromYouAdapter(hotelReponse.getData());
                        nearFromYouAdapter.setHotelConsumer(o -> {
                            Intent intent = new Intent(getActivity(), HotelActivity.class);
                            intent.putExtra(AppConstant.HOTEL_EXTRA, o.get_id());
                            startActivity(intent);
                        });
                        binding.recyclerviewNearFromYouHomeFragment.setAdapter(nearFromYouAdapter);
                        binding.contentCenter.setVisibility(View.VISIBLE);
                        bestForYouAdapter.setType(0);
                    } else {
                        binding.contentCenter.setVisibility(View.GONE);
                        bestForYouAdapter.setType(1);
                    }
                }
            }
        });
        homeViewModel.getmProgressMutableData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        homeViewModel.getStringMutableLiveData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    Date date = format.parse(s);
                    String checkDate1 = DateFormat.format("dd/MM/yyyy", currentTimeNow).toString();
                    String checkDate2 = DateFormat.format("dd/MM/yyyy", date).toString();
                    if (!checkDate1.equals(checkDate2)) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.dia_log_check_date);
                        Window window = dialog.getWindow();
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (dialog.getWindow() != null) {
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        }


                        contentText = (TextView) dialog.findViewById(R.id.contentText);
                        login = (Button) dialog.findViewById(R.id.login);
                        dialog.setCancelable(false);
                        login.setOnClickListener(v -> {
                        });
                        dialog.show();
//                        Toast.makeText(getActivity(), "Ngày đã bị đổi", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!nameLocation.equals("")) {
            binding.textSearch.setText(nameLocation);
        }
    }

    private void showDiaLogEditPerson() {
        bottomSheetPersonHome = new BottomSheetPersonHome(getActivity(), R.style.MaterialDialogSheet, new BottomSheetPersonHome.CallBack() {
            @Override
            public void onCLickCLose() {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCLickSum(int person, int children, int room, int age) {
                binding.countRoom.setText(room + " " + getString(R.string.Room));
                binding.countChildren.setText(children + " " + getString(R.string.Children));
                binding.countPerson.setText(person + " " + getString(R.string.Adult));

                countPerson = person;
                countChildren = children;
                countRoom = room;
                ageChildren = age;
            }
        });

        bottomSheetPersonHome.show();
        bottomSheetPersonHome.setCanceledOnTouchOutside(false);
    }

    private void saveDataSearch() {
        SharedPreferences sharedPreferences_user_count_room = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_count_room = sharedPreferences_user_count_room.edit();
        editor_user_count_room.putInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_ROOM, countRoom);
        editor_user_count_room.commit();

        SharedPreferences sharedPreferences_user_count_person = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_count_person = sharedPreferences_user_count_person.edit();
        editor_user_count_person.putInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_PERSON, countPerson);
        editor_user_count_person.commit();

        SharedPreferences sharedPreferences_user_count_children = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_count_children = sharedPreferences_user_count_children.edit();
        editor_user_count_children.putInt(AppConstant.SHAREDPREFERENCES_USER_COUNT_CHILDREN, countChildren);
        editor_user_count_children.commit();

        SharedPreferences sharedPreferences_user_count_text_search = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_count_text_search = sharedPreferences_user_count_text_search.edit();
        editor_user_count_text_search.putString(AppConstant.SHAREDPREFERENCES_USER_TEXT_SEARCH, binding.textSearch.getText().toString());
        editor_user_count_text_search.commit();

        SharedPreferences sharedPreferences_user_age_children = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, MODE_PRIVATE);
        SharedPreferences.Editor editor_user_age_children = sharedPreferences_user_age_children.edit();
        editor_user_age_children.putInt(AppConstant.SHAREDPREFERENCES_USER_AGE_CHILDREN, ageChildren);
        editor_user_age_children.commit();
    }
}