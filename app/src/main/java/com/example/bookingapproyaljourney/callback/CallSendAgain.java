package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.TestResponse;

public interface CallSendAgain {
    void onResponse(TestResponse testResponse);

    void onFailure(Throwable t);
}
