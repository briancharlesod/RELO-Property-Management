package com.techelevator.model;

public class Maintenance {

    private int maintenanceID;
    private int rentalID;
    private String completionDate;
    private boolean completed;
    private String maintenanceRequest;

    public Maintenance(int maintenanceID, int rentalID, String completionDate, String maintenanceRequest, boolean completed) {
        this.maintenanceID = maintenanceID;
        this.rentalID = rentalID;
        this.completionDate = completionDate;
        this.maintenanceRequest = maintenanceRequest;
        this.completed = completed;
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

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getMaintenanceRequest() {
        return maintenanceRequest;
    }

    public void setMaintenanceRequest(String maintenanceRequest) {
        this.maintenanceRequest = maintenanceRequest;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
