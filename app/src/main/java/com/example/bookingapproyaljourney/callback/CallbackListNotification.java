package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.NotiResponse;

public interface CallbackListNotification {
    void success(NotiResponse notiResponse);

    void failure(Throwable t);
}
