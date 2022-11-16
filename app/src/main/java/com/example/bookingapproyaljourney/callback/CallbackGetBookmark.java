package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.BookmarkResponse;

public interface CallbackGetBookmark {
    void onResponse(BookmarkResponse bookmarkResponse);

    void onFailure(BookmarkResponse bookmarkResponse);
}
