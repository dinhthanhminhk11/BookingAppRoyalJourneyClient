package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;

public class DetailProductViewModel extends AndroidViewModel {

    private DetailProductRepository detailProductRepository;

    public DetailProductViewModel(@NonNull Application application) {
        super(application);
        detailProductRepository = new DetailProductRepository();
    }

    public LiveData<HouseDetailResponse> getHouseById(String id) {
        return detailProductRepository.getCategory(id);
    }
}
