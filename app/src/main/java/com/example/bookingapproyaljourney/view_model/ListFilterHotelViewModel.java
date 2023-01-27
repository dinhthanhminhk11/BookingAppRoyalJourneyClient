package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.search.SearchModel;
import com.example.bookingapproyaljourney.repository.Repository;

import java.util.List;

public class ListFilterHotelViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<List<SearchModel>> searchModelMutableLiveData = new MutableLiveData<>();

    public ListFilterHotelViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }
}
