package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallbackFilter;
import com.example.bookingapproyaljourney.callback.CallbackGetBookmark;
import com.example.bookingapproyaljourney.callback.InterfaceBookmark;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.order.ListFilterResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterRepository {
    private ApiRequest apiRequest;

    public FilterRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }


    public void getListFilter(String startPrice, String endPrice, String sao, String idCategory, CallbackFilter callbackFilter) {
       apiRequest.getListFilter(startPrice,endPrice,sao,idCategory).enqueue(new Callback<ListFilterResponse>() {
           @Override
           public void onResponse(Call<ListFilterResponse> call, Response<ListFilterResponse> response) {
               callbackFilter.onResponse(response.body());
           }

           @Override
           public void onFailure(Call<ListFilterResponse> call, Throwable t) {
                callbackFilter.onFailure(t);
           }
       });
    }

}
