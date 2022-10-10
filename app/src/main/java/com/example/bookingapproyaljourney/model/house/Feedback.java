package com.example.bookingapproyaljourney.model.house;

public class Feedback {
    private String title;
    private String content;
    private String date;
    private String nameUser;
    private int imageUser;

    public Feedback(String title, String content, String date, String nameUser, int imageUser) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.nameUser = nameUser;
        this.imageUser = imageUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getImageUser() {
        return imageUser;
    }

    public void setImageUser(int imageUser) {
        this.imageUser = imageUser;
    }

}
