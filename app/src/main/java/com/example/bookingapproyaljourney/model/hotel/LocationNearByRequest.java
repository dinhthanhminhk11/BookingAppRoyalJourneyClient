package com.example.bookingapproyaljourney.model.hotel;

public class LocationNearByRequest {
    private double longitude;
    private double latitude;
    private double dist;
    private int treEm;

    public LocationNearByRequest(double longitude, double latitude, double dist, int treEm) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = dist;
        this.treEm = treEm;
    }

    public LocationNearByRequest(double longitude, double latitude, double dist) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dist = dist;
    }
}
