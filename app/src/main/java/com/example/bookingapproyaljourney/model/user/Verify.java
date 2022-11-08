package com.example.bookingapproyaljourney.model.user;

public class Verify {
    private String email;
    private String otp;

    public Verify(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
}
