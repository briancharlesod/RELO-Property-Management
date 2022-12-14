package com.techelevator.dao;

import com.techelevator.model.PaymentClass;
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

    boolean updateProperty(Rental rental, String username);

    boolean payRent(PaymentClass rent, String username);

    List<PaymentClass> getAllRents(int userID, String username);

    List<Rental> viewOwnedPropertiesRenter(int userID, String username);

    boolean OffMarket(int rental_id);

    boolean OnMarket(int rental_id);

    boolean deleteProperty(int rental_id);

    boolean deleteRenterFromProperty(int User_id, int Rental_id);

}
