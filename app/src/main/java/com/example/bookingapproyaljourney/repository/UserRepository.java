package com.example.bookingapproyaljourney.repository;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.callback.CallbackCountResponse;
import com.example.bookingapproyaljourney.callback.CallbackTokenDevice;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.model.user.UserRequestTokenDevice;
import com.example.bookingapproyaljourney.response.CountNotiResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.RegisterResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

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
                    if (response.body().isStatus()) {
                        interfaceLoginResponse.onResponse(response.body());
                    } else {
                        interfaceLoginResponse.onResponseFailure(response.body());
                    }
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
                    if (response.body().isStatus()) {
                        interfaceResponse.onResponseRegister(response.body());
                    } else {
                        interfaceResponse.onResponseRegisterFailed(response.body());
                    }
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

    public void getUserByToken(String token, InterfaceResponse interfaceLoginResponse) {
        Call<LoginResponse> loginResponseCall = apiRequest.getUserByToken(token);
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

            }
        });
    }

    public interface InterfaceResponse {
        void onResponse(LoginResponse loginResponse);

        void onResponseFailure(LoginResponse loginResponse);

        void onFailure(Throwable t);

    }

    public interface InterfaceResponseRegister {
        void onFailureRegister(Throwable t);

        void onResponseRegister(RegisterResponse registerResponse);

        void onResponseRegisterFailed(RegisterResponse registerResponse);
    }

    public void sendAgain(Email email, CallSendAgain callSendAgain) {
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

    public void updateTokenDevice(UserRequestTokenDevice userRequestTokenDevice, CallbackTokenDevice callbackTokenDevice) {
        apiRequest.updateTokenDevice(userRequestTokenDevice).enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                if (response.isSuccessful()) {
                    callbackTokenDevice.onResponse(response.body());
                } else {
                    callbackTokenDevice.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                callbackTokenDevice.onFailure(t);
            }
        });
    }

    public void getCountNotificationByUser(String id, CallbackCountResponse callbackCountResponse) {
        apiRequest.getCountNotification(id).enqueue(new Callback<CountNotiResponse>() {
            @Override
            public void onResponse(Call<CountNotiResponse> call, Response<CountNotiResponse> response) {
                if (response.isSuccessful()) {
                    callbackCountResponse.onResponse(response.body());
                } else {
                    callbackCountResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<CountNotiResponse> call, Throwable t) {
                callbackCountResponse.onFailure(t);
            }
        });
    }
}
