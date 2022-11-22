package com.example.bookingapproyaljourney.model.user;

import com.google.gson.annotations.SerializedName;

public class User {
    private String _id;
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
    @SerializedName("countBooking")
    private int countBooking;

    public User() {
    }

    public String getId() {
        return id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public int getCountBooking() {
        return countBooking;
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
