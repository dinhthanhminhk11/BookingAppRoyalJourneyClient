package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.RegisterResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {


    private ApiRequest apiRequest;

    public UserRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getUser(UserLogin userLogin, InterfaceResponse interfaceLoginResponse) {
        Call<LoginResponse> loginResponseCall = apiRequest.getUser(userLogin);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    interfaceLoginResponse.onResponse(response.body());
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

    public void getUserRegister(UserRegister userRegister, InterfaceResponseRegister interfaceResponse) {
        Call<RegisterResponse> registerResponseCall = apiRequest.getUserRegister(userRegister);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponse.onResponseRegister(response.body());
                } else {
                    interfaceResponse.onFailureRegister(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                interfaceResponse.onFailureRegister(t);
            }
        });
    }

    public interface InterfaceResponse {
        void onResponse(LoginResponse loginResponse);

        void onFailure(Throwable t);

    }

    public interface InterfaceResponseRegister {
        void onFailureRegister(Throwable t);

        void onResponseRegister(RegisterResponse registerResponse);
    }
}
