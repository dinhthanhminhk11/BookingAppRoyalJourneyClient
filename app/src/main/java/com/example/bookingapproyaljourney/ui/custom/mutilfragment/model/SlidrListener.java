package com.example.bookingapproyaljourney.ui.custom.mutilfragment.model;

public interface SlidrListener {
    void onSlideStateChanged(int state);

    void onSlideChange(float percent);

    void onSlideOpened();

    boolean onSlideClosed();
}
