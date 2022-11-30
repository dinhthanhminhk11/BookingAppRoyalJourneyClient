package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser2;

public interface CallbackListOrderAccessById {
    void success(ListOrderByIdUser2 listOrderByIdUser);

    void failure(Throwable t);
}
