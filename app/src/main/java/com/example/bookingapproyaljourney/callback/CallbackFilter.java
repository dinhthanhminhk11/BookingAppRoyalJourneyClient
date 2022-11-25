package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.order.ListFilterResponse;

public interface CallbackFilter {
    void onResponse(ListFilterResponse listFilterResponse);

    void onFailure(Throwable t);
}
