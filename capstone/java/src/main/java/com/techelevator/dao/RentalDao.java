package com.techelevator.dao;

import com.techelevator.model.Rental;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalDao {

    List<Rental> viewAllAvailableProperties();

    Rental viewSpecificProperty(int rentalID);

    int rentDueDate(int rentalID);

    List<Rental> propertiesByLandlord(int userID);

    boolean addNewProperty(Rental rental);

    boolean updateProperty(Rental rental);
}
