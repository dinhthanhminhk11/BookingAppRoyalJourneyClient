package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackTokenDevice;
import com.example.bookingapproyaljourney.repository.UserRepository;
import com.example.bookingapproyaljourney.response.ProfileUserResponse;
import com.example.bookingapproyaljourney.response.TestResponse;

public class EditProfileViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void updateProfileUser(ProfileUserResponse profileUserResponse) {
        mProgressMutableData.postValue(View.VISIBLE);
        userRepository.updateInfoUser(profileUserResponse, new CallbackTokenDevice() {
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
