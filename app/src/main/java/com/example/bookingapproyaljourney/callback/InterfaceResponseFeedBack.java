package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.model.feedback.FeedBack;

public interface InterfaceResponseFeedBack {
    void onResponse(FeedBack feedBack);

    void onFailure(Throwable t);
}
