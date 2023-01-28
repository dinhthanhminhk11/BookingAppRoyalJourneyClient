package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.TestResponse;

public interface CallbackTextResponse {
    void onResponse(TestResponse testResponse);

    void onFailure(Throwable t);
}
