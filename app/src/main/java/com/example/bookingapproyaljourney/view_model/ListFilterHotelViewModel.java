package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.location.Location;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.repository.Repository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ListFilterHotelViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<List<Hotel>> searchModelMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private Location locationYouSelf;

    public ListFilterHotelViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListFilterHotelByHome(String textLocation, int ageChildren, int person, int children, int countRoom) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getListFilterHotelByHome(textLocation, ageChildren, person, children, countRoom, o -> {
            searchModelMutableLiveData.postValue((List<Hotel>) o);
            mProgressMutableData.postValue(View.GONE);
        });
    }

    public void getFilterHotelAndStarAndPrice(String textLocation, int ageChildren, int person, int children, int countRoom, int startPrice, int endPrice, int TbSao) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getFilterHotelAndStarAndPrice(textLocation, ageChildren, person, children, countRoom, startPrice, endPrice, TbSao, o -> {
            searchModelMutableLiveData.postValue((List<Hotel>) o);
            mProgressMutableData.postValue(View.GONE);
        });
    }

    public void nearByUserLocationAndFilter(LocationNearByRequest locationNearByRequest, int ageChildren, int person, int children, int countRoom) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.nearByUserLocationAndFilter(locationNearByRequest, ageChildren, person, children, countRoom, o -> {
            searchModelMutableLiveData.postValue((List<Hotel>) o);
            mProgressMutableData.postValue(View.GONE);
        });
    }

    public void nearByUserLocationAndFilterAndPriceAndStar(LocationNearByRequest locationNearByRequest, int ageChildren, int person, int children, int countRoom , int startPrice, int endPrice, int TbSao) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.nearByUserLocationAndFilterAndPriceAndStar(locationNearByRequest, ageChildren, person, children, countRoom , startPrice, endPrice, TbSao, o -> {
            searchModelMutableLiveData.postValue((List<Hotel>) o);
            mProgressMutableData.postValue(View.GONE);
        });
    }

    public MutableLiveData<List<Hotel>> getSearchModelMutableLiveData() {
        return searchModelMutableLiveData;
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }
}
