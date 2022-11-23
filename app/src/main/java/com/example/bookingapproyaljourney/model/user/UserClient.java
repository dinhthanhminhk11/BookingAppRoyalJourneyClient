package com.example.bookingapproyaljourney.model.user;

public class UserClient {
    private String id;
    private String name;
    private String email;
    private String image;
    private String phone;
    private String address;
    private int countBooking;
    private static UserClient instance = null;

    protected UserClient() {
    }

    public static UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
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

    public static void setInstance(UserClient instance) {
        UserClient.instance = instance;
    }

    public void setCountBooking(int countBooking) {
        this.countBooking = countBooking;
    }

    public int getCountBooking() {
        return countBooking;
    }
}
