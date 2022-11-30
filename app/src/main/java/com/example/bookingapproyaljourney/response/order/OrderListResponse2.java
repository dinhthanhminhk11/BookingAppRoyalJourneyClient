package com.example.bookingapproyaljourney.response.order;

import com.google.gson.annotations.SerializedName;

public class OrderListResponse2 {
    public String _id;
    @SerializedName("IdOder")
    public String idOder;
    @SerializedName("IdHost")
    public String idHost;
    @SerializedName("IdUser")
    public String idUser;
    @SerializedName("IdPro")
    public String idPro;
    public String price;
    public int payDay;
    public boolean cashMoney;
    public boolean banking;
    public boolean isBackingPercent;
    public boolean seem;
    public String status;
    public String startDate;
    public String endDate;
    public int person;
    public String phone;
    public String reasonUser;
    public String reasonHost;
    public String pricePercent;
    public String cancellationDate;
    public boolean isCancellationDate;
    public boolean isSuccess;
    public boolean checkedOut;
    public String createdAt;
    public String updatedAt;
    public int __v;

    public String get_id() {
        return _id;
    }

    public String getIdOder() {
        return idOder;
    }

    public String getIdHost() {
        return idHost;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdPro() {
        return idPro;
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

    public boolean isBackingPercent() {
        return isBackingPercent;
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

    public String getReasonUser() {
        return reasonUser;
    }

    public String getReasonHost() {
        return reasonHost;
    }

    public String getPricePercent() {
        return pricePercent;
    }

    public String getCancellationDate() {
        return cancellationDate;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int get__v() {
        return __v;
    }
}
