package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

public class SleepingPlace {
    @SerializedName("_id")
    private String id;
    @SerializedName("bed")
    private String bed;
    @SerializedName("bedroom")
    private String bedroom;
    @SerializedName("iconImage")
    private String iconImage;

    public SleepingPlace(String id, String bed, String bedroom, String iconImage) {
        this.id = id;
        this.bed = bed;
        this.bedroom = bedroom;
        this.iconImage = iconImage;
    }

    public String getId() {
        return id;
    }

    public String getBed() {
        return bed;
    }

    public String getBedroom() {
        return bedroom;
    }

    public String getIconImage() {
        return iconImage;
    }
}
