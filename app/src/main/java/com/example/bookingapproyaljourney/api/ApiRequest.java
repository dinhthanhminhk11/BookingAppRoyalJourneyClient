package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.response.CategoryResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.RegisterResponse;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @GET("api/listCategory")
    Call<List<Category>> getCategory();

    @POST("api/signin")
    Call<LoginResponse> getUser(@Body UserLogin userLogin);

    @POST("api/signup")
    Call<RegisterResponse> getUserRegister(@Body UserRegister userRegister);
}