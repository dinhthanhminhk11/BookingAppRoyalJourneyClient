package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;

public interface CallbackDeleteOrder {
    void onResponse(OrderStatusResponse orderStatusResponse);

    void onFailure(Throwable t);
}
