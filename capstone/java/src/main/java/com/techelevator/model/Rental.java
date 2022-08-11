package com.techelevator.model;

import java.math.BigDecimal;

public class Rental {

    private int rentalID;
    private String address;
    private double price;
    private int bedroom;
    private int bathroom;
    private boolean isRented;
    private String typeOfResidence;
    private String description;
    private String imgURL;
    private int landlord_id;

    public Rental(int rentalID, String address, double price, int bedroom, int bathroom, boolean isRented, String typeOfResidence, String description, String imgURL, int landlord_id) {
        this.rentalID = rentalID;
        this.address = address;
        this.price = price;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.isRented = isRented;
        this.typeOfResidence = typeOfResidence;
        this.description = description;
        this.imgURL = imgURL;
        this.landlord_id = landlord_id;

    }

    public Rental(){}

    public int getRentalID() {
        return rentalID;
    }

    public String getDescription() {
        return description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getLandlord_id() {
        return landlord_id;
    }

    public void setLandlord_id(int landlord_id) {
        this.landlord_id = landlord_id;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
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
