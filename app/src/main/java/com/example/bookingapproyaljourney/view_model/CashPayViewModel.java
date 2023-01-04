package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackCashPay;
import com.example.bookingapproyaljourney.callback.CallbackListPayCashFolw;
import com.example.bookingapproyaljourney.repository.CashPayRepository;
import com.example.bookingapproyaljourney.response.user.CashFolwResponse;

import java.util.List;

public class CashPayViewModel extends AndroidViewModel {

    private CashPayRepository repository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    MutableLiveData<String> getPrice = new MutableLiveData<>();
    MutableLiveData<List<CashFolwResponse>> listMutableLiveDataListCashPayFolw = new MutableLiveData<>();

    public CashPayViewModel(@NonNull Application application) {
        super(application);
        repository = new CashPayRepository();
    }

    public void getPricePayCashByUser(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getPriceCashPay(id, new CallbackCashPay() {
            @Override
            public void success(String result) {
                mProgressMutableData.postValue(View.GONE);
                getPrice.postValue(result);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void getListPayCash(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getListPayCashFolw(id, new CallbackListPayCashFolw() {
            @Override
            public void success(List<CashFolwResponse> list) {
                mProgressMutableData.postValue(View.GONE);
                listMutableLiveDataListCashPayFolw.postValue(list);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public LiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public LiveData<String> getGetPrice() {
        return getPrice;
    }

    public LiveData<List<CashFolwResponse>> getListMutableLiveDataListCashPayFolw() {
        return listMutableLiveDataListCashPayFolw;
    }
}
