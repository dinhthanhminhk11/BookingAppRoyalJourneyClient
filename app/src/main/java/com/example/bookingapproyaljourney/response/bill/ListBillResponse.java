package com.example.bookingapproyaljourney.response.bill;

public class ListBillResponse {
    private String idBill;
    private String codeBill;
    private String nameHotel;
    private String countPerson;
    private String timeCreate;
    private String imageHotel;
    private String startDate;
    private String endDate;
    private String timeInRoom;
    private String timeOutRoom;
    private int countDay;
    private int price;
    private String status;

    public String getIdBill() {
        return idBill;
    }

    public String getCodeBill() {
        return codeBill;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public String getCountPerson() {
        return countPerson;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public String getImageHotel() {
        return imageHotel;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTimeInRoom() {
        return timeInRoom;
    }

    public String getTimeOutRoom() {
        return timeOutRoom;
    }

    public int getCountDay() {
        return countDay;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
