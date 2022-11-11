package com.example.bookingapproyaljourney.model.chat;

import java.util.List;

public class Data {
    private List<Content> data;

    public Data(List<Content> data) {
        this.data = data;
    }

    public List<Content> getData() {
        return data;
    }

    public void setData(List<Content> data) {
        this.data = data;
    }
}
