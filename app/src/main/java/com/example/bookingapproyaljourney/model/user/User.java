package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String image;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("otp")
    private String otp;
    @SerializedName("active")
    private boolean active;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtp() {
        return otp;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", email='" + email + '\'' + "\n" +
                ", image='" + image + '\'' + "\n" +
                ", phone='" + phone + '\'' + "\n" +
                ", address='" + address + '\'' + "\n" +
                '}';
    }
}
