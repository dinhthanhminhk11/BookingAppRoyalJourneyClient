package com.example.bookingapproyaljourney.response.order;

import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListFilterResponse {
    @SerializedName("datapros")
    private List<Hotel> hotels;
    public List<Hotel> getHotel() {
        return hotels;
    }
}
