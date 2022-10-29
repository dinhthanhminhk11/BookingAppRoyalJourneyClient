package com.example.bookingapproyaljourney.model.house;

import com.example.bookingapproyaljourney.R;

public class Convenient {
    private long id;
    private String name;
    private int image = R.drawable.ic_ellipse_96;

    public Convenient(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Convenient(long id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
