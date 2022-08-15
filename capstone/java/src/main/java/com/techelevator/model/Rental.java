package com.techelevator.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Rental {
    private int rentalID;
    @NotNull
    @NotEmpty
    @NotBlank
    private String address;
    @Min(value = 0)
    private double price;
    @NotNull
    @NotEmpty
    @NotBlank
    private double bedroom;
    @Min(value = 1)
    private double bathroom;
    private boolean isRented;
    @NotNull
    @NotEmpty
    @NotBlank
    private String typeOfResidence;
    @Min(value = 1)
    private int landlord;
    @NotNull
    @NotEmpty
    @NotBlank
    private String description;
    @NotNull
    @NotEmpty
    @NotBlank
    private String picture;

    public Rental(int landlord, int rentalID, String address, double price, double bedroom, double bathroom, boolean isRented, String typeOfResidence) {
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

    public String getDescription() {
        return description;
    }

    public String getImgURL() {
        return picture;
    }

    public void setImgURL(String imgURL) {
        this.picture = imgURL;
    }

    public int getLandlord_id() {
        return landlord;
    }

    public void setLandlord_id(int landlord_id) {
        this.landlord = landlord_id;
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

    public double getBedroom() {
        return bedroom;
    }

    public void setBedroom(double bedroom) {
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
