package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.BookmarkResponse;

public interface InterfaceBookmark {
    void onResponse(BookmarkResponse bookmarkResponse);

    void onFailure(Throwable t);
}
