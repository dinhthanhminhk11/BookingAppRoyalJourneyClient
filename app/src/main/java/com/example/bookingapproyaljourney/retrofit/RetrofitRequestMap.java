package com.example.bookingapproyaljourney.retrofit;

import com.example.bookingapproyaljourney.constants.AppConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequestMap {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstanceMap() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.URL_API_GOOGLE_MAP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
