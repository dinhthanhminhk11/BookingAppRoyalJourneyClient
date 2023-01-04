package com.example.bookingapproyaljourney.model.cash;

public class CashFolwRequest {
    private String idUser;
    private boolean status;
    private String title;
    private String content;
    private String price;

    public CashFolwRequest(String idUser, boolean status, String title, String content, String price) {
        this.idUser = idUser;
        this.status = status;
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
