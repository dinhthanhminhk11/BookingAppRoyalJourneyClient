package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackChangePassword;
import com.example.bookingapproyaljourney.model.user.ChangePasswordRequest;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.TestResponse;

public class ChangePasswordViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    private MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public LiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public LiveData<TestResponse> getTestResponseMutableLiveData() {
        return testResponseMutableLiveData;
    }

    public void ChangePassword(ChangePasswordRequest changePasswordRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.ChangePassword(changePasswordRequest, new CallbackChangePassword() {
            @Override
            public void onResponse(TestResponse testResponse) {
                mProgressMutableData.postValue(View.GONE);
                testResponseMutableLiveData.postValue(testResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }


}
