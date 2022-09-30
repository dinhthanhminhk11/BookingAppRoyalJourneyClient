package com.example.bookingapproyaljourney.model.house;

public class Location {
    private double log;
    private double lat;

    public Location(double log, double lat) {
        this.log = log;
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
