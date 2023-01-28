package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.repository.Repository;

public class RoomInfoViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<Room> roomMutableLiveData = new MutableLiveData<>();

    public RoomInfoViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getRoomById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        repository.getRoomById(id, o -> {
            if (o instanceof Room) {
                mProgressMutableData.postValue(View.GONE);
                roomMutableLiveData.postValue(o);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<Room> getRoomMutableLiveData() {
        return roomMutableLiveData;
    }
}
