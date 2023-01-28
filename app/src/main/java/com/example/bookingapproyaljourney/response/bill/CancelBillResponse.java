package com.example.bookingapproyaljourney.response.bill;

public class CancelBillResponse {
    private String imageHost;
    private String nameHost;
    private boolean checkDataCancel;
    private String dataCancelByUser;

    public String getImageHost() {
        return imageHost;
    }

    public String getNameHost() {
        return nameHost;
    }

    public boolean isCheckDataCancel() {
        return checkDataCancel;
    }

    public String getDataCancelByUser() {
        return dataCancelByUser;
    }
}
