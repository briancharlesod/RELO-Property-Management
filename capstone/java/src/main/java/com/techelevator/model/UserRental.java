package com.techelevator.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserRental {

    @NotNull
    String userID;
    @Min(value = 1)
    int rentalID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
}
