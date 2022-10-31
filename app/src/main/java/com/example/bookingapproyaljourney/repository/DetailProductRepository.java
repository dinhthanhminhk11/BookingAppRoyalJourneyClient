package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductRepository {
    private ApiRequest apiRequest;

    public DetailProductRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<House> getCategory(String idHouse) {
        final MutableLiveData<House> data = new MutableLiveData<>();
        apiRequest.getDetailProduct(idHouse).enqueue(new Callback<House>() {
            @Override
            public void onResponse(Call<House> call, Response<House> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<House> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
        return data;
    }
}
