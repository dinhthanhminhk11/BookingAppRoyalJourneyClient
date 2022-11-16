package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

public class PostIDUserAndIdHouse {
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("idHouse")
    private String idHouse;

    public PostIDUserAndIdHouse(String idUser, String idHouse) {
        this.idUser = idUser;
        this.idHouse = idHouse;
    }
}
