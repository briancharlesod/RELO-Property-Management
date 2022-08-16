package com.techelevator.dao;

import com.techelevator.model.PaymentClass;
import com.techelevator.model.Rental;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component

public class JdbcRentalDao implements RentalDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcRentalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rental> viewAllAvailableProperties() {
        List<Rental> rentalList = null;
        String sql = "Select * From rental_property "+
                "Where is_rented = 'false';";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            System.out.println("good so far");
            rentalList = new ArrayList<>();
            while(result.next())
            {
                rentalList.add(mapToRental(result));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not find any properties that were not rented");
        }
        return rentalList;
    }

    @Override
    public List<Rental> viewOwnedPropertiesRenter(int userID, String username) {

            if(getUserIDFromUsername(username) != userID)
            {
                System.out.println("try accessing the data from your own account");
                return null;
            }
            List<Rental> rentalList = null;
            String sql = "Select rental_id, rental_address, rental_amount, bathrooms, bedrooms, is_rented, type_of_residence, description, picture, landlord_id " +
                    "from user_rental Join users using(user_id) Join rental_property using(rental_id) Where user_id = ?;";
            try{
                SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
                rentalList = new ArrayList<>();
                while(result.next())
                {
                    rentalList.add(mapToRental(result));
                }
            }catch (Exception e)
            {
                System.out.println(e);
                System.out.println("Could not find any properties");
            }
            return rentalList;

    }

    @Override
    public Rental viewSpecificProperty(int rentalID) {
        Rental result = null;
        String sql = "Select * " +
                "From rental_property " +
                "Where rental_id = ?;";
        try{
            SqlRowSet res = jdbcTemplate.queryForRowSet(sql, rentalID);
            result = new Rental();
            if(res.next())
            {
                result = mapToRental(res);
            }
        }catch (Exception e)
        {
            System.out.println("Could not find that specific");
        }
        return result;
    }

    @Override
    public int rentDueDate() {
        LocalDateTime now = LocalDateTime.now();
        long addYear = 0;
        if(now.getDayOfMonth() == 1)
        {
            return 0;
        }
        if(now.getMonthValue() == 12)
        {
            addYear++;
        }
        LocalDateTime later = LocalDateTime.of((now.plusYears(addYear)).getYear(), now.plusMonths(1).getMonthValue(), 1, 1, 1);

        return later.minusDays(now.getDayOfMonth()).getDayOfMonth();
    }

    @Override
    public List<Rental> propertiesByLandlord(int userID, String username) {
        if(getUserIDFromUsername(username) != userID)
        {
            System.out.println("try accessing the data from your own account");
            return null;
        }
        List<Rental> rentalList = null;
        String sql = "Select * " +
                "From rental_property " +
                "Where landlord_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            rentalList = new ArrayList<>();
            while(result.next())
            {
                rentalList.add(mapToRental(result));
            }
        }catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not find any properties");
        }
        return rentalList;
    }

    private List<Integer> rentalIDByLandlord(int userID, String username) {
        if(getUserIDFromUsername(username) != userID)
        {
            System.out.println("try accessing the data from your own account");
            return null;
        }
        List<Integer> rentalList = null;
        String sql = "Select rental_id " +
                "From rental_property " +
                "Where landlord_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            rentalList = new ArrayList<>();
            while(result.next())
            {
                rentalList.add(result.getInt("rental_id"));
            }
        }catch (Exception e)
        {
            System.out.println("Could not find any properties");
        }
        return rentalList;
    }

    private String getStatement(List<Integer> propertiesList)
    {
        String inStatement = "(";
        if(propertiesList.size() == 0)
        {
            return null;
        }
        for (int i = 0; i < propertiesList.size(); i++) {
            if(i < propertiesList.size()-1) {
                inStatement = inStatement + propertiesList.get(i) + ",";
            }
            else{
                inStatement = inStatement + propertiesList.get(i) + ")";
            }
        }
        return inStatement;
    }

    @Override
    public List<PaymentClass> getAllRents(int userID, String username)
    {
        if(userID != getUserIDFromUsername(username))
        {
            return null;
        }
        List<Integer> allProps = rentalIDByLandlord(userID, username);
        if(allProps.size() == 0)
        {
            return null;
        }
        List<PaymentClass> rentedProps = new ArrayList<>();
        String statement = getStatement(allProps);
        String sql = "Select rental_property.rental_address, user_rental.last_paid, rental_property.rental_id " +
                "From rental_property " +
                "Join user_rental Using (rental_id) " +
                "Where rental_id In "+statement+";";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next())
            {
                PaymentClass temp = new PaymentClass();
                temp.setAddress(result.getString("rental_address"));
                System.out.println(temp.getAddress());
                temp.setRent(result.getString("last_paid"));
                System.out.println(temp.getRent());
                temp.setRentalID(result.getString("rental_id"));
                System.out.println(temp.getRentalID());
                rentedProps.add(temp);
                System.out.println(rentedProps.size());
            }
        }catch (Exception e)
        {
            return null;
        }
        return rentedProps;
    }

    @Override
    public boolean payRent(PaymentClass rent, String username)
    {
        System.out.println(checkProperty(username));
        System.out.println(rent.getRentalID());
        if (checkProperty(username) == Integer.parseInt(rent.getRentalID())) {
            String sql = "Update user_rental " +
                    "Set last_paid = (Select Current_date- INTERVAL '1 DAY') " +
                    "Where rental_id = ?;";
            try {
                jdbcTemplate.update(sql, Integer.parseInt(rent.getRentalID()));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public int addNewProperty(Rental rental, String username) {
        if (!getRole(rental.getLandlord()).contains("LANDLORD")) {
            System.out.println(getRole(rental.getLandlord()));
            System.out.println("A landlord has to have that role");
            return -1;
        }
        if (getUserIDFromUsername(username) != rental.getLandlord()) {
            System.out.println("You cannot list a property in someone else's name");
            return -2;
        }
        int rentalID = -1;
        String sql = "Insert Into rental_property (rental_address, rental_amount, bathrooms, bedrooms, is_rented, type_of_residence, description, picture, landlord_id) " +
                "Values(?, ?, ?, ?, ?, ?, ?, ?, ?) Returning rental_id;";
        try {
            rentalID = jdbcTemplate.queryForObject(sql, Integer.class, rental.getAddress(), rental.getPrice(), rental.getBathroom(), rental.getBedroom(), rental.isRented(), rental.getTypeOfResidence(), rental.getDescription(), rental.getPicture(), rental.getLandlord());
            System.out.println(rentalID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentalID;
    }

    @Override
    public BigDecimal getRent(int rentalID, String username)
    {
        if(checkProperty(username) != rentalID)
        {
            System.out.println("You do not live here");
            return null;
        }
        BigDecimal rent = null;
        String sql = "Select rental_amount " +
                "From rental_property " +
                "Where rental_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, rentalID);
            if (result.next()) {
                rent = result.getBigDecimal("rental_amount");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not pull rent");
        }
        return rent;
    }

    private int checkProperty(String username)
    {
        int rental_id = -1;
        String sql = "Select rental_id " +
                "From user_rental " +
                "Join users Using (user_id) " +
                "Where username = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if(result.next())
            {
                rental_id = result.getInt("rental_id");
            }
        }catch (Exception e)
        {
            System.out.println("Could not get the required data");
        }
        return rental_id;
    }

    private String getRole(int userID)
    {
        String role = "";
        String sql = "Select role " +
                "From users " +
                "Where user_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            if(result.next())
            {
                role = result.getString("role");
            }
        }catch (Exception e){
            System.out.println("Could not find user");
        }
        return role;
    }
    public boolean updateProperty(Rental rental, String username) {
        if (!getRole(rental.getLandlord()).contains("LANDLORD")) {
            System.out.println(getRole(rental.getLandlord()));
            System.out.println("A landlord has to have that role");
            return false;
        }
        if (getUserIDFromUsername(username) != rental.getLandlord()) {
            System.out.println("You cannot list a property in someone else's name");
            return false;
        }
        String sql = "UPDATE rental_property SET rental_address = ?, rental_amount = ?, bathrooms = ?, bedrooms = ?, is_rented = ?, type_of_residence = ?, description = ? WHERE rental_id = ?";
       try {
           jdbcTemplate.update(sql, rental.getAddress(), rental.getPrice(), rental.getBathroom(), rental.getBedroom(), rental.isRented(), rental.getTypeOfResidence(), rental.getDescription(), rental.getRentalID());
       return true;
       } catch (Exception e) {
           System.out.println(e);
       }
       return false;
       }

    private Rental mapToRental(SqlRowSet result)
    {
        Rental rental = new Rental();
        rental.setAddress(result.getString("rental_address"));
        rental.setBathroom(result.getDouble("bathrooms"));
        rental.setBedroom((result.getString("bedrooms")));
        rental.setPrice(result.getBigDecimal("rental_amount"));
        rental.setRentalID((result.getInt("rental_id")));
        rental.setRented(result.getBoolean("is_rented"));
        rental.setTypeOfResidence(result.getString("type_of_residence"));
        rental.setLandlord(result.getInt("landlord_id"));
        rental.setPicture(result.getString("picture"));
        rental.setDescription(result.getString("description"));
        return rental;
    }

    private int getUserIDFromUsername(String username){
        int userID = -1;
        String sql = "Select user_id " +
                "From users " +
                "Where username = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if(result.next())
            {
                userID = result.getInt("user_id");
            }
        }catch (Exception e)
        {
            System.out.println("Could not find that username");
        }
        return userID;
    }
}
