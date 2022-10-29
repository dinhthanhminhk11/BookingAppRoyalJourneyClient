package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {
    @GET("listCategory")
    Call<List<Category>> getCategory();

    @POST("signin")
    Call<LoginResponse> getUser(@Body UserLogin userLogin);

    @POST("signup")
    Call<RegisterResponse> getUserRegister(@Body UserRegister userRegister);

    @POST("nearByUserLocationAllCategory")
    Call<HouseNearestByUserResponse> getHouseNearestByUser(@Body HouseNearestByUser houseNearestByUser);

    @GET("getNameCategory/{id}")
    Call<String> getNameCategoryById(@Path("id") String idCategory);
}
