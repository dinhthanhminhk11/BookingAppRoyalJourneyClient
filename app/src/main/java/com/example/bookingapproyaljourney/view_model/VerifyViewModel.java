package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.callback.CallVerifyRepository;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.repository.VerifyRepository;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;

public class VerifyViewModel extends AndroidViewModel {
    private VerifyRepository verifyRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<TestResponse> mLoginResultMutableData = new MutableLiveData<>();
    MutableLiveData<LoginResponse> mLoginResultMutableDataToKen = new MutableLiveData<>();
    MutableLiveData<TestResponse> sendAgainTestResponse = new MutableLiveData<>();
    public VerifyViewModel(@NonNull Application application) {
        super(application);
        verifyRepository = new VerifyRepository();
    }

    public void postVerify(Verify verify) {
        mProgressMutableData.postValue(View.VISIBLE);
        verifyRepository.verifyOTPEmail(verify, new CallVerifyRepository() {
            @Override
            public void onResponse(TestResponse testResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableData.postValue(testResponse);
            }

            @Override
            public void onResponseLogin(LoginResponse loginResponse) {

            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public void login(String username, String password) {
        mProgressMutableData.postValue(View.VISIBLE);
        verifyRepository.getUser(new UserLogin(username, password), new CallVerifyRepository() {
            @Override
            public void onResponse(TestResponse testResponse) {

            }

            @Override
            public void onResponseLogin(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableDataToKen.postValue(loginResponse);
                UserClient userClient = UserClient.getInstance();
                userClient.setEmail(loginResponse.getUser().getEmail());
                userClient.setId(loginResponse.getUser().getId());
                userClient.setName(loginResponse.getUser().getName());
                userClient.setImage(loginResponse.getUser().getImage());
                userClient.setPhone(loginResponse.getUser().getPhone());
                userClient.setAddress(loginResponse.getUser().getAddress());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public void sendAgain(Email email){
        mProgressMutableData.postValue(View.VISIBLE);
        verifyRepository.sendAgain(email, new CallSendAgain() {
            @Override
            public void onResponse(TestResponse testResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                sendAgainTestResponse.postValue(testResponse);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public MutableLiveData<LoginResponse> getmLoginResultMutableDataToKen() {
        return mLoginResultMutableDataToKen;
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<TestResponse> getmLoginResultMutableData() {
        return mLoginResultMutableData;
    }

    public MutableLiveData<TestResponse> getSendAgainTestResponse() {
        return sendAgainTestResponse;
    }
}
