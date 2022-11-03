package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryBestForYouResponse {
    @SerializedName("data")
    private List<Category> categories;
    @SerializedName("product")
    private List<House> houses;

    public List<Category> getCategories() {
        return categories;
    }

    public List<House> getHouses() {
        return houses;
    }
}
