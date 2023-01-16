package com.example.bookingapproyaljourney.model.hotel;

import com.example.bookingapproyaljourney.model.house.Location;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Hotel {
    private Location location;
    private String _id;
    private String idUser;
    private String name;
    private ArrayList<String> images;
    private String dienTich;
    private String tinh;
    private String huyen;
    private String xa;
    private String sonha;
    private String timeDat;
    private String timeTra;
    @SerializedName("TienNghiKS")
    private ArrayList<TienNghiK> tienNghiKS;
    private Double TbSao;
    private ArrayList<String> imageConfirm;
    private String mota;
    private String chinhsach;
    private boolean yte;
    private int treEm;
    private boolean chinhSachHuy;
    private boolean checkConfirm;
    private int __v;
    private String giaDaoDong;
    private double calculated;

    public Location getLocation() {
        return location;
    }

    public String get_id() {
        return _id;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getDienTich() {
        return dienTich;
    }

    public String getTinh() {
        return tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public String getXa() {
        return xa;
    }

    public String getSonha() {
        return sonha;
    }

    public String getTimeDat() {
        return timeDat;
    }

    public String getTimeTra() {
        return timeTra;
    }

    public ArrayList<TienNghiK> getTienNghiKS() {
        return tienNghiKS;
    }

    public Double getTbSao() {
        return TbSao;
    }

    public ArrayList<String> getImageConfirm() {
        return imageConfirm;
    }

    public String getMota() {
        return mota;
    }

    public String getChinhsach() {
        return chinhsach;
    }

    public boolean isYte() {
        return yte;
    }

    public int getTreEm() {
        return treEm;
    }

    public boolean isChinhSachHuy() {
        return chinhSachHuy;
    }

    public boolean isCheckConfirm() {
        return checkConfirm;
    }

    public int get__v() {
        return __v;
    }

    public String getGiaDaoDong() {
        return giaDaoDong;
    }

    public double getCalculated() {
        return calculated;
    }
}
