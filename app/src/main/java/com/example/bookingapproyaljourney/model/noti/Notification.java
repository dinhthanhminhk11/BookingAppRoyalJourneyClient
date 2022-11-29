package com.example.bookingapproyaljourney.model.noti;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("_id")
    private String id;
    private String idOder;
    private String idUser;
    private String title;
    private String content;
    private String imageHoust;
    private String date;
    private String time;
    private boolean isSeem;
    private int __v;

    public String getId() {
        return id;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageHoust() {
        return imageHoust;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isSeem() {
        return isSeem;
    }

    public int get__v() {
        return __v;
    }

    public String getIdOder() {
        return idOder;
    }
}
