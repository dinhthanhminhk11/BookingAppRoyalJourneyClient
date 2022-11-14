package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.chat.Data;
import com.example.bookingapproyaljourney.model.chat.DataUser;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRepository {
    private ApiRequest apiRequest;

    public ChatRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<List<Content>> getContentChat(String sendId, String sendToId){
        final MutableLiveData<List<Content>> data = new MutableLiveData<>();
        apiRequest.getDataChat(sendId,sendToId).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                data.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage() );
            }
        });
        return data;
    }

    public void insertMessage(Message message){
        apiRequest.addMessage(message).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.e("zzzzzzzzzzzzzz","da gui" );
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage()+"error" );
            }
        });
    }

    public MutableLiveData<List<Content>> getMsgId(String send){
        final MutableLiveData<List<Content>> data = new MutableLiveData<>();
        apiRequest.getMsgId(send).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()){
                    data.postValue(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage()+"error" );
            }
        });
        return data;
    }

    public MutableLiveData<List<User>> getHost(String id){
        final MutableLiveData<List<User>> user = new MutableLiveData<>();
        apiRequest.getHost(id).enqueue(new Callback<DataUser>() {
            @Override
            public void onResponse(Call<DataUser> call, Response<DataUser> response) {
                if(response.isSuccessful()){
                    user.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataUser> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzz", t.getMessage()+"error" );
            }
        });
        return user;
    }
}
