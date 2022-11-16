package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.house.Bookmark;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BookmarkResponse {
    @SerializedName("message")
    private boolean message;
    @SerializedName("data")
    List<Bookmark> data = new ArrayList<>();

    public boolean isMessage() {
        return message;
    }

    public List<Bookmark> getData() {
        return data;
    }
}
