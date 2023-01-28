package com.example.bookingapproyaljourney.model.hotel;

public class LocationNearByRequest {
    private double longitude;
    private double latitude;
    private double dist;

    public LocationNearByRequest(double longitude, double latitude, double dist) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = dist;
    }
}
