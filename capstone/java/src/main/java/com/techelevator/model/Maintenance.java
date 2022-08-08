package com.techelevator.model;

import java.time.LocalDateTime;

public class Maintenance {

    private int maintenanceID;
    private int rentalID;
    private String completed;
    private String maintenanceRequest;

    public Maintenance(int maintenanceID, int rentalID, String completed, String maintenanceRequest) {
        this.maintenanceID = maintenanceID;
        this.rentalID = rentalID;
        this.completed = completed;
        this.maintenanceRequest = maintenanceRequest;
    }

    public Maintenance() {
    }

    public int getMaintenanceID() {
        return maintenanceID;
    }

    public void setMaintenanceID(int maintenanceID) {
        this.maintenanceID = maintenanceID;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getMaintenanceRequest() {
        return maintenanceRequest;
    }

    public void setMaintenanceRequest(String maintenanceRequest) {
        this.maintenanceRequest = maintenanceRequest;
    }
}
