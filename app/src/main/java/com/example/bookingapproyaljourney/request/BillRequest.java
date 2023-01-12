package com.example.bookingapproyaljourney.request;

public class BillRequest {
    private String idHost;
    private String idUser;
    private String idHotel;
    private String idRoom;
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
    private String refundDate;


    public BillRequest(String idHost, String idUser, String idHotel, String idRoom, String startDate, String endDate, int payDay, int countRoom, String numberGuests, String phone, String specialRequirements, int priceAll, boolean cashMoney, boolean banking, String refundDate) {
        this.idHost = idHost;
        this.idUser = idUser;
        this.idHotel = idHotel;
        this.idRoom = idRoom;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payDay = payDay;
        this.countRoom = countRoom;
        this.numberGuests = numberGuests;
        this.phone = phone;
        this.specialRequirements = specialRequirements;
        this.priceAll = priceAll;
        this.cashMoney = cashMoney;
        this.banking = banking;
        this.refundDate = refundDate;
    }
}

