package com.example.bookingapproyaljourney.response.order;

public class OrderResponse {
    private boolean messege;
    private String idBill;
    private String idPro;
    private String idUser;
    private String idHost;
    private String price;
    private int payDay;
    private boolean cashMoney;
    private boolean banking;
    private String startDate;
    private String endDate;
    private int person;
    private String phone;
    private String status;
    private String date;
    private String time;
    private boolean isBackingPercent;
    private String pricePercent;

    public OrderResponse(boolean messege, String idBill, String idPro, String idUser, String idHost, String price, String pricePercent, int payDay, boolean cashMoney, boolean banking, boolean isBackingPercent, String startDate, String endDate, int person, String phone, String status, String date, String time) {
        this.messege = messege;
        this.idBill = idBill;
        this.idPro = idPro;
        this.idUser = idUser;
        this.idHost = idHost;
        this.price = price;
        this.payDay = payDay;
        this.cashMoney = cashMoney;
        this.banking = banking;
        this.startDate = startDate;
        this.endDate = endDate;
        this.person = person;
        this.phone = phone;
        this.status = status;
        this.date = date;
        this.time = time;
        this.isBackingPercent = isBackingPercent;
        this.pricePercent = pricePercent;
    }

    public boolean isMessege() {
        return messege;
    }

    public String getIdBill() {
        return idBill;
    }

    public String getIdPro() {
        return idPro;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdHost() {
        return idHost;
    }

    public String getPrice() {
        return price;
    }

    public int getPayDay() {
        return payDay;
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

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isBackingPercent() {
        return isBackingPercent;
    }

    public String getPricePercent() {
        return pricePercent;
    }
}
