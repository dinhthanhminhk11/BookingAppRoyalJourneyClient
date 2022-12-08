package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class UserEditProfileRequest {

    @SerializedName("id")
    private String id;
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

    public UserEditProfileRequest(String id, String name, String phone, String idcard, String address, String image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.idcard = idcard;
        this.address = address;
        this.image = image;
    }

    public String getid() {
        return id;
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

}

