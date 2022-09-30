package com.example.bookingapproyaljourney.model.house;

public class Image {
    private int imagePicture;
    private int imageBackground;

    public Image(int imagePicture, int imageBackground) {
        this.imagePicture = imagePicture;
        this.imageBackground = imageBackground;
    }

    public int getImagePicture() {
        return imagePicture;
    }

    public void setImagePicture(int imagePicture) {
        this.imagePicture = imagePicture;
    }

    public int getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(int imageBackground) {
        this.imageBackground = imageBackground;
    }
}
