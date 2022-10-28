package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {
    @SerializedName("type")
    private String type;
    @SerializedName("coordinates")
    private List<Double> coordinates;

    public Location(String type, List<Double> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }
}
