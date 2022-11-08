package com.example.bookingapproyaljourney.repository;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallVerifyRepository;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyRepository {
    private ApiRequest apiRequest;

    public VerifyRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void verifyOTPEmail(Verify verify, CallVerifyRepository callVerifyRepository) {
        Call<TestResponse> testResponseCall = apiRequest.getUserRegisterOTP(verify);
        testResponseCall.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    callVerifyRepository.onResponse(response.body());
                } else {
                    callVerifyRepository.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                callVerifyRepository.onFailure(t);
            }
        });
    }
}
