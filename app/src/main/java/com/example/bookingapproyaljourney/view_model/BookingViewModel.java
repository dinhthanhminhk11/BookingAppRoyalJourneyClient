package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.repository.Repository;
import com.example.bookingapproyaljourney.request.BillRequest;
import com.example.bookingapproyaljourney.response.bill.BillResponse;

public class BookingViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<HotelBillResponse> hotelBillResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<BillResponse> billResponseMutableLiveData = new MutableLiveData<>();

    public BookingViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getHotelAndRoomByIdRoom(String id,String idUser) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getHotelAndRoomByIdRoom(id, idUser, o -> {
            if (o instanceof HotelBillResponse) {
                mProgressMutableData.postValue(View.GONE);
                hotelBillResponseMutableLiveData.postValue(o);
            }
        });
    }

    public void createBooking(BillRequest billRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.createBill(billRequest, o -> {
            if (o instanceof BillResponse) {
                mProgressMutableData.postValue(View.GONE);
                billResponseMutableLiveData.postValue((BillResponse) o);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<HotelBillResponse> getHotelBillResponseMutableLiveData() {
        return hotelBillResponseMutableLiveData;
    }

    public MutableLiveData<BillResponse> getBillResponseMutableLiveData() {
        return billResponseMutableLiveData;
    }
}
