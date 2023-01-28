package com.example.bookingapproyaljourney.callback;

public interface CallbackObject {
    void onResponse(Object object);

    void onFailure(Throwable t);
}
