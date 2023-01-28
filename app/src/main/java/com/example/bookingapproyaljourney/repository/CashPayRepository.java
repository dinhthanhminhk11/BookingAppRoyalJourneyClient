package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallbackCashPay;
import com.example.bookingapproyaljourney.callback.CallbackListPayCashFolw;
import com.example.bookingapproyaljourney.callback.CallbackPassCash;
import com.example.bookingapproyaljourney.callback.CallbackTextResponse;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.cash.CashFolwRequest;
import com.example.bookingapproyaljourney.model.user.UserPin;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.response.user.CashFolwResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashPayRepository {
    private ApiRequest apiRequest;

    public CashPayRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getPriceCashPay(String id, CallbackCashPay callbackCashPay) {
        apiRequest.getPriceCash(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    callbackCashPay.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callbackCashPay.failure(t);
            }
        });
    }

    public void getListPayCashFolw(String id, CallbackListPayCashFolw callbackListPayCashFolw) {
        apiRequest.getListCashFolw(id).enqueue(new Callback<List<CashFolwResponse>>() {
            @Override
            public void onResponse(Call<List<CashFolwResponse>> call, Response<List<CashFolwResponse>> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    callbackListPayCashFolw.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CashFolwResponse>> call, Throwable t) {
                callbackListPayCashFolw.failure(t);
            }
        });
    }

    public void createCashByUser(CashFolwRequest cashFolwRequest, CallbackTextResponse callbackTextResponse) {
        apiRequest.createCashFolw(cashFolwRequest).enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    callbackTextResponse.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                callbackTextResponse.onFailure(t);
            }
        });
    }

    public void getPassCash(String id, CallbackPassCash callbackPassCash) {
        apiRequest.getPassCash(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    callbackPassCash.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callbackPassCash.failure(t);
            }
        });
    }

    public void createPassCash(UserPin userPin, CallbackTextResponse callbackTextResponse) {
        apiRequest.createPassCash(userPin).enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    callbackTextResponse.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                callbackTextResponse.onFailure(t);
            }
        });
    }
}
