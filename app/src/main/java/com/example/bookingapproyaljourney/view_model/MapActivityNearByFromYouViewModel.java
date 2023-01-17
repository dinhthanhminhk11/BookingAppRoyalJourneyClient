package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.InterfaceResponseHouseNearestByUser;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.repository.HouseNearestByUserRepository;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.google.android.gms.maps.model.LatLng;

public class MapActivityNearByFromYouViewModel extends AndroidViewModel {

    MutableLiveData<Integer> progressMutableData = new MutableLiveData<>();
    MutableLiveData<HouseNearestByUserResponse> nearestByUserOnMapResultMutableData = new MutableLiveData<>();
    MutableLiveData<HotelReponseNearBy> hotelReponseMutableLiveDataNearBy = new MutableLiveData<>();
    private HouseNearestByUserRepository houseNearestByUserRepository;

    public MapActivityNearByFromYouViewModel(@NonNull Application application) {
        super(application);
        houseNearestByUserRepository = new HouseNearestByUserRepository();
    }

    public void getHouseNearestByUserOnPosition(LatLng latLngYouSelf) {
        progressMutableData.postValue(View.VISIBLE);
        houseNearestByUserRepository.getHouseNearestByUserOnMap(new HouseNearestByUser(latLngYouSelf.longitude, latLngYouSelf.latitude, 10000), new InterfaceResponseHouseNearestByUser() {
            @Override
            public void onResponse(HouseNearestByUserResponse houseNearestByUserResponse) {
                nearestByUserOnMapResultMutableData.postValue(houseNearestByUserResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void getListHotelNearBy(LatLng latLngYouSelf) {
        progressMutableData.postValue(View.VISIBLE);
        houseNearestByUserRepository.getListHotelNearByUser(new LocationNearByRequest(latLngYouSelf.longitude, latLngYouSelf.latitude, 10000), o -> {
            if (o instanceof HotelReponseNearBy) {
                progressMutableData.postValue(View.GONE);
                hotelReponseMutableLiveDataNearBy.postValue(o);
            }
        });
    }

    public MutableLiveData<Integer> getProgressMutableData() {
        return progressMutableData;
    }

    public MutableLiveData<HouseNearestByUserResponse> getNearestByUserOnMapResultMutableData() {
        return nearestByUserOnMapResultMutableData;
    }

    public MutableLiveData<HotelReponseNearBy> getHotelReponseMutableLiveDataNearBy() {
        return hotelReponseMutableLiveDataNearBy;
    }
}
