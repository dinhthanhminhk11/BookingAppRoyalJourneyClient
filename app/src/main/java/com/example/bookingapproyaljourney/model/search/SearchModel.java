package com.example.bookingapproyaljourney.model.search;

public class SearchModel {
    private String idHotel;
    private String nameHotel;
    private String address ;
    private int type;
    private String imageHotel;

    public SearchModel(String nameHotel, int type) {
        this.nameHotel = nameHotel;
        this.type = type;
    }

    public SearchModel(String idHotel, String nameHotel, String address, int type) {
        this.idHotel = idHotel;
        this.nameHotel = nameHotel;
        this.address = address;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getImageHotel() {
        return imageHotel;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public String getAddress() {
        return address;
    }
}
