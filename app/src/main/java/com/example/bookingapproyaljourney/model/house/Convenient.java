package com.example.bookingapproyaljourney.model.house;

import com.example.bookingapproyaljourney.R;
import com.google.gson.annotations.SerializedName;

public class Convenient {
    @SerializedName("id")
    public String _id;
    @SerializedName("name")
    public String name;
    @SerializedName("iconImage")
    public String iconImage;

    private int imageCheck = R.drawable.ic_ellipse_96;

    public Convenient(String _id, String name, String iconImage) {
        this._id = _id;
        this.name = name;
        this.iconImage = iconImage;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getIconImage() {
        return iconImage;
    }

    public int getImageCheck() {
        return imageCheck;
    }
}
