package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.util.Log;
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
    MutableLiveData<String> mRegisterResultMutableData = new MutableLiveData<>();

    private UserRepository userRepository;


    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void register(String fullName, String email, String password, String registerSuccess, String registerFailed) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.getUserRegister(new UserRegister(fullName, email, password), new UserRepository.InterfaceResponseRegister() {
            @Override
            public void onFailureRegister(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mRegisterResultMutableData.postValue(registerFailed);
            }

            @Override
            public void onResponseRegister(RegisterResponse registerResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mRegisterResultMutableData.postValue(registerSuccess);

                Log.e("MinhRegister", registerResponse.getEmail());
                Log.e("MinhRegister", registerResponse.getMessage());
                Log.e("MinhRegister", registerResponse.getStatus());

            }
        });
    }

    public LiveData<String> getLoginResult() {
        return mRegisterResultMutableData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

}
