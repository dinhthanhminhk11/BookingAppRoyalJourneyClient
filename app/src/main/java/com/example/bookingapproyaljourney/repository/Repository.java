package com.example.bookingapproyaljourney.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getListAllHotel(Consumer<HotelReponse> consumer) {
        apiRequest.getAllListHotel().enqueue(new Callback<HotelReponse>() {
            @Override
            public void onResponse(Call<HotelReponse> call, Response<HotelReponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelReponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
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
