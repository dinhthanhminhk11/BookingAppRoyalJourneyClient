package com.example.bookingapproyaljourney.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.house.Location;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.CategoryHouseAdapter;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapter;
import com.example.bookingapproyaljourney.view_model.CategoryViewModel;
import com.example.librarynav.SlidingRootNav;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements CategoryHouseAdapter.UpdateRecyclerView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    private CategoryViewModel categoryViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        listLocation = (Spinner) view.findViewById(R.id.listLocationHomFragment);
        bell = (ImageView) view.findViewById(R.id.bellMain);
        etSearch = (EditText) view.findViewById(R.id.etSearchHomeFragment);
        btnFilter = (ImageButton) view.findViewById(R.id.btnFilterHomeFragment);
        listCategory = (RecyclerView) view.findViewById(R.id.listCategoryHomeFragment);
        contentBestForYou = (RelativeLayout) view.findViewById(R.id.contentBestForYouHomeFragment);
        seeMoreNearFromYou = (TextView) view.findViewById(R.id.seeMoreNearFromYouHomeFragment);
        recyclerviewNearFromYou = (RecyclerView) view.findViewById(R.id.recyclerviewNearFromYouHomeFragment);
        seeMoreBestForYou = (TextView) view.findViewById(R.id.seeMoreBestForYouHomeFragment);
        recyclerviewListBestForYou = (RecyclerView) view.findViewById(R.id.recyclerviewBestForYouHomeFragment);

        categoryViewModel = new ViewModelProvider(getActivity()).get(CategoryViewModel.class);


    }

    private void initData() {
//        fake data Location
        locations.add("Hà Nội");
        locations.add("Hải Phòng");
        locations.add("Thái Bình ");
        locations.add("Hưng Yên");
        locations.add("Tp.Hồ Chí Minh");
        ArrayAdapter listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, locations);
        listLocation.setAdapter(listAdapter);

        getListCategory();

//      fake data NearFromYou
        dataHouse = new ArrayList<>();
        dataHouse.add(new House(1, "Hanoi Prime Center", "Ha Noi", 1, "1.5", 15000, 2, 2, 5));
        dataHouse.add(new House(2, "Haiphong Prime Center", "Hai Phong", 2, "2.7", 13000, 1, 2, 4));
        dataHouse.add(new House(3, "Thaibinh Prime Center", "Thai Binh", 3, "3", 10000, 2, 1, 3));
        dataHouse.add(new House(4, "HCM Prime Center", "Tp.Ho Chi Minh", 4, "4.5", 17000, 2, 2, 2));
        dataHouse.add(new House(5, "Hungyen Prime Center", "Hung Yen", 5, "4", 12000, 1, 1, 1));
        nearFromYouAdapter = new NearFromYouAdapter(dataHouse, new NearFromYouAdapter.Listerner() {
            @Override
            public void onClick(View v, int position) {
                startActivity(new Intent(getActivity(), DetailProductActivity.class));
            }
        });
        recyclerviewNearFromYou.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerviewNearFromYou.setAdapter(nearFromYouAdapter);
//      fake data BestForYou
        bestForYouAdapter = new BestForYouAdapter(dataHouse);
        recyclerviewListBestForYou.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerviewListBestForYou.setAdapter(bestForYouAdapter);


    }

    private void getListCategory() {
        categoryViewModel.getListCategory().observe(getActivity(), items -> {
            categoryHouseAdapter = new CategoryHouseAdapter(this, items);
            listCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            listCategory.setAdapter(categoryHouseAdapter);
        });
    }

    @Override
    public void callbacksNearFromYou(int position, List<House> list) {

    }

    @Override
    public void callbacksBestForYou(int position, List<House> list) {

    }

}