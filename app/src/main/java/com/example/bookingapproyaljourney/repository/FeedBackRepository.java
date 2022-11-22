package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.InterfaceResponseFeedBack;
import com.example.bookingapproyaljourney.model.feedback.DataFeedBack;
import com.example.bookingapproyaljourney.model.feedback.DataId;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.bookingapproyaljourney.model.feedback.ListIdUser;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBackRepository {
    private ApiRequest apiRequest;

    public FeedBackRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void updateSao(String id, Double sao){
        apiRequest.updateSaoProduct(id,sao).enqueue(new Callback<HouseDetailResponse>() {
            @Override
            public void onResponse(Call<HouseDetailResponse> call, Response<HouseDetailResponse> response) {
                Log.e("zzzzzzzzzz", "updateTrue" );
            }

            @Override
            public void onFailure(Call<HouseDetailResponse> call, Throwable t) {
                Log.e("zzzzzzzzzz", t.getMessage() );
            }
        });
    }

    public void insertFeedback(FeedBack feedBack, InterfaceResponseFeedBack interfaceResponseFeedBack) {
        apiRequest.createFeedBack(feedBack).enqueue(new Callback<FeedBack>() {
            @Override
            public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
                if (response.isSuccessful()) {
                    interfaceResponseFeedBack.onResponse(response.body());
                } else {
                    interfaceResponseFeedBack.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<FeedBack> call, Throwable t) {
                interfaceResponseFeedBack.onFailure(t);
            }
        });
    }

    public void updateFeedback(FeedBack feedBack) {
       apiRequest.updateUser(feedBack).enqueue(new Callback<FeedBack>() {
           @Override
           public void onResponse(Call<FeedBack> call, Response<FeedBack> response) {
           }

           @Override
           public void onFailure(Call<FeedBack> call, Throwable t) {
               Log.e("zzzzzzzzzz", t.getMessage() );
           }
       });
    }

    public MutableLiveData<List<FeedBack>> getFeedbackId(String idHouse) {
        final MutableLiveData<List<FeedBack>> dataFeedback = new MutableLiveData<>();
        apiRequest.getFeedBack(idHouse).enqueue(new Callback<DataFeedBack>() {
            @Override
            public void onResponse(Call<DataFeedBack> call, Response<DataFeedBack> response) {
                if(response.isSuccessful()){
                    dataFeedback.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataFeedBack> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzzzz", t.getMessage() );
            }
        });
        return dataFeedback;
    }

    public MutableLiveData<List<ListIdUser>> getListIdUser(String idHouse){
        MutableLiveData<List<ListIdUser>> list = new MutableLiveData<>();
        apiRequest.getListUserFeedback(idHouse).enqueue(new Callback<DataId>() {
            @Override
            public void onResponse(Call<DataId> call, Response<DataId> response) {
                if(response.isSuccessful()){
                    list.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<DataId> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzzzz", t.getMessage() );
            }
        });
        return list;
    }

    public MutableLiveData<List<FeedBack>> getFeedbackTk(String idHouse,String tk) {
        final MutableLiveData<List<FeedBack>> dataFeedback = new MutableLiveData<>();
        apiRequest.getFeedbackTk(idHouse, tk).enqueue(new Callback<DataFeedBack>() {
            @Override
            public void onResponse(Call<DataFeedBack> call, Response<DataFeedBack> response) {
                if(response.isSuccessful()){
                    dataFeedback.postValue(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<DataFeedBack> call, Throwable t) {
                Log.e("zzzzzzzzzzzzzzzz", t.getMessage() );
            }
        });
        return dataFeedback;
    }
}
