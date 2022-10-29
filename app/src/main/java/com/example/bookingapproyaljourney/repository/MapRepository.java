package com.example.bookingapproyaljourney.repository;

import android.util.Log;
import android.widget.TextView;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.map.Root;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequestMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRepository {
    private ApiRequest apiRequest;

    public MapRepository() {
        this.apiRequest = RetrofitRequestMap.getRetrofitInstanceMap().create(ApiRequest.class);
    }

    public void getRootDistanceAndDuration(String locationUser, String locationEnd, TextView distanceText, TextView durationText) {
        apiRequest.getRoot(locationUser, locationEnd, AppConstant.API_KEY).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    Root root = response.body();
                    Log.d("ooooooo", response.body().toString());
                    Log.d("ooooooo", root.getRoutes().get(0).getLegs().get(0).getDistance().getText());
                    distanceText.setText(root.getRoutes().get(0).getLegs().get(0).getDistance().getText());
                    durationText.setText(root.getRoutes().get(0).getLegs().get(0).getDuration().getText());
                } else {
                    Log.d(AppConstant.TAG_ERROR, " error");
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

}
