package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);

    List<Integer> getAllPropertiesByUser(int userID);

    boolean setUserToProperty(int userID, int rentalID, String username);

    boolean setUserToMaintenance(int userID, int maintenanceID, String username);
}
