package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;

import java.util.List;

public interface UpdateRecyclerView {
    void callbacksNearFromYou(int position, HouseNearestByUserResponse houseNearestByUserResponse);

    void callbacksBestForYou(int position, CategoryBestForYouResponse categoryBestForYouResponse);

    void callLoading(int view);

    void callBackNull(CategoryBestForYouResponse categoryBestForYouResponse);
}
