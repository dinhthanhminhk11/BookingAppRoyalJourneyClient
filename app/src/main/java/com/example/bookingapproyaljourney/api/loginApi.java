package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface loginApi {
    @POST("")
    Call<UserLogin> loginUser(@Body UserLogin userLogin);

    @POST("")
    Call<User> user(@Body User user);
}
