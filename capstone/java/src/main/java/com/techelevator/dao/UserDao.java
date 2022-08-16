package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role, String questionOne, String questionTwo, String answerOne, String answerTwo);

    List<Integer> getAllPropertiesByUser(int userID);

    boolean setUserToProperty(String userID, int rentalID, String username);

    boolean setUserToMaintenance(int userID, int maintenanceID, String username);

    String getAnswerTwo(int userID);

    String getAnswerOne(int userID);

    String getQuestionTwo(int userID);

    String getQuestionOne(int userID);

    boolean updatePassword(int userID, String password);

}
