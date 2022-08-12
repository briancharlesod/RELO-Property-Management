package com.techelevator.dao;

import com.techelevator.model.Rental;
import org.apache.tomcat.jni.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface RentalDao {

    List<Rental> viewAllAvailableProperties();

    Rental viewSpecificProperty(int rentalID);

    int rentDueDate();

    BigDecimal getRent(int rentalID, String username);

    List<Rental> propertiesByLandlord(int userID, String username);

    int addNewProperty(Rental rental, String username);
}
