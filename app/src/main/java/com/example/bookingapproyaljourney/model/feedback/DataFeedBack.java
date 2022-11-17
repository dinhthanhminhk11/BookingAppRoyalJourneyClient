package com.example.bookingapproyaljourney.model.feedback;

import java.util.List;

public class DataFeedBack {
    private List<FeedBack> data;

    public DataFeedBack(List<FeedBack> data) {
        this.data = data;
    }

    public List<FeedBack> getData() {
        return data;
    }

    public void setData(List<FeedBack> data) {
        this.data = data;
    }
}
