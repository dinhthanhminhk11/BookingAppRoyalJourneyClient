package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;

public interface CallbackFilter {
    void onResponse(CategoryBestForYouResponse categoryBestForYouResponse);

    void onFailure(Throwable t);
}
