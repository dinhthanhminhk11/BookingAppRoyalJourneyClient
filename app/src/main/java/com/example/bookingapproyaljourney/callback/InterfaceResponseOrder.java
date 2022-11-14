package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.order.OrderResponse;

public interface InterfaceResponseOrder {
    void onResponse(OrderResponse orderResponse);

    void onFailure(Throwable t);
}
