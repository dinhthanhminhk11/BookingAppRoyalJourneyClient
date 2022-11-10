package com.example.bookingapproyaljourney.ui.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.bookingapproyaljourney.view_model.CategoryViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements UpdateRecyclerView, BestForYouAdapter.Listernaer, BestForYouAdapterNotNull.Listernaer {
    private ResultReceiver resultReceiver;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LottieAnimationView progressBar;
    private Spinner listLocation;
    private ImageView bell;
    private EditText etSearch;
    private ImageButton btnFilter;
    private RecyclerView listCategory;
    private RelativeLayout contentBestForYou;
    private TextView seeMoreNearFromYou;
    private RecyclerView recyclerviewNearFromYou;
    private TextView seeMoreBestForYou;
    private RecyclerView recyclerviewListBestForYou;
    // TODO: Rename and change types of parameters
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

        Log.e("MinhIDUser" , UserClient.getInstance().getId() + "dsfdsfd");
    }

    private void initView(View view) {
        listLocation = (Spinner) view.findViewById(R.id.listLocationHomFragment);
        bell = (ImageView) view.findViewById(R.id.bellMain);
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

        seeMoreBestForYou.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SeeMoreBestForYouActivity.class);
            startActivity(i);
        });

        seeMoreNearFromYou.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SeeMoreNearFromYouActivity.class);
            startActivity(i);
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
}