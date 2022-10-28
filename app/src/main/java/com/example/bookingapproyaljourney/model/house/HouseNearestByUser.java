package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

public class HouseNearestByUser {
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("dist")
    private int dist;
    @SerializedName("idCategory")
    private String idCategory;

    public HouseNearestByUser(double longitude, double latitude ,int dist) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = dist;
    }

    public HouseNearestByUser(double longitude, double latitude,  String idCategory) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = 10000;
        this.idCategory = idCategory;
    }


}
