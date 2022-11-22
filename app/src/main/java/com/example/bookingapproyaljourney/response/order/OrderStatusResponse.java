package com.example.bookingapproyaljourney.response.order;

import com.google.gson.annotations.SerializedName;

public class OrderStatusResponse {
    @SerializedName("messege")
    private boolean messege;
    @SerializedName("data")
    private OrderListResponse orderListResponse;

    public boolean isMessege() {
        return messege;
    }

    public OrderListResponse getOrderListResponse() {
        return orderListResponse;
    }
}
