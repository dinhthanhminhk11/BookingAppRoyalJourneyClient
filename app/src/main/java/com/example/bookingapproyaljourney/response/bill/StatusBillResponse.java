package com.example.bookingapproyaljourney.response.bill;

import com.example.bookingapproyaljourney.model.hotel.Bedroom;

import java.io.Serializable;
import java.util.ArrayList;

public class StatusBillResponse implements Serializable {
    private String idOrder;
    private String nameHouse;
    private int startHotel;
    private String imageHotel;
    private boolean policyHotel;
    private String nameRoom;
    private ArrayList<Bedroom> bedroom;
    private int priceRoom;
    private String startDate;
    private String endDate;
    private int payDay;
    private int countRoom;
    private String numberGuests;
    private String phone;
    private String specialRequirements;
    private int priceAll;
    private boolean cashMoney;
    private boolean banking;
    private boolean seem;
    private String status;
    private String reasonUser;
    private String reasonHost;
    private String checkDataCancel;
    private boolean isCancellationDate;
    private boolean isSuccess;
    private boolean checkedOut;
    private String dateCreate;
    private String timeCreate;
    private String idHotel;
    private String nameHost;
    private String imageHost;


    public String getIdHotel() {
        return idHotel;
    }

    public String getNameHost() {
        return nameHost;
    }

    public String getImageHost() {
        return imageHost;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getNameHouse() {
        return nameHouse;
    }

    public int getStartHotel() {
        return startHotel;
    }

    public String getImageHotel() {
        return imageHotel;
    }

    public boolean isPolicyHotel() {
        return policyHotel;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public ArrayList<Bedroom> getBedroom() {
        return bedroom;
    }

    public int getPriceRoom() {
        return priceRoom;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getPayDay() {
        return payDay;
    }

    public int getCountRoom() {
        return countRoom;
    }

    public String getNumberGuests() {
        return numberGuests;
    }

    public String getPhone() {
        return phone;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public int getPriceAll() {
        return priceAll;
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

    public String getReasonUser() {
        return reasonUser;
    }

    public String getReasonHost() {
        return reasonHost;
    }

    public boolean isCancellationDate() {
        return isCancellationDate;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public String getCheckDataCancel() {
        return checkDataCancel;
    }
}
