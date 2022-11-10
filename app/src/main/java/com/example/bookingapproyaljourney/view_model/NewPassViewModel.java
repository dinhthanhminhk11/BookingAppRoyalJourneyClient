package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.InterfaceResponseChangePassword;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.repository.ChangePassRepository;
import com.example.bookingapproyaljourney.response.TestResponse;

public class NewPassViewModel extends AndroidViewModel {

    private ChangePassRepository changePassRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();

    public NewPassViewModel(@NonNull Application application) {
        super(application);
        changePassRepository = new ChangePassRepository();
    }

    public void newPassword(UserLogin userLogin) {
        mProgressMutableData.postValue(View.VISIBLE);
        changePassRepository.newPassWord(userLogin, new InterfaceResponseChangePassword() {
            @Override
            public void onResponseCheckMail(TestResponse testResponse) {
                mProgressMutableData.postValue(View.GONE);
                testResponseMutableLiveData.postValue(testResponse);
                Log.e("Minhzzzzzzzzzzzzzz" , testResponse.getMessage());
                Log.e("Minhzzzzzzzzzzzzzz" , testResponse.isStatus() + " a");
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
