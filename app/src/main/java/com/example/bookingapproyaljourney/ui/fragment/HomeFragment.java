package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.UpdateRecyclerView;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;
import com.example.bookingapproyaljourney.ui.activity.Hotel.HotelActivity;
import com.example.bookingapproyaljourney.ui.activity.SeeMoreBestForYouActivity;
import com.example.bookingapproyaljourney.ui.activity.SeeMoreNearFromYouActivity;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapterNotNull;
import com.example.bookingapproyaljourney.ui.adapter.CategoryHouseAdapter;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetFilterHome;
import com.example.bookingapproyaljourney.ui.custom.LighterHelper;
import com.example.bookingapproyaljourney.view_model.CategoryViewModel;
import com.example.bookingapproyaljourney.view_model.FilterViewModel;
import com.example.hightlight.Lighter;
import com.example.hightlight.interfaces.OnLighterListener;
import com.example.hightlight.parameter.Direction;
import com.example.hightlight.parameter.LighterParameter;
import com.example.hightlight.parameter.MarginOffset;
import com.example.hightlight.shape.CircleShape;
import com.example.hightlight.shape.RectShape;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements UpdateRecyclerView, BestForYouAdapterNotNull.Listernaer, BottomSheetFilterHome.EventClick {
    private ResultReceiver resultReceiver;
    private View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LottieAnimationView progressBar;
    private Spinner listLocation;
    private ImageView bell;
    private ImageView dotCheck;
    private EditText etSearch;
    private ImageButton btnFilter;
    private RecyclerView listCategory;
    private RelativeLayout contentBestForYou;
    private TextView seeMoreNearFromYou;
    private RecyclerView recyclerviewNearFromYou;
    private TextView seeMoreBestForYou, titleBestYou, titleNearBy;
    private RecyclerView recyclerviewListBestForYou;
    // TODO: Rename and change types of parameters
    private TextView tvShowNull;
    private View viewShowNull;
    private TextView tvContentNull;
    private TextView tvContentNull2;
    private TextView btnShowNull;

    private String mParam1;
    private String mParam2;
    private List<String> locations = new ArrayList<>();
    private List<Category> dataCategory;
    private List<House> dataHouse;
    private CategoryHouseAdapter categoryHouseAdapter;
    private NearFromYouAdapter nearFromYouAdapter;
    private BestForYouAdapter bestForYouAdapter;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private CategoryViewModel categoryViewModel;
    private android.location.Location locationYouSelf;
    private RelativeLayout contentTextNearFromYou;
    private RelativeLayout contentBestForYouHomeFragment;
    private LatLng currentUserLocation;
    private BestForYouAdapterNotNull bestForYouAdapterNotNull;
    private FilterViewModel filterViewModel;
    private ConstraintLayout backgroundContent;

    public HomeFragment(Location locationYouSelf) {
        this.locationYouSelf = locationYouSelf;
    }

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();

        Log.e("MinhIDUser", UserClient.getInstance().getId() + "dsfdsfd");
    }

    private void initView(View view) {
        backgroundContent = (ConstraintLayout) view.findViewById(R.id.backgroundContent);

        tvShowNull = (TextView) view.findViewById(R.id.tvShowNull);
        titleNearBy = (TextView) view.findViewById(R.id.titleNearBy);
        titleBestYou = (TextView) view.findViewById(R.id.titleBestYou);
        viewShowNull = (View) view.findViewById(R.id.viewShowNull);
        tvContentNull = (TextView) view.findViewById(R.id.tvContentNull);
        tvContentNull2 = (TextView) view.findViewById(R.id.tvContentNull2);
        btnShowNull = (TextView) view.findViewById(R.id.btnShowNull);

        listLocation = (Spinner) view.findViewById(R.id.listLocationHomFragment);
        bell = (ImageView) view.findViewById(R.id.bellMain);
        dotCheck = (ImageView) view.findViewById(R.id.dotCheck);
        etSearch = (EditText) view.findViewById(R.id.etSearchHomeFragment);
        btnFilter = (ImageButton) view.findViewById(R.id.btnFilterHomeFragment);
        listCategory = (RecyclerView) view.findViewById(R.id.listCategoryHomeFragment);
        seeMoreNearFromYou = (TextView) view.findViewById(R.id.seeMoreNearFromYouHomeFragment);
        recyclerviewNearFromYou = (RecyclerView) view.findViewById(R.id.recyclerviewNearFromYouHomeFragment);
        seeMoreBestForYou = (TextView) view.findViewById(R.id.seeMoreBestForYouHomeFragment);
        recyclerviewListBestForYou = (RecyclerView) view.findViewById(R.id.recyclerviewBestForYouHomeFragment);
        progressBar = (LottieAnimationView) view.findViewById(R.id.progressBar);
        contentTextNearFromYou = (RelativeLayout) view.findViewById(R.id.contentTextNearFromYou);
        contentBestForYouHomeFragment = (RelativeLayout) view.findViewById(R.id.contentBestForYouHomeFragment);

        recyclerviewListBestForYou.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerviewListBestForYou.setNestedScrollingEnabled(false);
        recyclerviewNearFromYou.setNestedScrollingEnabled(false);
        filterViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        categoryViewModel = new ViewModelProvider(getActivity()).get(CategoryViewModel.class);
//        bestForYouAdapter = new BestForYouAdapter(this);
        bestForYouAdapterNotNull = new BestForYouAdapterNotNull();
        bestForYouAdapterNotNull.setListernaer(this);

//        nearFromYouAdapter = new NearFromYouAdapter(new NearFromYouAdapter.Listerner() {
//            @Override
//            public void onClick(House house) {
//                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
//                intent.putExtra(AppConstant.HOUSE_EXTRA, house.getId());
//                startActivity(intent);
//            }
//        });

        SharedPreferences sharedPreferencesTheme = getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        btnFilter.setOnClickListener(v -> {
            showDialog();
        });

        seeMoreBestForYou.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SeeMoreBestForYouActivity.class);
            startActivity(i);
        });

        seeMoreNearFromYou.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SeeMoreNearFromYouActivity.class);
            startActivity(i);
        });
        btnShowNull.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (textView.getText().toString().isEmpty() || textView.getText().toString().trim().equals("")) {
                        in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                        etSearch.setText("");
                        return false;
                    }
                    in.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                    search(textView.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        locations.add(getString(R.string.Hanoi));
        locations.add(getString(R.string.Hai_Phong));
        locations.add(getString(R.string.Thai_binh));
        locations.add(getString(R.string.Hung_yen));
        locations.add(getString(R.string.Tp_Ho_Chi_Minh));
        ArrayAdapter listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, locations);
        listLocation.setAdapter(listAdapter);
        getListCategory();
    }

    private void getListCategory() {
        categoryViewModel.getListCategory().observe(getActivity(), items -> {
            categoryHouseAdapter = new CategoryHouseAdapter(this, items, locationYouSelf);
            listCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            listCategory.setAdapter(categoryHouseAdapter);
        });
    }

    @Override
    public void callbacksNearFromYou(int position, HouseNearestByUserResponse houseNearestByUserResponse) {
        if (houseNearestByUserResponse.getDataMaps().size() == 0) {
            contentTextNearFromYou.setVisibility(View.GONE);
            recyclerviewNearFromYou.setVisibility(View.GONE);
        } else {
            contentBestForYouHomeFragment.setVisibility(View.VISIBLE);
            contentTextNearFromYou.setVisibility(View.VISIBLE);
            recyclerviewNearFromYou.setVisibility(View.VISIBLE);
//            nearFromYouAdapter.setDataHouse(houseNearestByUserResponse.getDataMaps());
            recyclerviewNearFromYou.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerviewNearFromYou.setAdapter(nearFromYouAdapter);
        }
    }

    @Override
    public void callbacksBestForYou(int position, CategoryBestForYouResponse categoryBestForYouResponse) {
        if (contentTextNearFromYou.getVisibility() == View.GONE) {
            bestForYouAdapterNotNull.setDataHouse(categoryBestForYouResponse.getHouses());
            recyclerviewListBestForYou.setAdapter(bestForYouAdapterNotNull);
        }
        if (contentTextNearFromYou.getVisibility() == View.VISIBLE) {
//            bestForYouAdapter.setDataHouse(categoryBestForYouResponse.getHouses());
            recyclerviewListBestForYou.setAdapter(bestForYouAdapter);
        }
    }

    @Override
    public void callLoading(int view) {
        progressBar.setVisibility(view);
    }

    @Override
    public void callBackNull(CategoryBestForYouResponse categoryBestForYouResponse) {
        contentTextNearFromYou.setVisibility(View.GONE);
        recyclerviewNearFromYou.setVisibility(View.GONE);
        contentBestForYouHomeFragment.setVisibility(View.VISIBLE);
        bestForYouAdapterNotNull.setDataHouse(categoryBestForYouResponse.getHouses());
        recyclerviewListBestForYou.setAdapter(bestForYouAdapterNotNull);
    }

    @Override
    public void callBackVIew(View view) {
        this.view = view;
    }



    private void showDialog() {
        BottomSheetFilterHome bottomSheetFilterHome = new BottomSheetFilterHome(requireContext(), R.style.MaterialDialogSheet, this);
        bottomSheetFilterHome.show();
        bottomSheetFilterHome.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onCLickFilter(String giaBd, String giaKt, String sao, String idLoai) {
        progressBar.setVisibility(View.VISIBLE);
        contentTextNearFromYou.setVisibility(View.GONE);
        contentBestForYouHomeFragment.setVisibility(View.GONE);
        listCategory.setVisibility(View.GONE);
        recyclerviewNearFromYou.setVisibility(View.GONE);
        filterViewModel.filterLiveData(giaBd, giaKt, sao, idLoai).observe(getActivity(), it -> {
            if (it.getHotel().size() == 0) {
                progressBar.setVisibility(View.GONE);
                tvShowNull.setVisibility(View.VISIBLE);
                tvContentNull.setVisibility(View.VISIBLE);
                viewShowNull.setVisibility(View.VISIBLE);
                tvContentNull2.setVisibility(View.VISIBLE);
                btnShowNull.setVisibility(View.VISIBLE);
                btnShowNull.setText(R.string.Clean_the_filter);
                recyclerviewListBestForYou.setVisibility(View.GONE);
            } else {
                recyclerviewListBestForYou.setVisibility(View.VISIBLE);
                bestForYouAdapterNotNull.setDataHouse(it.getHotel());
                recyclerviewListBestForYou.setAdapter(bestForYouAdapterNotNull);
                progressBar.setVisibility(View.GONE);
                tvShowNull.setVisibility(View.GONE);
                tvContentNull.setVisibility(View.GONE);
                viewShowNull.setVisibility(View.GONE);
                tvContentNull2.setVisibility(View.GONE);
                btnShowNull.setVisibility(View.GONE);
            }
        });

    }

    void search(String tk) {
        progressBar.setVisibility(View.VISIBLE);
        contentTextNearFromYou.setVisibility(View.GONE);
        contentBestForYouHomeFragment.setVisibility(View.GONE);
        recyclerviewNearFromYou.setVisibility(View.GONE);
        listCategory.setVisibility(View.GONE);
        filterViewModel.listSearchLiveData(tk).observe(getActivity(), it -> {
            if (it.getHotel().size() == 0) {
                progressBar.setVisibility(View.GONE);
                tvShowNull.setVisibility(View.VISIBLE);
                tvContentNull.setVisibility(View.VISIBLE);
                viewShowNull.setVisibility(View.VISIBLE);
                tvContentNull2.setVisibility(View.VISIBLE);
                btnShowNull.setVisibility(View.VISIBLE);
                btnShowNull.setText(R.string.delete_search);
                tvContentNull.setText(R.string.Change_Location);
                recyclerviewListBestForYou.setVisibility(View.GONE);
            } else {
                recyclerviewListBestForYou.setVisibility(View.VISIBLE);
                bestForYouAdapterNotNull.setDataHouse(it.getHotel());
                recyclerviewListBestForYou.setAdapter(bestForYouAdapterNotNull);
                progressBar.setVisibility(View.GONE);
                tvShowNull.setVisibility(View.GONE);
                tvContentNull.setVisibility(View.GONE);
                viewShowNull.setVisibility(View.GONE);
                tvContentNull2.setVisibility(View.GONE);
                btnShowNull.setVisibility(View.GONE);
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(com.example.bookingapproyaljourney.event.KeyEvent event) {
        if (event.getIdEven() == AppConstant.SAVE_THEME_DARK) {
            changeTheme(1);
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_LIGHT) {
            changeTheme(2);
        } else if (event.getIdEven() == AppConstant.BY_USER_VER2) {
            showGuide();
            return;
        }
    }

    @SuppressLint("RestrictedApi")
    private void showGuide() {
        TranslateAnimation translateAnimation = new TranslateAnimation(-500, 0, 0, 0);
        translateAnimation.setDuration(500);
        translateAnimation.setInterpolator(new BounceInterpolator());

        CircleShape circleShape = new CircleShape(25);
        circleShape.setPaint(LighterHelper.getDashPaint());


        RectShape rectShape = new RectShape();
        rectShape.setPaint(LighterHelper.getDiscretePaint());

        Lighter.with(getActivity())
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {

                    }

                    @Override
                    public void onDismiss() {
                        com.example.bookingapproyaljourney.event.KeyEvent locationReceivedStickyEvent = EventBus.getDefault().getStickyEvent(com.example.bookingapproyaljourney.event.KeyEvent.class);
                        EventBus.getDefault().removeStickyEvent(locationReceivedStickyEvent);
                    }
                })
                .setBackgroundColor(0xB3000000)
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.listCategoryHomeFragment)
                        .setTipView(LighterHelper.createCommonTipView(getActivity(), R.drawable.ic_vector_hand_controler, getString(R.string.Types_that_you_can_choose_from)))
                        .setLighterShape(new RectShape(0, 0, 25))
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewDisplayAnimation(LighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(0, 20, 0, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.recyclerviewNearFromYouHomeFragment)
                        .setTipView(LighterHelper.createCommonTipView(getActivity(), R.drawable.ic_vector_hand_controler, getString(R.string.List_of_rooms_closest_to_you)))
                        .setLighterShape(new RectShape(0, 0, 25))
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewDisplayAnimation(LighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(0, 20, 0, 0))
                        .build())

                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.recyclerviewBestForYouHomeFragment)
                        .setTipView(LighterHelper.createCommonTipView2(getActivity(), R.drawable.ic_vector_hand_controller_ver2, getString(R.string.List_of_the_best_rooms_included_in_terms_of_price_and_service)))
                        .setLighterShape(new RectShape(0, 0, 25))
                        .setTipViewRelativeDirection(Direction.TOP)
                        .setTipViewDisplayAnimation(LighterHelper.getScaleAnimation())
                        .setTipViewRelativeOffset(new MarginOffset(0, 20, 0, 0))
                        .build())
                .addHighlight(new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.recyclerviewNearFromYouHomeFragment)
                                .setTipView(LighterHelper.createCommonTipView2(getActivity(), R.drawable.ic_vector_hand_controller_ver2, getString(R.string.Now_choose_your_trip)))
                                .setLighterShape(new RectShape(0, 0, 25))
                                .setTipViewRelativeDirection(Direction.TOP)
                                .setTipViewDisplayAnimation(LighterHelper.getScaleAnimation())
                                .setTipViewRelativeOffset(new MarginOffset(100, 10, 0, 20))
                                .build(),
                        new LighterParameter.Builder()
                                .setHighlightedViewId(R.id.recyclerviewBestForYouHomeFragment)
                                .setTipView(LighterHelper.createCommonTipView2(getActivity(), R.drawable.ic_vector_hand_controller_ver2, getString(R.string.Now_choose_your_trip)))
                                .setLighterShape(new RectShape(0, 0, 25))
                                .setTipViewRelativeDirection(Direction.BOTTOM)
                                .setTipViewDisplayAnimation(LighterHelper.getScaleAnimation())
                                .setTipViewRelativeOffset(new MarginOffset(300, 0, 0, 0))
                                .build())
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            backgroundContent.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            etSearch.setBackgroundResource(R.drawable.framesearch_homefragment_dark);

            etSearch.setTextColor(Color.WHITE);
            titleNearBy.setTextColor(Color.WHITE);
            titleBestYou.setTextColor(Color.WHITE);

            bestForYouAdapter.setColor(Color.WHITE, Color.WHITE);
            bestForYouAdapterNotNull.setColor(Color.WHITE, Color.WHITE);
        } else {
            backgroundContent.setBackgroundColor((this.getResources().getColor(R.color.white)));
            etSearch.setBackgroundResource(R.drawable.framesearch_homefragment);

            etSearch.setTextColor(Color.BLACK);
            titleNearBy.setTextColor(Color.BLACK);
            titleBestYou.setTextColor(Color.BLACK);

            bestForYouAdapterNotNull.setColor(Color.BLACK, getContext().getResources().getColor(R.color.color_858585));
        }
    }

    @Override
    public void onClick(Hotel hotel) {
        Intent intent = new Intent(getActivity(), HotelActivity.class);
        intent.putExtra(AppConstant.HOUSE_EXTRA, hotel.get_id());
        startActivity(intent);
    }
}