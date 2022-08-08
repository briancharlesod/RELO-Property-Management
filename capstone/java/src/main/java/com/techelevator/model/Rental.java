package com.techelevator.model;

import java.math.BigDecimal;

public class Rental {

    private int rentalID;
    private String address;
    private BigDecimal price;
    private String bedroom;
    private String bathroom;
    private boolean isRented;
    private String typeOfResidence;

    public Rental(int rentalID, String address, BigDecimal price, String bedroom, String bathroom, boolean isRented, String typeOfResidence) {
        this.rentalID = rentalID;
        this.address = address;
        this.price = price;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.isRented = isRented;
        this.typeOfResidence = typeOfResidence;
    }

    public Rental(){}

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public String getTypeOfResidence() {
        return typeOfResidence;
    }

    public void setTypeOfResidence(String typeOfResidence) {
        this.typeOfResidence = typeOfResidence;
    }
}
