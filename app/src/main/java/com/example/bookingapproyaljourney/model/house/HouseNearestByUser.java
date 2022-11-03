package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

public class HouseNearestByUser {
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("dist")
    private int dist;
    @SerializedName("category")
    private String idCategory;

    public HouseNearestByUser(double longitude, double latitude ,int dist) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = dist;
    }

    public HouseNearestByUser(double longitude, double latitude, int dist, String idCategory) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = dist;
        this.idCategory = idCategory;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getDist() {
        return dist;
    }

    public String getIdCategory() {
        return idCategory;
    }
}
