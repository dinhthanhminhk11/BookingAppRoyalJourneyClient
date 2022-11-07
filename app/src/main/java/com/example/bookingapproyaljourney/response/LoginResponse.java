package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.user.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private User user;
    @SerializedName("accessToken")
    private String token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" + "\n"+
                "status=" + status +"\n"+
                ", message='" + message + '\'' +"\n"+
                ", user=" + user +"\n"+
                ", token='" + token + '\'' +"\n"+
                '}';
    }
}
