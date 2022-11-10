package com.example.bookingapproyaljourney.repository;


import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallVerifyRepository;
import com.example.bookingapproyaljourney.callback.InterfaceResponseChangePassword;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassRepository {
    private ApiRequest apiRequest;

    public ChangePassRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void checkMail(Email email, InterfaceResponseChangePassword interfaceResponseChangePassword) {
        Call<TestResponse> call = apiRequest.checkEmail(email);
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseChangePassword.onResponseCheckMail(response.body());
                } else {
                    interfaceResponseChangePassword.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                interfaceResponseChangePassword.onFailure(t);
            }
        });
    }

    public void checkOtpPass(Verify verify, InterfaceResponseChangePassword interfaceResponseChangePassword) {
        Call<TestResponse> call = apiRequest.checkOtpPass(verify);
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseChangePassword.onResponseCheckMail(response.body());
                } else {
                    interfaceResponseChangePassword.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                interfaceResponseChangePassword.onFailure(t);
            }
        });
    }

    public void newPassWord(UserLogin userLogin, InterfaceResponseChangePassword interfaceResponseChangePassword) {
        Call<TestResponse> call = apiRequest.newPassWord(userLogin);
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseChangePassword.onResponseCheckMail(response.body());
                } else {
                    interfaceResponseChangePassword.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                interfaceResponseChangePassword.onFailure(t);
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

}
