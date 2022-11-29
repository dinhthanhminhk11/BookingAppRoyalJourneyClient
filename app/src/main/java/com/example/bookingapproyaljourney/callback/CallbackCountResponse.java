package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.CountNotiResponse;

public interface CallbackCountResponse {
    void onResponse(CountNotiResponse countNotiResponse);

    void onFailure(Throwable t);
}
