package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.model.order.OrderBill;
import com.example.bookingapproyaljourney.response.order.OrderResponse;

public interface CallbackOrderById {
    void onResponse(OrderBill orderResponse);

    void onFailure(Throwable t);
}
