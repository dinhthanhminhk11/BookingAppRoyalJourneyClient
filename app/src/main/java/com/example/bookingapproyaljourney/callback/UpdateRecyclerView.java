package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;

import java.util.List;

public interface UpdateRecyclerView {
    public void callbacksNearFromYou(int position, HouseNearestByUserResponse houseNearestByUserResponse);

    public void callbacksBestForYou(int position, CategoryBestForYouResponse categoryBestForYouResponse);
}
