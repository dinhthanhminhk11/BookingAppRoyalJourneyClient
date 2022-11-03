package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;

public interface HouseByCategoryCallback {
    void success(CategoryBestForYouResponse categoryBestForYouResponse);

    void failure(Throwable t);
}
