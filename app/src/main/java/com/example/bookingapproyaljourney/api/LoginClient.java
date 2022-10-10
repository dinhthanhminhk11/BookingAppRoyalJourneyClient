package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.user.UserLogin;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginClient {
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("")
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    public static UserLogin getLognin(){
        UserLogin userLogin = getRetrofit().create(UserLogin.class);
        return userLogin;
    }
}
