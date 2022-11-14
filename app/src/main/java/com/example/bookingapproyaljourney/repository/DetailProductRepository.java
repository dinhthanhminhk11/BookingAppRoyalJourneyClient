package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductRepository {
    private ApiRequest apiRequest;

    public DetailProductRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<HouseDetailResponse> getCategory(String idHouse) {
        final MutableLiveData<HouseDetailResponse> data = new MutableLiveData<>();
        apiRequest.getDetailProduct(idHouse).enqueue(new Callback<HouseDetailResponse>() {
            @Override
            public void onResponse(Call<HouseDetailResponse> call, Response<HouseDetailResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG_ERROR, "code ; " + response.code());
                } else {
                    Log.e(AppConstant.TAG, "data " + response.body().getContent());
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<HouseDetailResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
        return data;
    }

    public void getProductById(String id, CallbackHouseById callbackHouseById) {
        apiRequest.getDetailProduct(id).enqueue(new Callback<HouseDetailResponse>() {
            @Override
            public void onResponse(Call<HouseDetailResponse> call, Response<HouseDetailResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG_ERROR, "code ; " + response.code());
                } else {
                    Log.e(AppConstant.TAG, "data " + response.body().getContent());
                    callbackHouseById.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<HouseDetailResponse> call, Throwable t) {
                callbackHouseById.failure(t);
            }
        });
    }
}
