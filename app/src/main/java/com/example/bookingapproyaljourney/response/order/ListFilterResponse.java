package com.example.bookingapproyaljourney.response.order;

import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListFilterResponse {
    @SerializedName("datapros")
    private List<House> houses;
    public List<House> getHouses() {
        return houses;
    }
}
