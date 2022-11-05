package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataMap {
    @SerializedName("data")
    private House data;
    @SerializedName("distance")
    private int distance;

    public House getData() {
        return data;
    }

    public int getDistance() {
        return distance;
    }
}
