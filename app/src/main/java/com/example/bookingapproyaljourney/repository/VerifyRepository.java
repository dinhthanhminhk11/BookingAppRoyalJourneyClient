package com.example.bookingapproyaljourney.repository;

import android.telecom.Call;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

public class VerifyRepository {
    private ApiRequest apiRequest;

    public VerifyRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void verifyOTPEmail(Verify verify){
//        Call<>
    }
}
