package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class UserRequestTokenDevice {
    @SerializedName("id")
    private String id;
    @SerializedName("tokenDevice")
    private String tokenDevice;

    public UserRequestTokenDevice(String id, String tokenDevice) {
        this.id = id;
        this.tokenDevice = tokenDevice;
    }
}
