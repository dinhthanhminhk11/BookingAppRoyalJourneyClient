package com.example.bookingapproyaljourney.callback;

public interface CallbackCashPay {
    void success(String result);

    void failure(Throwable t);
}
