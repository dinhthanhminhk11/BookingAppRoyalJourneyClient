package com.example.bookingapproyaljourney.response;

import com.example.bookingapproyaljourney.model.house.Bathroom;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.model.house.Location;
import com.example.bookingapproyaljourney.model.house.SleepingPlace;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HouseDetailResponse {
    @SerializedName("location")
    private Location location;
    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("images")
    private List<String> images;
    @SerializedName("price")
    private int price;
    @SerializedName("supplement")
    private List<Convenient> supplement;
    @SerializedName("nameLocation")
    private String nameLocation;
    @SerializedName("category")
    private Category category;
    @SerializedName("hostResponse")
    private HostResponse hostResponse;
    @SerializedName("legal")
    private String legal;
    @SerializedName("content")
    private String content;
    @SerializedName("yte")
    private boolean yte;
    @SerializedName("bathrooms")
    private List<Bathroom> bathrooms;
    @SerializedName("limitPerson")
    private int limitPerson;
    @SerializedName("sleepingPlaces")
    private List<SleepingPlace> sleepingPlaces;
    @SerializedName("opening")
    private String opening;
    @SerializedName("ending")
    private String ending;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("__v")
    private int __v;

    public Location getLocation() {
        return location;
    }

    public String get_id() {
        return _id;
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

    public Category getCategory() {
        return category;
    }

    public HostResponse getHostResponse() {
        return hostResponse;
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

    public int get__v() {
        return __v;
    }
}
