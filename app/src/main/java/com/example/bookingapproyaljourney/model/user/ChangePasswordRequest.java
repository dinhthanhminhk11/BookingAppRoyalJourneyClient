package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {
    @SerializedName("id")
    private String id;
    @SerializedName("password")
    private String password;

    @SerializedName("passwordNew")
    private String passwordNew;

    public ChangePasswordRequest(String id, String password, String passwordNew) {
        this.id = id;
        this.password = password;
        this.passwordNew = passwordNew;
    }
}
