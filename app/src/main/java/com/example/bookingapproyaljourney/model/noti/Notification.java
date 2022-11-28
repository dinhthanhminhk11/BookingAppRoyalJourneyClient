package com.example.bookingapproyaljourney.model.noti;

public class Notification {
    private String _id;
    private String idOder;
    private String idUser;
    private String title;
    private String content;
    private String imageHoust;
    private String date;
    private String time;
    private boolean isSeem;
    private int __v;

    public String get_id() {
        return _id;
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
