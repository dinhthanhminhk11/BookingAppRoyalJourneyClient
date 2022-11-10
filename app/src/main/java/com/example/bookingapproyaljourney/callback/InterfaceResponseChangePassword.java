package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.TestResponse;

public interface InterfaceResponseChangePassword {
    void onResponseCheckMail(TestResponse testResponse);
    void onFailure(Throwable t);
}
