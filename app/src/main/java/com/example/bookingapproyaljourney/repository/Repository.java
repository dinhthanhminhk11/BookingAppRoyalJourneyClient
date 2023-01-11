package com.example.bookingapproyaljourney.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.model.hotel.Room;
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

    public void getHotelById(String id, Consumer<HotelById> consumer) {
        apiRequest.getHotelById(id).enqueue(new Callback<HotelById>() {
            @Override
            public void onResponse(Call<HotelById> call, Response<HotelById> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelById> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getRoomById(String id, Consumer<Room> consumer) {
        apiRequest.getRoomById(id).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getHotelAndRoomByIdRoom(String id, Consumer<HotelBillResponse> consumer) {
        apiRequest.getHotelAndRoomByIdRoom(id).enqueue(new Callback<HotelBillResponse>() {
            @Override
            public void onResponse(Call<HotelBillResponse> call, Response<HotelBillResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelBillResponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }
}
