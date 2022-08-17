package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentClass {

    String rent;
    String rentalID;
    String address;
    BigDecimal rentAmount;
    String date;

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "PaymentClass{" +
                "rent='" + rent + '\'' +
                ", rentalID='" + rentalID + '\'' +
                ", address='" + address + '\'' +
                ", rentAmount=" + rentAmount +
                ", date='" + date + '\'' +
                '}';
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRentalID() {
        return rentalID;
    }

    public void setRentalID(String rentalID) {
        this.rentalID = rentalID;
    }
}
