package com.techelevator.model;

import javax.validation.constraints.Min;

public class UserRental {

    @Min(value = 1)
    int userID;
    @Min(value = 1)
    int rentalID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
}
