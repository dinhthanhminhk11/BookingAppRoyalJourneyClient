package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;

public interface InterfaceResponseHouseNearestByUser {
    void onResponse(HouseNearestByUserResponse houseNearestByUserResponse);

    void onFailure(Throwable t);
}
