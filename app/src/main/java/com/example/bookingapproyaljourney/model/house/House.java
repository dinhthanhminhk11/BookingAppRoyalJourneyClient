package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class House {
    @SerializedName("_id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("images")
    public List<String> images;
    @SerializedName("price")
    public int price;
    @SerializedName("supplement")
    public List<Convenient> supplement;
    @SerializedName("nameLocation")
    public String nameLocation;
    @SerializedName("location")
    public Location location;
    @SerializedName("category")
    public String idCategory;
    @SerializedName("user")
    public String idUser;
    @SerializedName("legal")
    public String legal;
    @SerializedName("content")
    public String content;
    @SerializedName("yte")
    public boolean yte;
    @SerializedName("bathrooms")
    public List<Bathroom> bathrooms;
    @SerializedName("limitPerson")
    public int limitPerson;
    @SerializedName("sleepingPlaces")
    public List<SleepingPlace> sleepingPlaces;
    @SerializedName("opening")
    public String opening;
    @SerializedName("ending")
    public String ending;
    @SerializedName("createdAt")
    public String createdAt;
    @SerializedName("updatedAt")
    public String updatedAt;
    @SerializedName("__v")
    public int v;
    @SerializedName("calculated")
    public double calculated;
    public Double sao;

    public Double getSao() {
        return sao;
    }

    public House(String id, String name, List<String> images, int price, List<Convenient> supplement, String nameLocation, Location location, String idCategory, String idUser, String legal, String content, boolean yte, List<Bathroom> bathrooms, int limitPerson, List<SleepingPlace> sleepingPlaces, String opening, String ending, String createdAt, String updatedAt, int v, double calculated) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.price = price;
        this.supplement = supplement;
        this.nameLocation = nameLocation;
        this.location = location;
        this.idCategory = idCategory;
        this.idUser = idUser;
        this.legal = legal;
        this.content = content;
        this.yte = yte;
        this.bathrooms = bathrooms;
        this.limitPerson = limitPerson;
        this.sleepingPlaces = sleepingPlaces;
        this.opening = opening;
        this.ending = ending;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.v = v;
        this.calculated = calculated;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getImages() {
        return images;
    }

    public int getPrice() {
        return price;
    }

    public List<Convenient> getSupplement() {
        return supplement;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public Location getLocation() {
        return location;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getLegal() {
        return legal;
    }

    public String getContent() {
        return content;
    }

    public boolean isYte() {
        return yte;
    }

    public List<Bathroom> getBathrooms() {
        return bathrooms;
    }

    public int getLimitPerson() {
        return limitPerson;
    }

    public List<SleepingPlace> getSleepingPlaces() {
        return sleepingPlaces;
    }

    public String getOpening() {
        return opening;
    }

    public String getEnding() {
        return ending;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getV() {
        return v;
    }

    public double getCalculated() {
        return calculated;
    }
}
