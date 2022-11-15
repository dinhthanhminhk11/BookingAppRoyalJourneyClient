package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallbackGetBookmark;
import com.example.bookingapproyaljourney.callback.InterfaceBookmark;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkRepository {
    private ApiRequest apiRequest;

    public BookmarkRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getListBookmarkById(String id, InterfaceBookmark interfaceBookmark) {
        Call<BookmarkResponse> call = apiRequest.getListBookMarkByIdUser(id);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response.isSuccessful()) {
                    interfaceBookmark.onResponse(response.body());
                } else {
                    interfaceBookmark.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void addBookMark(PostIDUserAndIdHouse postIDUserAndIdHouse, InterfacePostBookmark interfacePostBookmark) {
        Call<BookmarkResponse> call = apiRequest.addBookmark(postIDUserAndIdHouse);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response.isSuccessful()) {
                    interfacePostBookmark.onResponse(response.body());
                } else {
                    interfacePostBookmark.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void deleteBookmark(String idUser, String idHouse, InterfacePostBookmark interfacePostBookmark) {
        Call<BookmarkResponse> call = apiRequest.deleteBookmark(idUser, idHouse);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response.isSuccessful()) {
                    interfacePostBookmark.onResponse(response.body());
                } else {
                    interfacePostBookmark.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void getBookmarkByIdUserAndIdHouse(String idUser, String idHouse, CallbackGetBookmark callbackGetBookmark) {
        Call<BookmarkResponse> call = apiRequest.getBookmarkByIdUserAndIdHouse(idUser, idHouse);
        call.enqueue(new Callback<BookmarkResponse>() {
            @Override
            public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                if (response.isSuccessful()) {
                    callbackGetBookmark.onResponse(response.body());
                } else {
                    callbackGetBookmark.onFailure(response.body());
                }
            }

            @Override
            public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

}
