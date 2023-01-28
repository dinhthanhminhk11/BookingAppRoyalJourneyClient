package com.example.bookingapproyaljourney.response.bill;

import com.example.bookingapproyaljourney.model.bill.Bill;
import com.google.gson.annotations.SerializedName;

public class BillResponse {
    public boolean message;
    @SerializedName("dataOrder")
    public Bill bill;

    public boolean isMessage() {
        return message;
    }

    public Bill getBill() {
        return bill;
    }
}
