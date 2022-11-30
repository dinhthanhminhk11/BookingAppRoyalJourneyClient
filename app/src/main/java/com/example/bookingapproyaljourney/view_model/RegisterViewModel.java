package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.RegisterResponse;

public class RegisterViewModel extends AndroidViewModel {
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<RegisterResponse> registerResponseMutableLiveData = new MutableLiveData<>();

    private UserRepository userRepository;


    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void register(String fullName, String email, String password,  String tokenDevice) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getUserRegister(new UserRegister(fullName, email, password, tokenDevice), new UserRepository.InterfaceResponseRegister() {
            @Override
            public void onFailureRegister(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }

            @Override
            public void onResponseRegister(RegisterResponse registerResponse) {
                mProgressMutableData.postValue(View.GONE);
                registerResponseMutableLiveData.postValue(registerResponse);
            }

            @Override
            public void onResponseRegisterFailed(RegisterResponse registerResponse) {
                registerResponseMutableLiveData.postValue(registerResponse);
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public LiveData<RegisterResponse> getLoginResult() {
        return registerResponseMutableLiveData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

}
