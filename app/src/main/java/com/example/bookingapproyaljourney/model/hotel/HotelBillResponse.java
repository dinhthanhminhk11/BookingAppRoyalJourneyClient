package com.example.bookingapproyaljourney.model.hotel;

import java.util.ArrayList;

public class HotelBillResponse {
    private String idHotel;
    private String idHost;
    private String nameHotel;
    private String addressHotel;
    private int startHotel;
    private String imageHotel;
    private boolean policyHotel;
    private int ageChildren;
    private String idRoom;
    private String nameRoom;
    private ArrayList<Bedroom> bedroom;
    private int priceRoom;
    private int countRoom;
    private int maxPeople;
    private int maxChildren;
    private String dateCancel;

    public String getIdHost() {
        return idHost;
    }

    public String getDateCancel() {
        return dateCancel;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public String getAddressHotel() {
        return addressHotel;
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

    public int getAgeChildren() {
        return ageChildren;
    }

    public String getIdRoom() {
        return idRoom;
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

    public int getCountRoom() {
        return countRoom;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public int getMaxChildren() {
        return maxChildren;
    }
}
