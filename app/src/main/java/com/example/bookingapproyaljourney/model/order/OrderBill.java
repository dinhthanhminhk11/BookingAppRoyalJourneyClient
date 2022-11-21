package com.example.bookingapproyaljourney.model.order;

public class OrderBill {
    private String _id;
    private String IdOder;
    private String IdHost;
    private String IdUser;
    private String IdPro;
    private String price;
    private int payDay;
    private boolean cashMoney;
    private boolean banking;
    private boolean isBackingPercent;
    private boolean seem;
    private String status;
    private String startDate;
    private String endDate;
    private int person;
    private String phone;
    private String createdAt;
    private String updatedAt;
    private int __v;
    private String reasonHost;
    private String reasonUser;

    public String getReasonHost() {
        return reasonHost;
    }

    public String getReasonUser() {
        return reasonUser;
    }

    public String get_id() {
        return _id;
    }

    public String getIdOder() {
        return IdOder;
    }

    public String getIdHost() {
        return IdHost;
    }

    public String getIdUser() {
        return IdUser;
    }

    public String getIdPro() {
        return IdPro;
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

    public boolean isSeem() {
        return seem;
    }

    public String getStatus() {
        return status;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public boolean isBackingPercent() {
        return isBackingPercent;
    }
}
