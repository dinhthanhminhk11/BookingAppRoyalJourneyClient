package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.chat.Data;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private ApiRequest apiRequest;

    public ChatRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<List<Content>> getContentChat(){
        final MutableLiveData<List<Content>> data = new MutableLiveData<>();
        apiRequest.getDataChat().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                data.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage() );
            }
        });
        return data;
    }
}
