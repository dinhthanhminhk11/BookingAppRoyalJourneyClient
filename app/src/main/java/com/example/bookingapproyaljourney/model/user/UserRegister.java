package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class UserRegister {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("tokenDevice")
    private String tokenDevice;

    public UserRegister(String name, String email, String password, String tokenDevice) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.tokenDevice = tokenDevice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }
}
