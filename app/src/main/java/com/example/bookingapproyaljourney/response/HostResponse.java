package com.example.bookingapproyaljourney.response;

import com.google.gson.annotations.SerializedName;

public class HostResponse {
    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("address")
    private String address;
    @SerializedName("idcard")
    private String idCard;
    @SerializedName("image")
    private String image;
    private int role;
    private int __v;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getImage() {
        return image;
    }

    public int getRole() {
        return role;
    }

    public int get__v() {
        return __v;
    }
}
