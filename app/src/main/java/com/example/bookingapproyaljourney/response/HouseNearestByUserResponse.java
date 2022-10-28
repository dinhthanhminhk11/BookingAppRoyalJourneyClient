package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.house.DataMaps;
import com.example.bookingapproyaljourney.model.house.House;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HouseNearestByUserResponse {
    @SerializedName("messege")
    private String message;
    @SerializedName("dataMaps")
    private List<DataMaps> dataMaps;

    public String getMessage() {
        return message;
    }

    public List<DataMaps> getDataMaps() {
        return dataMaps;
    }
}
