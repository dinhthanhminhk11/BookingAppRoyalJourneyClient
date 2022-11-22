package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;

public interface CallbackEditOrderByUser {
    void success(OrderStatusResponse orderStatusResponse);

    void failure(Throwable t);
}
