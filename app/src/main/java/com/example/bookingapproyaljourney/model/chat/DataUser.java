package com.example.bookingapproyaljourney.model.chat;

import com.example.bookingapproyaljourney.model.user.User;

import java.util.List;

public class DataUser {
    private List<User> data;

    public DataUser(List<User> data) {
        this.data = data;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
