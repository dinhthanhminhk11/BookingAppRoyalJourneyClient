package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.HouseDetailResponse;

public interface CallbackHouseById {
    void success(HouseDetailResponse houseDetailResponse);

    void failure(Throwable t);
}
