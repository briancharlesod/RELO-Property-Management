package com.techelevator.model;

import java.math.BigDecimal;

public class Rental {

    private int rentalID;
    private String address;
    private BigDecimal price;
    private String bedroom;
    private double bathroom;
    private boolean isRented;
    private String typeOfResidence;
    private int landlord;
    private String description;
    private String picture;

    public Rental(int landlord, int rentalID, String address, BigDecimal price, String bedroom, double bathroom, boolean isRented, String typeOfResidence) {
        this.rentalID = rentalID;
        this.address = address;
        this.price = price;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.isRented = isRented;
        this.typeOfResidence = typeOfResidence;
        this.landlord = landlord;
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

    public double getBathroom() {
        return bathroom;
    }

    public void setBathroom(double bathroom) {
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

    public void setLandlord(int landlord) {
        this.landlord = landlord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLandlord() {
        return landlord;
    }
}
