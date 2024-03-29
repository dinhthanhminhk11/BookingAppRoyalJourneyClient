package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.callback.CallbackCountResponse;
import com.example.bookingapproyaljourney.callback.CallbackListOrderAccessById;
import com.example.bookingapproyaljourney.callback.CallbackTokenDevice;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRequestTokenDevice;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.CountNotiResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser2;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<Integer>  mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();
    MutableLiveData<LoginResponse> mLoginResultMutableDataToKen = new MutableLiveData<>();
    MutableLiveData<CountNotiResponse> countNotiResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<ListOrderByIdUser2> listOrderByIdUserMutableLiveData = new MutableLiveData<>();

    private UserRepository userRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void login(String username, String password) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getUser(new UserLogin(username, password), new UserRepository.InterfaceResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.GONE);
                mLoginResultMutableDataToKen.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }

            @Override
            public void onResponseFailure(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.GONE);
                mLoginResultMutableData.postValue(loginResponse.getMessage());
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
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }

            @Override
            public void onResponseFailure(LoginResponse loginResponse) {

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

    public void getListOrderAccessById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getListOrderAccessById(id, new CallbackListOrderAccessById() {
            @Override
            public void success(ListOrderByIdUser2 listOrderByIdUser) {
                listOrderByIdUserMutableLiveData.postValue(listOrderByIdUser);
                mProgressMutableData.postValue(View.GONE);
            }

            @Override
            public void failure(Throwable t) {
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

    public LiveData<ListOrderByIdUser2> getListOrderByIdUserMutableLiveData() {
        return listOrderByIdUserMutableLiveData;
    }
}
