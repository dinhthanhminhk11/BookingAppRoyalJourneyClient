package com.example.bookingapproyaljourney.repository;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.callback.CallVerifyRepository;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.LoginResponse;
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

    public void getUser(UserLogin userLogin, CallVerifyRepository interfaceLoginResponse) {
        Call<LoginResponse> loginResponseCall = apiRequest.getUser(userLogin);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    interfaceLoginResponse.onResponseLogin(response.body());
                } else {
                    interfaceLoginResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                interfaceLoginResponse.onFailure(t);
            }
        });
    }

    public void sendAgain(Email email , CallSendAgain callSendAgain){
        Call<TestResponse> testResponseCall = apiRequest.sendAgain(email);
        testResponseCall.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    callSendAgain.onResponse(response.body());
                } else {
                    callSendAgain.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                callSendAgain.onFailure(t);
            }
        });

    }
}
