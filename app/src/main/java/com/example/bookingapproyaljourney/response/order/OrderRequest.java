package com.example.bookingapproyaljourney.response.order;

public class OrderRequest {
    private String id;
    private String status;
    private String reasonUser;
    private String cancellationDate;

    public OrderRequest(String id, String status, String reasonUser, String cancellationDate) {
        this.id = id;
        this.status = status;
        this.reasonUser = reasonUser;
        this.cancellationDate = cancellationDate;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getReasonUser() {
        return reasonUser;
    }
}
