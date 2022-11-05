package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;

public interface InterfaceResponseHouseNearestByUser {
    void onResponse(HouseNearestByUserResponse houseNearestByUserResponse);

    void onFailure(Throwable t);
}
