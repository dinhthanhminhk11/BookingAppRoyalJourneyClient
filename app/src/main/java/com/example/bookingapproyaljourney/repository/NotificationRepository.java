package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallbackListNotification;
import com.example.bookingapproyaljourney.callback.CallbackUpdateNoti;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.response.NotiResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {

    private ApiRequest apiRequest;

    public NotificationRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getListNotificationById(String id, CallbackListNotification callbackListNotification) {
        apiRequest.getListNotification(id).enqueue(new Callback<NotiResponse>() {
            @Override
            public void onResponse(Call<NotiResponse> call, Response<NotiResponse> response) {
                if (response.isSuccessful()) {
                    callbackListNotification.success(response.body());
                } else {
                    callbackListNotification.failure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<NotiResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void updateNotiSeen(String id, CallbackUpdateNoti callbackUpdateNoti) {
        apiRequest.updateNotiSeen(id).enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    callbackUpdateNoti.onResponse(response.body());
                } else {
                    callbackUpdateNoti.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }
}
