package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.model.order.OrderBill;

public interface CallBackString {
    void onResponse(String string);

    void onFailure(Throwable t);
}
