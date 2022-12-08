package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.TestResponse;

public interface CallBackEditProfile {
    void onResponse(TestResponse testResponse);

    void onFailure(Throwable t);
}
