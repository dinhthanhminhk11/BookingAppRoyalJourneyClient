package com.example.bookingapproyaljourney.response.order;

public class OrderRequest {
    private String id;
    private String status;
    private String reasonUser;

    public OrderRequest(String id, String status, String reasonUser) {
        this.id = id;
        this.status = status;
        this.reasonUser = reasonUser;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
