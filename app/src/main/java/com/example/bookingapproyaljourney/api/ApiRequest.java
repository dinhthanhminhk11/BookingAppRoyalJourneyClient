package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.response.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("api/listCategory")
    Call<List<Category>> getCategory();
}
