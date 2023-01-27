package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.search.SearchModel;
import com.example.bookingapproyaljourney.repository.Repository;

import java.util.List;

public class SearchHotelViewModel extends AndroidViewModel {
    private Repository repository;

    MutableLiveData<List<SearchModel>> searchModelMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public SearchHotelViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void getListSearchLocationHotel(String id) {
        repository.getListSearchLocationHotel(id, o -> {
            searchModelMutableLiveData.postValue((List<SearchModel>) o);
        });
    }

    public MutableLiveData<List<SearchModel>> getSearchModelMutableLiveData() {
        return searchModelMutableLiveData;
    }
}
