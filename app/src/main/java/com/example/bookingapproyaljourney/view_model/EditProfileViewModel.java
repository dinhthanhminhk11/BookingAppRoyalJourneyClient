package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallBackEditProfile;
import com.example.bookingapproyaljourney.model.user.UserEditProfileRequest;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.TestResponse;

public class EditProfileViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void updateProfileUser(UserEditProfileRequest userEditProfileRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.updateInfoUser(userEditProfileRequest, new CallBackEditProfile() {
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

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<TestResponse> getTestResponseMutableLiveData() {
        return testResponseMutableLiveData;
    }
}
