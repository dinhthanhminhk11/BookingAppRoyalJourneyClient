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
import com.example.bookingapproyaljourney.response.order.ListFilterResponse;

public class FilterViewModel extends AndroidViewModel {
    private FilterRepository filterRepository;

    public FilterViewModel(@NonNull Application application) {
        super(application);
        filterRepository = new FilterRepository();
    }
    public MutableLiveData<ListFilterResponse> filterLiveData(String startPrice, String endPrice, String sao, String idCategory){
        MutableLiveData<ListFilterResponse> data = new MutableLiveData<>();
        filterRepository.getListFilter(startPrice, endPrice, sao, idCategory, new CallbackFilter() {
            @Override
            public void onResponse(ListFilterResponse listFilterResponse) {
                data.postValue(listFilterResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("zzzzzzzzzzzzz", t.getMessage() );
            }
        });
        return data;
    }

}
