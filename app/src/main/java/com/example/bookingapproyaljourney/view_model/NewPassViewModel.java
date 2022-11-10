package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallSendAgain;
import com.example.bookingapproyaljourney.callback.CallVerifyRepository;
import com.example.bookingapproyaljourney.callback.InterfaceResponseChangePassword;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.repository.ChangePassRepository;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.ui.activity.NewPasswordActivity;

public class NewPassViewModel extends AndroidViewModel {

    private ChangePassRepository changePassRepository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<LoginResponse> mLoginResultMutableDataToKen = new MutableLiveData<>();
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

    public void login(String username, String password) {
        mProgressMutableData.postValue(View.VISIBLE);
        changePassRepository.getUser(new UserLogin(username, password), new CallVerifyRepository() {
            @Override
            public void onResponse(TestResponse testResponse) {

            }

            @Override
            public void onResponseLogin(LoginResponse loginResponse) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableDataToKen.postValue(loginResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
            }
        });
    }

    public MutableLiveData<LoginResponse> getmLoginResultMutableDataToKen() {
        return mLoginResultMutableDataToKen;
    }

    public void sendAgain(Email email){
        mProgressMutableData.postValue(View.VISIBLE);
        changePassRepository.sendAgain(email, new CallSendAgain() {
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
}
