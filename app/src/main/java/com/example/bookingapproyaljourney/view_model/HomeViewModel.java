package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.repository.Repository;

public class HomeViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<HotelReponse> hotelReponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<HotelReponseNearBy> hotelReponseMutableLiveDataNearBy = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListAllHotel() {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getListAllHotel(o -> {
            if (o instanceof HotelReponse) {
                mProgressMutableData.postValue(View.GONE);
                hotelReponseMutableLiveData.postValue(o);
            }
        });
    }

    public void getListHotelNearBy(LocationNearByRequest locationNearByRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getListHotelNearByUser(locationNearByRequest, o -> {
            if (o instanceof HotelReponseNearBy) {
                mProgressMutableData.postValue(View.GONE);
                hotelReponseMutableLiveDataNearBy.postValue(o);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<HotelReponse> getHotelReponseMutableLiveData() {
        return hotelReponseMutableLiveData;
    }

    public MutableLiveData<HotelReponseNearBy> getHotelReponseMutableLiveDataNearBy() {
        return hotelReponseMutableLiveDataNearBy;
    }
}
