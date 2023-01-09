package com.example.bookingapproyaljourney.ui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.databinding.FragmentHomeVer2Binding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.ui.adapter.BestForYouAdapter;
import com.example.bookingapproyaljourney.ui.adapter.NearFromYouAdapter;
import com.example.bookingapproyaljourney.view_model.HomeViewModel;


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

    public HomeVer2Fragment(Location locationYouSelf) {
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
        binding = FragmentHomeVer2Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        bestForYouAdapter = new BestForYouAdapter(1, o -> {
            if (o instanceof Hotel) {

            }
        });

        binding.recyclerviewBestForYouHomeFragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerviewNearFromYouHomeFragment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

    }

    private void initData() {
        homeViewModel.getHotelReponseMutableLiveData().observe(getActivity(), new Observer<HotelReponse>() {
            @Override
            public void onChanged(HotelReponse hotelReponse) {
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
                        binding.recyclerviewNearFromYouHomeFragment.setAdapter(nearFromYouAdapter);
                        binding.contentCenter.setVisibility(View.VISIBLE);
                    } else {
                        binding.contentCenter.setVisibility(View.GONE);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.getListAllHotel();
        if (locationYouSelf != null) {
            homeViewModel.getListHotelNearBy(new LocationNearByRequest(locationYouSelf.getLongitude(), locationYouSelf.getLatitude(), 1000));
        }
    }
}