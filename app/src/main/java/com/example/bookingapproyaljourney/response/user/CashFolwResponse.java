package com.example.bookingapproyaljourney.response.user;

import com.google.gson.annotations.SerializedName;

public class CashFolwResponse {
    @SerializedName("title")
   private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("dateTime")
    private String dateTime;
    @SerializedName("price")
    private String price;
    @SerializedName("status")
    private boolean status;

    public CashFolwResponse(String title, String content, String dataTime, String price, boolean status) {
        this.title = title;
        this.content = content;
        this.dateTime = dataTime;
        this.price = price;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDataTime() {
        return dateTime;
    }

    public String getPrice() {
        return price;
    }

    public boolean isStatus() {
        return status;
    }
}
