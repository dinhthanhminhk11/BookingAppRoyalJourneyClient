package com.example.bookingapproyaljourney.model.house;

import com.google.gson.annotations.SerializedName;

public class Bathroom {
    @SerializedName("_id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("content")
    public String content;
    @SerializedName("iconImage")
    public String iconImage;

    public Bathroom(String id, String name, String content, String iconImage) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.iconImage = iconImage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getIconImage() {
        return iconImage;
    }
}
