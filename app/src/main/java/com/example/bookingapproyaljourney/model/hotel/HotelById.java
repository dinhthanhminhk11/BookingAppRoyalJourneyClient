package com.example.bookingapproyaljourney.model.hotel;

import com.example.bookingapproyaljourney.model.user.Host;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HotelById {
    @SerializedName("dataHotel")
    private Hotel dataHotel;
    @SerializedName("dataRoom")
    private ArrayList<Room> dataRoom;
    @SerializedName("dataUser")
    private Host dataUser;

    public Hotel getDataHotel() {
        return dataHotel;
    }

    public ArrayList<Room> getDataRoom() {
        return dataRoom;
    }

    public Host getDataUser() {
        return dataUser;
    }
}
