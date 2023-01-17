package com.example.bookingapproyaljourney.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.InterfaceResponseHouseNearestByUser;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
public class HouseNearestByUserRepository {
    private ApiRequest apiRequest;

    public HouseNearestByUserRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<HouseNearestByUserResponse> getHouseNearestByUserOnMap(HouseNearestByUser houseNearestByUser) {
        final MutableLiveData<HouseNearestByUserResponse> data = new MutableLiveData<>();
        Call<HouseNearestByUserResponse> houseNearestByUserResponseCall = apiRequest.getHouseNearestByUser(houseNearestByUser);
        houseNearestByUserResponseCall.enqueue(new Callback<HouseNearestByUserResponse>() {
            @Override
            public void onResponse(Call<HouseNearestByUserResponse> call, Response<HouseNearestByUserResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    Log.d(AppConstant.TAG, "House total result:: " + response.body().getMessage());
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HouseNearestByUserResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
        return data;
    }

    public void getHouseNearestByUserOnMap(HouseNearestByUser houseNearestByUser, InterfaceResponseHouseNearestByUser interfaceResponseHouseNearestByUser) {
        Call<HouseNearestByUserResponse> houseNearestByUserResponseCall = apiRequest.getHouseNearestByUser(houseNearestByUser);
        houseNearestByUserResponseCall.enqueue(new Callback<HouseNearestByUserResponse>() {
            @Override
            public void onResponse(Call<HouseNearestByUserResponse> call, Response<HouseNearestByUserResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseHouseNearestByUser.onResponse(response.body());
                    Log.e(AppConstant.TAGZZZZZZZZ, "dataa  " + response.body().getDataMaps().size());
                } else {
                    interfaceResponseHouseNearestByUser.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<HouseNearestByUserResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void getListHotelNearByUser(LocationNearByRequest locationNearByRequest, Consumer<HotelReponseNearBy> consumer) {
        apiRequest.getListHotelNearBy(locationNearByRequest).enqueue(new Callback<HotelReponseNearBy>() {
            @Override
            public void onResponse(Call<HotelReponseNearBy> call, Response<HotelReponseNearBy> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelReponseNearBy> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

}
