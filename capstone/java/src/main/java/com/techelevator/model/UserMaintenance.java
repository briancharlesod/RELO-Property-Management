package com.techelevator.model;

import javax.validation.constraints.Min;

public class UserMaintenance {

    @Min(value = 1)
    private int maintenanceID;
    @Min(value = 1)
    private int userID;

    public int getMaintenanceID() {
        return maintenanceID;
    }

    public void setMaintenanceID(int maintenanceID) {
        this.maintenanceID = maintenanceID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
