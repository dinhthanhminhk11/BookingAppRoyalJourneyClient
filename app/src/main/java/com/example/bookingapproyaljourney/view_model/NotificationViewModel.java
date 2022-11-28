package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackListNotification;
import com.example.bookingapproyaljourney.callback.CallbackUpdateNoti;
import com.example.bookingapproyaljourney.repository.NotificationRepository;
import com.example.bookingapproyaljourney.response.NotiResponse;
import com.example.bookingapproyaljourney.response.TestResponse;

public class NotificationViewModel extends AndroidViewModel {

    private NotificationRepository notificationRepository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<NotiResponse> notiResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<TestResponse> testResponseMutableLiveData = new MutableLiveData<>();

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationRepository = new NotificationRepository();
    }

    public void getListNotification(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        notificationRepository.getListNotificationById(id, new CallbackListNotification() {
            @Override
            public void success(NotiResponse notiResponse) {
                mProgressMutableData.postValue(View.GONE);
                notiResponseMutableLiveData.postValue(notiResponse);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void updateNotiSeen(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        notificationRepository.updateNotiSeen(id, new CallbackUpdateNoti() {
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

    public MutableLiveData<NotiResponse> getNotiResponseMutableLiveData() {
        return notiResponseMutableLiveData;
    }

    public MutableLiveData<TestResponse> getTestResponseMutableLiveData() {
        return testResponseMutableLiveData;
    }
}
