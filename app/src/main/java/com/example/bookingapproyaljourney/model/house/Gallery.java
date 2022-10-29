package com.example.bookingapproyaljourney.model.house;

import java.io.Serializable;

public class Gallery implements Serializable {
  private int ivimgHotel;
  private int btnAmount;

  public Gallery(int ivimgHotel, int btnAmount) {
    this.ivimgHotel = ivimgHotel;
    this.btnAmount = btnAmount;
  }

  public int getIvimgHotel() {
    return ivimgHotel;
  }

  public void setIvimgHotel(int ivimgHotel) {
    this.ivimgHotel = ivimgHotel;
  }

  public int getBtnAmount() {
    return btnAmount;
  }

  public void setBtnAmount(int btnAmount) {
    this.btnAmount = btnAmount;
  }
}
