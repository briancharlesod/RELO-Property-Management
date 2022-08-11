package com.techelevator.dao;

import com.techelevator.model.Maintenance;
import com.techelevator.model.User;

import java.util.List;

public interface MaintenanceDao {

    int addMaintenanceRequest(Maintenance request);

    boolean addMaintenanceToUser(Maintenance request, User user);

    List<Maintenance> viewMaintenanceRequests(int userID);

    boolean completeMaintenanceRequest(int maintenanceID);
}
