package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.house.DataMap;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HouseNearestByUserResponse {
    @SerializedName("messege")
    private String message;
    @SerializedName("dataMaps")
    private List<DataMap> dataMaps;

    public String getMessage() {
        return message;
    }

    public List<DataMap> getDataMaps() {
        return dataMaps;
    }
}
