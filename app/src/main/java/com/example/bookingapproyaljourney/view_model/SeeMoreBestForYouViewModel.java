package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.HouseByCategoryCallback;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.repository.CategoryRepository;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;

public class SeeMoreBestForYouViewModel extends AndroidViewModel {

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();

    public MutableLiveData<HotelReponse> getHotelReponseMutableLiveData() {
        return hotelReponseMutableLiveData;
    }

    MutableLiveData<HotelReponse> hotelReponseMutableLiveData = new MutableLiveData<>();

    MutableLiveData<CategoryBestForYouResponse> categoryBestForYouResponseMutableLiveData = new MutableLiveData<>();

    private CategoryRepository categoryRepository;

    public SeeMoreBestForYouViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository();
    }
    public void getListAllHotel() {
        mProgressMutableData.postValue(View.VISIBLE);
        categoryRepository.getListAllHotel(o -> {
            if (o instanceof HotelReponse) {
                mProgressMutableData.postValue(View.GONE);
                hotelReponseMutableLiveData.postValue(o);
            }
        });
    }


    public void getListBestForYouById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        categoryRepository.getHouseByCategory(id, new HouseByCategoryCallback() {
            @Override
            public void success(CategoryBestForYouResponse categoryBestForYouResponse) {
                mProgressMutableData.postValue(View.GONE);
                categoryBestForYouResponseMutableLiveData.postValue(categoryBestForYouResponse);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<CategoryBestForYouResponse> getCategoryBestForYouResponseMutableLiveData() {
        return categoryBestForYouResponseMutableLiveData;
    }
}
