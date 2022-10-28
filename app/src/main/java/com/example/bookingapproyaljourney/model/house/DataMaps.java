package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataMaps {
    @SerializedName("data")
    private House data;
    @SerializedName("data")
    private int distance;

    public House getData() {
        return data;
    }

    public int getDistance() {
        return distance;
    }
}
