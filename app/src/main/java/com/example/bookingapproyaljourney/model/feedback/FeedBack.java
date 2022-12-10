package com.example.bookingapproyaljourney.model.feedback;

public class FeedBack{
    private String idHouse;
    private String idUser;
    private String imgUser;
    private String name;
    private String email;
    private int sao;
    private String time;
    private String textUser;
    private String textHost;

    public FeedBack(String idHouse, String idUser, String imgUser, String name, String email, int sao, String time, String textUser, String textHost) {
        this.idHouse = idHouse;
        this.idUser = idUser;
        this.imgUser = imgUser;
        this.name = name;
        this.email = email;
        this.sao = sao;
        this.time = time;
        this.textUser = textUser;
        this.textHost = textHost;
    }

    public String getIdHouse() {
        return idHouse;
    }

    public void setIdHouse(String idHouse) {
        this.idHouse = idHouse;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSao() {
        return sao;
    }

    public void setSao(int sao) {
        this.sao = sao;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTextUser() {
        return textUser;
    }

    public void setTextUser(String textUser) {
        this.textUser = textUser;
    }

    public String getTextHost() {
        return textHost;
    }

    public void setTextHost(String textHost) {
        this.textHost = textHost;
    }
}
