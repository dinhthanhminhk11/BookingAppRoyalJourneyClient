package com.example.bookingapproyaljourney.response.order;

public class OrderListResponse {
    private String idOder;
    private String idHost;
    private String idUser;
    private String idProduct;
    private String namePro;
    private String nameUser;
    private String dateCreate;
    private int day;
    private String price;
    private boolean cashMoney;
    private boolean banking;
    private boolean seem;
    private String startDate;
    private String endDate;
    private int person;
    private String phone;
    private String status;
    private String time;
    private boolean isCancellationDate;
    private boolean isBackingPercent;
    private boolean checkedOut;
    public String getIdOder() {
        return idOder;
    }

    public String getIdHost() {
        return idHost;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public String getNamePro() {
        return namePro;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public int getDay() {
        return day;
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

    public boolean isSeem() {
        return seem;
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

    public String getTime() {
        return time;
    }

    public boolean isCancellationDate() {
        return isCancellationDate;
    }

    public boolean isBackingPercent() {
        return isBackingPercent;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }
}
