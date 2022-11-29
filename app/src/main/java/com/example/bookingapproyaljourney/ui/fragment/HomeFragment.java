package com.example.bookingapproyaljourney.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.UpdateRecyclerView;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;
import com.example.bookingapproyaljourney.ui.activity.SeeMoreBestForYouActivity;
import com.example.bookingapproyaljourney.ui.activity.SeeMoreNearFromYouActivity;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapterNotNull;
import com.example.bookingapproyaljourney.ui.adapter.CategoryHouseAdapter;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapter;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetFilterHome;
import com.example.bookingapproyaljourney.view_model.CategoryViewModel;
import com.example.bookingapproyaljourney.view_model.FilterViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements UpdateRecyclerView, BestForYouAdapter.Listernaer, BestForYouAdapterNotNull.Listernaer, BottomSheetFilterHome.EventClick {
    private ResultReceiver resultReceiver;

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
    private TextView seeMoreBestForYou;
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
        tvShowNull = (TextView) view.findViewById(R.id.tvShowNull);
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
        bestForYouAdapter = new BestForYouAdapter(this);
        bestForYouAdapterNotNull = new BestForYouAdapterNotNull(this);

        nearFromYouAdapter = new NearFromYouAdapter(new NearFromYouAdapter.Listerner() {
            @Override
            public void onClick(House house) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                intent.putExtra(AppConstant.HOUSE_EXTRA, house.getId());
                startActivity(intent);
            }
        });
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
        locations.add("Hà Nội");
        locations.add("Hải Phòng");
        locations.add("Thái Bình ");
        locations.add("Hưng Yên");
        locations.add("Tp.Hồ Chí Minh");
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
            nearFromYouAdapter.setDataHouse(houseNearestByUserResponse.getDataMaps());
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
            bestForYouAdapter.setDataHouse(categoryBestForYouResponse.getHouses());
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
    public void onClickListChinh(House house) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra(AppConstant.HOUSE_EXTRA, house.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(House house) {
        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
        intent.putExtra(AppConstant.HOUSE_EXTRA, house.getId());
        startActivity(intent);
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
            if (it.getHouses().size() == 0) {
                progressBar.setVisibility(View.GONE);
                tvShowNull.setVisibility(View.VISIBLE);
                tvContentNull.setVisibility(View.VISIBLE);
                viewShowNull.setVisibility(View.VISIBLE);
                tvContentNull2.setVisibility(View.VISIBLE);
                btnShowNull.setVisibility(View.VISIBLE);
                btnShowNull.setText("Xóa bộ lọc");
                recyclerviewListBestForYou.setVisibility(View.GONE);
            } else {
                recyclerviewListBestForYou.setVisibility(View.VISIBLE);
                bestForYouAdapterNotNull.setDataHouse(it.getHouses());
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
            if (it.getHouses().size() == 0) {
                progressBar.setVisibility(View.GONE);
                tvShowNull.setVisibility(View.VISIBLE);
                tvContentNull.setVisibility(View.VISIBLE);
                viewShowNull.setVisibility(View.VISIBLE);
                tvContentNull2.setVisibility(View.VISIBLE);
                btnShowNull.setVisibility(View.VISIBLE);
                btnShowNull.setText("Xóa tìm kiếm");
                tvContentNull.setText("Hãy thử thay đổi địa chỉ tìm kiếm và tìm lại nhé");
                recyclerviewListBestForYou.setVisibility(View.GONE);
            } else {
                recyclerviewListBestForYou.setVisibility(View.VISIBLE);
                bestForYouAdapterNotNull.setDataHouse(it.getHouses());
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
}