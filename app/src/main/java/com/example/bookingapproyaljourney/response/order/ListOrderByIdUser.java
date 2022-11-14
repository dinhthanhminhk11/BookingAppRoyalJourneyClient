package com.example.bookingapproyaljourney.response.order;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListOrderByIdUser {
    @SerializedName("messege")
    private boolean messege;
    @SerializedName("data")
    private List<OrderListResponse> data = new ArrayList<>();

    public boolean isMessege() {
        return messege;
    }

    public List<OrderListResponse> getData() {
        return data;
    }
}
