package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

public class PostIDUserAndIdHouse {
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("idHotel")
    private String idHotel;

    public PostIDUserAndIdHouse(String idUser, String idHotel) {
        this.idUser = idUser;
        this.idHotel = idHotel;
    }
}
