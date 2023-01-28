package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.repository.Repository;

public class HotelInfoViewModel extends AndroidViewModel {

    private Repository repository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<HotelById> hotelMutableLiveData = new MutableLiveData<>();


    public HotelInfoViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getHotelById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getHotelById(id, o -> {
            if (o instanceof HotelById) {
                mProgressMutableData.postValue(View.GONE);
                hotelMutableLiveData.postValue(o);
            }
        });
    }


    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<HotelById> getHotelMutableLiveData() {
        return hotelMutableLiveData;
    }
}
