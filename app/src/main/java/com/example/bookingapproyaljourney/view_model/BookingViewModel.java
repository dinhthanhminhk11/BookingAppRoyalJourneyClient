package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.repository.Repository;

public class BookingViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<HotelBillResponse> hotelBillResponseMutableLiveData = new MutableLiveData<>();

    public BookingViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getHotelAndRoomByIdRoom(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getHotelAndRoomByIdRoom(id, o -> {
            if (o instanceof HotelBillResponse) {
                mProgressMutableData.postValue(View.GONE);
                hotelBillResponseMutableLiveData.postValue(o);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<HotelBillResponse> getHotelBillResponseMutableLiveData() {
        return hotelBillResponseMutableLiveData;
    }
}
