package com.example.bookingapproyaljourney.response.order;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListOrderByIdUser2 {
    @SerializedName("messege")
    private boolean messege;
    @SerializedName("data")
    private List<OrderListResponse2> data = new ArrayList<>();

    public boolean isMessege() {
        return messege;
    }

    public List<OrderListResponse2> getData() {
        return data;
    }
}
