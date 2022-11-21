package com.example.bookingapproyaljourney.model.order;

import com.google.gson.annotations.SerializedName;

public class OrderCreate {
    @SerializedName("IdOder")
    private String IdOder;
    @SerializedName("IdHost")
    private String IdHost;
    @SerializedName("IdPro")
    private String IdPro;
    @SerializedName("IdUser")
    private String IdUser;
    @SerializedName("payDay")
    private int payDay;
    @SerializedName("price")
    private String price;
    @SerializedName("cashMoney")
    private boolean cashMoney;
    @SerializedName("banking")
    private boolean banking;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("person")
    private int person;
    @SerializedName("phone")
    private String phone;
    @SerializedName("isBackingPercent")
    private boolean isBackingPercent;
    @SerializedName("pricePercent")
    private String pricePercent;


    public OrderCreate(String idOder, String idHost, String idPro, String idUser, int payDay, String price, String pricePercent, boolean cashMoney, boolean banking, boolean isBackingPercent, String startDate, String endDate, int person, String phone) {
        this.IdOder = idOder;
        this.IdHost = idHost;
        this.IdPro = idPro;
        this.IdUser = idUser;
        this.payDay = payDay;
        this.price = price;
        this.cashMoney = cashMoney;
        this.banking = banking;
        this.startDate = startDate;
        this.endDate = endDate;
        this.person = person;
        this.phone = phone;
        this.isBackingPercent = isBackingPercent;
        this.pricePercent = pricePercent;
    }

    public String getIdOder() {
        return IdOder;
    }

    public String getIdHost() {
        return IdHost;
    }

    public String getIdPro() {
        return IdPro;
    }

    public String getIdUser() {
        return IdUser;
    }

    public int getPayDay() {
        return payDay;
    }

    public String getPrice() {
        return price;
    }

    public boolean isCashMoney() {
        return cashMoney;
    }

    public boolean isBanking() {
        return banking;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getPerson() {
        return person;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isBackingPercent() {
        return isBackingPercent;
    }

    public String getPricePercent() {
        return pricePercent;
    }
}
