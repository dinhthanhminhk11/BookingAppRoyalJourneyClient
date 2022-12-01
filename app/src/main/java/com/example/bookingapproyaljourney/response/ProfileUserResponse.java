package com.example.bookingapproyaljourney.response;

import com.google.gson.annotations.SerializedName;

public class ProfileUserResponse {
    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("idcard")
    private String idcard;

    @SerializedName("address")
    private String address;

    @SerializedName("image")
    private String image;

    private int role;
    private int __v;

    public ProfileUserResponse(String _id, String name, String phone, String idcard, String address, String image, int role, int __v) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.idcard = idcard;
        this.address = address;
        this.image = image;
        this.role = role;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getAddress() {
        return address;
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
