package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.noti.Notification;

import java.util.List;

public class NotiResponse {
    public boolean messege;
    public List<Notification> data;

    public boolean isMessege() {
        return messege;
    }

    public List<Notification> getData() {
        return data;
    }
}
