package com.example.bookingapproyaljourney.view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    MutableLiveData<LoginResponse> mLoginResultMutableDataToKen = new MutableLiveData<>();

    private UserRepository userRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void login(String username, String password, String loginSuccess, String loginFail) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getUser(new UserLogin(username, password), new UserRepository.InterfaceResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableData.postValue(loginSuccess);
                mLoginResultMutableDataToKen.postValue(loginResponse);
                UserClient userClient = UserClient.getInstance();
                userClient.setEmail(loginResponse.getUser().getEmail());
                userClient.setId(loginResponse.getUser().getId());
                userClient.setName(loginResponse.getUser().getName());
                userClient.setImage(loginResponse.getUser().getImage());
                userClient.setPhone(loginResponse.getUser().getPhone());
                userClient.setAddress(loginResponse.getUser().getAddress());

                Log.e("MinhLogin", loginResponse.getUser().getEmail());
                Log.e("MinhLogin", loginResponse.getMessage());
                Log.e("MinhLogin", loginResponse.getUser().getId());
                Log.e("MinhLoginToken", loginResponse.getToken());
                Log.e("MinhLoginactive", loginResponse.getUser().isActive() + " sss");
                Log.e("MinhLoginactive", loginResponse.getUser().getOtp() + " sxxxxxss");


            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableData.postValue(loginFail);
            }
        });
    }

    public void getUserByToken(String token) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getUserByToken(token, new UserRepository.InterfaceResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableDataToKen.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public void sendAgain(Email email){
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.sendAgain(email, new CallSendAgain() {
            @Override
            public void onResponse(TestResponse testResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
//                sendAgainTestResponse.postValue(testResponse);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public LiveData<String> getLoginResult() {
        return mLoginResultMutableData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

    public MutableLiveData<LoginResponse> getLoginResultMutableDataToKen() {
        return mLoginResultMutableDataToKen;
    }
}
