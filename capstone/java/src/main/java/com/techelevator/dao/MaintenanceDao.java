package com.techelevator.dao;

import com.techelevator.model.Maintenance;
import com.techelevator.model.User;
import com.techelevator.model.UserMaintenance;

import java.util.List;

public interface MaintenanceDao {

    int addMaintenanceRequest(Maintenance request);

    boolean addMaintenanceToUser(UserMaintenance request);

    List<Maintenance> viewMaintenanceRequests(int userID);

    Maintenance viewSpecificMaintenanceRequests(int maintenanceID);

    boolean completeMaintenanceRequest(int maintenanceID);
}
