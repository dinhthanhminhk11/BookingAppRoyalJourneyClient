package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;

public interface CallbackListOrderById {
    void success(ListOrderByIdUser listOrderByIdUser);

    void failure(Throwable t);
}
