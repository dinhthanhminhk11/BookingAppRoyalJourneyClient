package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private static final String TAG = "Minh";
    private static final String TAG_ERROR = "Retrofit_Error";

    private ApiRequest apiRequest;

    public CategoryRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<List<Category>> getCategory() {
        final MutableLiveData<List<Category>> data = new MutableLiveData<>();
        apiRequest.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG_ERROR, "code ; " + response.code());
                } else {
                    Log.d(TAG, "Category total result:: " + response.body().get(0).getName());
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(TAG_ERROR, t.getMessage() + " error");
            }

        });
        return data;
    }
}
