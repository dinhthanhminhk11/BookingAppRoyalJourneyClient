package com.example.bookingapproyaljourney.callback;

import com.example.bookingapproyaljourney.response.user.CashFolwResponse;

import java.util.List;

public interface CallbackListPayCashFolw {
    void success(List<CashFolwResponse> list);

    void failure(Throwable t);
}
