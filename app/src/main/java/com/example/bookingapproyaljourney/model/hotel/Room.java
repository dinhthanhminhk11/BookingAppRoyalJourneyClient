package com.example.bookingapproyaljourney.model.hotel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Room {
    private String _id;
    private String idHotel;
    private String name;
    private ArrayList<String> images;
    private int price;
    private String dienTich;
    private String timeDat;
    private String timeTra;
    @SerializedName("TienNghiPhong")
    private ArrayList<TienNghiK> tienNghiPhong;
    private ArrayList<Bedroom> bedroom;
    @SerializedName("MaxNguoiLon")
    private int maxNguoiLon;
    @SerializedName("MaxTreEm")
    private int maxTreEm;
    @SerializedName("SoPhong")
    private int soPhong;
    private String mota;
    private int __v;

    public String get_id() {
        return _id;
    }

    public String getIdHotel() {
        return idHotel;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public int getPrice() {
        return price;
    }

    public String getDienTich() {
        return dienTich;
    }

    public String getTimeDat() {
        return timeDat;
    }

    public String getTimeTra() {
        return timeTra;
    }

    public ArrayList<TienNghiK> getTienNghiPhong() {
        return tienNghiPhong;
    }

    public ArrayList<Bedroom> getBedroom() {
        return bedroom;
    }

    public int getMaxNguoiLon() {
        return maxNguoiLon;
    }

    public int getMaxTreEm() {
        return maxTreEm;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public String getMota() {
        return mota;
    }

    public int get__v() {
        return __v;
    }
}
