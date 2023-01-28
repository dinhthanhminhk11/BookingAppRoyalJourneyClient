package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackTextResponse;
import com.example.bookingapproyaljourney.model.cash.CashFolwRequest;
import com.example.bookingapproyaljourney.repository.CashPayRepository;
import com.example.bookingapproyaljourney.response.TestResponse;

public class AddCashViewModel extends AndroidViewModel {

    private CashPayRepository repository;
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();

    public AddCashViewModel(@NonNull Application application) {
        super(application);
        repository = new CashPayRepository();
    }

    public void createCash(CashFolwRequest cashFolwRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.createCashByUser(cashFolwRequest, new CallbackTextResponse() {
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

    public LiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public LiveData<TestResponse> getTestResponseMutableLiveData() {
        return testResponseMutableLiveData;
    }
}
