package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackFilter;
import com.example.bookingapproyaljourney.callback.InterfaceBookmark;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.repository.FilterRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;

public class FilterViewModel extends AndroidViewModel {
    private FilterRepository filterRepository;

    public FilterViewModel(@NonNull Application application) {
        super(application);
        filterRepository = new FilterRepository();
    }
    public MutableLiveData<CategoryBestForYouResponse> filterLiveData(String startPrice, String endPrice, String sao, String idCategory){
        MutableLiveData<CategoryBestForYouResponse> data = new MutableLiveData<>();
        filterRepository.getListFilter(startPrice, endPrice, sao, idCategory, new CallbackFilter() {
            @Override
            public void onResponse(CategoryBestForYouResponse categoryBestForYouResponse) {
                data.postValue(categoryBestForYouResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("zzzzzzzzzzzz", t.getMessage() );
            }
        });
        return data;
    }

}
