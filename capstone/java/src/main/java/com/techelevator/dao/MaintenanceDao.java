package com.techelevator.dao;

import com.techelevator.model.Maintenance;
import com.techelevator.model.User;
import com.techelevator.model.UserMaintenance;

import java.util.List;

public interface MaintenanceDao {

    int addMaintenanceRequest(Maintenance request, String username);

    List<Maintenance> viewMaintenanceRequests(int userID, String username);

    Maintenance viewSpecificMaintenanceRequests(int maintenanceID, String username);

    boolean completeMaintenanceRequest(int maintenanceID, String username);
}
