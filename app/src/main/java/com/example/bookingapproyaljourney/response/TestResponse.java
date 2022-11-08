package com.example.bookingapproyaljourney.response;

import com.google.gson.annotations.SerializedName;

public class TestResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;

    public TestResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
