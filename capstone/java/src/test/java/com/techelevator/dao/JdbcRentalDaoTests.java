package com.techelevator.dao;

import com.techelevator.model.Rental;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcRentalDaoTests extends BaseDaoTests{

    protected static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER");
    protected static final User USER_2 = new User(2, "user2", "user2", "ROLE_USER");
    private static final User USER_3 = new User(3, "user3", "user3", "ROLE_USER");
    private static final User USER_4 = new User(5, "renter1", "user3", "ROLE_RENTER");
    private static final User USER_5 = new User(6, "employee1", "user3", "ROLE_EMPLOYEE");

    private JdbcRentalDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcRentalDao(jdbcTemplate);
    }

    @Test
    public void getAllAvailableProperties()
    {
        Assert.assertEquals(9, sut.viewAllAvailableProperties().size());
    }

    @Test
    public void getOneAvailableProperty()
    {
        Rental test = sut.viewSpecificProperty(2);
        Assert.assertEquals("2318 Jane St, Pittsburgh, PA 15203", test.getAddress());
        Assert.assertEquals(new BigDecimal("1950.00"), test.getPrice());
        Assert.assertEquals(2.5, test.getBathroom(), 0.01);
    }

    @Test
    public void happyPathAllPropertiesByLandlord()
    {
        Assert.assertEquals(10, sut.propertiesByLandlord(3, "landlord1").size());
        Assert.assertEquals(0, sut.propertiesByLandlord(7, "landlord2").size());
    }

    @Test
    public void unhappyPathAllPropertiesByLandlord()
    {
        Assert.assertNull(sut.propertiesByLandlord(3, "landlord2"));
        Assert.assertNull(sut.propertiesByLandlord(7, "landlord1"));
    }

    @Test
    public void happyPathAddRental()
    {
        Rental rental = new Rental();
        rental.setDescription("Located in the historic north side area of Pittsburgh.");
        rental.setPicture("123456");
        rental.setTypeOfResidence("house");
        rental.setRented(false);
        rental.setPrice(new BigDecimal(1500));
        rental.setBedroom("3");
        rental.setBathroom(2.5);
        rental.setAddress("123 this way");
        rental.setLandlord(3);

        int id = sut.addNewProperty(rental, "landlord1");
        Assert.assertEquals(11, id);
    }

    @Test
    public void unhappyPathAddRental_WrongLandlord()
    {
        Rental rental = new Rental();
        rental.setDescription("Located in the historic north side area of Pittsburgh.");
        rental.setPicture("123456");
        rental.setTypeOfResidence("house");
        rental.setRented(false);
        rental.setPrice(new BigDecimal(1500));
        rental.setBedroom("3");
        rental.setBathroom(2.5);
        rental.setAddress("123 this way");
        rental.setLandlord(3);

        int id = sut.addNewProperty(rental, "landlord2");
        Assert.assertEquals(-2, id);
    }

    @Test
    public void unhappyPathAddRental_WrongLandlord2()
    {
        Rental rental = new Rental();
        rental.setDescription("Located in the historic north side area of Pittsburgh.");
        rental.setPicture("123456");
        rental.setTypeOfResidence("house");
        rental.setRented(false);
        rental.setPrice(new BigDecimal(1500));
        rental.setBedroom("3");
        rental.setBathroom(2.5);
        rental.setAddress("123 this way");
        rental.setLandlord(7);

        int id = sut.addNewProperty(rental, "landlord1");
        Assert.assertEquals(-2, id);
    }

    @Test
    public void unhappyPathAddRental_WrongRole()
    {
        Rental rental = new Rental();
        rental.setDescription("Located in the historic north side area of Pittsburgh.");
        rental.setPicture("123456");
        rental.setTypeOfResidence("house");
        rental.setRented(false);
        rental.setPrice(new BigDecimal(1500));
        rental.setBedroom("3");
        rental.setBathroom(2.5);
        rental.setAddress("123 this way");
        rental.setLandlord(4);

        int id = sut.addNewProperty(rental, "landlord1");
        Assert.assertEquals(-1, id);
    }

    @Test
    public void unhappyPathAddRental_WrongRole2()
    {
        Rental rental = new Rental();
        rental.setDescription("Located in the historic north side area of Pittsburgh.");
        rental.setPicture("123456");
        rental.setTypeOfResidence("house");
        rental.setRented(false);
        rental.setPrice(new BigDecimal(1500));
        rental.setBedroom("3");
        rental.setBathroom(2.5);
        rental.setAddress("123 this way");
        rental.setLandlord(3);

        int id = sut.addNewProperty(rental, "renter1");
        Assert.assertEquals(-2, id);
    }

    @Test
    public void happyPathGetRent()
    {
        Assert.assertEquals(new BigDecimal("1680.00"), sut.getRent(1, "renter1"));
    }

    @Test
    public void unhappyPathGetRentWrongRenter()
    {
        Assert.assertNull(sut.getRent(1, "renter2"));
    }

    @Test
    public void unhappyPathGetRentWrongID()
    {
        Assert.assertNull(sut.getRent(4, "renter1"));
    }
}
