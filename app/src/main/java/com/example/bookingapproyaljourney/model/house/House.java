package com.example.bookingapproyaljourney.model.house;

public class House {
    private long id;
    private String name;
    private String address;
    private int image;
    private String distance;
    private double price;
    private int countBedroom;
    private int countBathroom;
    private int start;
    private double lat;
    private double log;
//    private Location location;

    public House() {
    }

    public House(long id, String name,int start ,String address , double price, double lat, double log) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.start = start;
        this.lat = lat;
        this.log = log;
    }

    public House(long id, String name, String address, int image, String distance, double price, int countBedroom, int countBathroom, int start) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.distance = distance;
        this.price = price;
        this.countBedroom = countBedroom;
        this.countBathroom = countBathroom;
        this.start = start;
//        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCountBedroom() {
        return countBedroom;
    }

    public void setCountBedroom(int countBedroom) {
        this.countBedroom = countBedroom;
    }

    public int getCountBathroom() {
        return countBathroom;
    }

    public void setCountBathroom(int countBathroom) {
        this.countBathroom = countBathroom;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
