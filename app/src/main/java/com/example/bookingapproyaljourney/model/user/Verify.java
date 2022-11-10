package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class Verify {
    @SerializedName("email")
    private String email;
    @SerializedName("otp")
    private String otp;

    public Verify(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
}
