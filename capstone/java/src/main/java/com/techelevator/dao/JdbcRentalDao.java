package com.techelevator.dao;

import com.techelevator.model.Rental;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@CrossOrigin
public class JdbcRentalDao implements RentalDao{

    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Rental> viewAllAvailableProperties() {
        List<Rental> rentalList = null;
        String sql = "Select * " +
                "From Rental " +
                "Where isRented = false;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next())
            {
                rentalList.add(mapToRental(result));
            }
        }catch (Exception e)
        {
            System.out.println("Could not find any properties that were not rented");
        }
        return rentalList;
    }

    @Override
    public Rental viewSpecificProperty(int rentalID) {
        Rental result = null;
        String sql = "Select * " +
                "From Rental " +
                "Where rental_id = ?;";
        try{
            result = jdbcTemplate.queryForObject(sql, Rental.class, rentalID);

        }catch (Exception e)
        {
            System.out.println("Could not find any properties that were not rented");
        }
        return result;
    }

    @Override
    public int rentDueDate(int rentalID) {
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
    public List<Rental> propertiesByLandlord(int userID) {
        List<Rental> rentalList = null;
        String sql = "Select * " +
                "From rental_property " +
                "Join user_rental Using (rental_id) " +
                "Where users.user_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            while(result.next())
            {
                rentalList.add(mapToRental(result));
            }
        }catch (Exception e)
        {
            System.out.println("Could not find any properties");
        }
        return rentalList;
    }

    @Override
    public int addNewProperty(Rental rental) {
        int rentalID = -1;
        String sql = "Insert Into rental_property (rental_address, rental_amount, bathrooms, bedrooms, is_rented, type_of_residence) " +
                "Values(?, ?, ?, ?, ?, ?) Returning rental_id;";
        try{
            rentalID = jdbcTemplate.queryForObject(sql, Integer.class, rental.getAddress(), rental.getPrice(), rental.getBathroom(), rental.getBedroom(), rental.isRented(), rental.getTypeOfResidence());
        }catch(Exception e)
        {
            System.out.println("Could not add property to database");
        }
        return rentalID;
    }

    private Rental mapToRental(SqlRowSet result)
    {
        Rental rental = new Rental();
        rental.setAddress(result.getString("rental_address"));
        rental.setBathroom(result.getString("bathrooms"));
        rental.setBedroom((result.getString("bedrooms")));
        rental.setPrice(result.getBigDecimal("rental_amount"));
        rental.setRentalID((result.getInt("rental_id")));
        rental.setRented(result.getBoolean("is_rented"));
        rental.setTypeOfResidence(result.getString("type_of_residence"));
        return rental;
    }
}