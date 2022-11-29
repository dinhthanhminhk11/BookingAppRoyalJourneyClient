package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.callback.CallbackCountResponse;
import com.example.bookingapproyaljourney.callback.CallbackTokenDevice;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRequestTokenDevice;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.CountNotiResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    MutableLiveData<LoginResponse> mLoginResultMutableDataToKen = new MutableLiveData<>();
    MutableLiveData<CountNotiResponse> countNotiResponseMutableLiveData = new MutableLiveData<>();

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
                mProgressMutableData.postValue(View.GONE);
                mLoginResultMutableData.postValue(loginSuccess);
                mLoginResultMutableDataToKen.postValue(loginResponse);
                UserClient userClient = UserClient.getInstance();
                userClient.setEmail(loginResponse.getUser().getEmail());
                userClient.setId(loginResponse.getUser().getId());
                userClient.setName(loginResponse.getUser().getName());
                userClient.setImage(loginResponse.getUser().getImage());
                userClient.setPhone(loginResponse.getUser().getPhone());
                userClient.setAddress(loginResponse.getUser().getAddress());
                userClient.setCountBooking(loginResponse.getUser().getCountBooking());
                Log.e("MinhLogin", loginResponse.getUser().getEmail());
                Log.e("MinhLogin", loginResponse.getMessage());
                Log.e("MinhLogin", loginResponse.getUser().getId());
                Log.e("MinhLoginToken", loginResponse.getToken());
                Log.e("MinhLoginactive", loginResponse.getUser().isActive() + " sss");
                Log.e("MinhLoginactive", loginResponse.getUser().getOtp() + " sxxxxxss");


            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
                mLoginResultMutableData.postValue(loginFail);
            }
        });
    }

    public void getUserByToken(String token) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getUserByToken(token, new UserRepository.InterfaceResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.GONE);
                mLoginResultMutableDataToKen.postValue(loginResponse);
                UserClient userClient = UserClient.getInstance();
                userClient.setEmail(loginResponse.getUser().getEmail());
                userClient.setId(loginResponse.getUser().getId());
                userClient.setName(loginResponse.getUser().getName());
                userClient.setImage(loginResponse.getUser().getImage());
                userClient.setPhone(loginResponse.getUser().getPhone());
                userClient.setAddress(loginResponse.getUser().getAddress());
                userClient.setCountBooking(loginResponse.getUser().getCountBooking());
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public void sendAgain(Email email) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.sendAgain(email, new CallSendAgain() {
            @Override
            public void onResponse(TestResponse testResponse) {
                mProgressMutableData.postValue(View.GONE);
//                sendAgainTestResponse.postValue(testResponse);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void updateTokenDevice(UserRequestTokenDevice userRequestTokenDevice) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.updateTokenDevice(userRequestTokenDevice, new CallbackTokenDevice() {
            @Override
            public void onResponse(TestResponse testResponse) {
                mProgressMutableData.postValue(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getCountNotificationByUser(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getCountNotificationByUser(id, new CallbackCountResponse() {
            @Override
            public void onResponse(CountNotiResponse countNotiResponse) {
                countNotiResponseMutableLiveData.postValue(countNotiResponse);
                mProgressMutableData.postValue(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
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

    public MutableLiveData<CountNotiResponse> getCountNotiResponseMutableLiveData() {
        return countNotiResponseMutableLiveData;
    }
}
