package com.example.bookingapproyaljourney.response;

import com.google.gson.annotations.SerializedName;

public class CountNotiResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("size")
    private int size;

    public boolean isStatus() {
        return status;
    }

    public int getSize() {
        return size;
    }
}
