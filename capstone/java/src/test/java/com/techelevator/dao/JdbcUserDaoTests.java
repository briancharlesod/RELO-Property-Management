package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.UserNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests {
    protected static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER");
    protected static final User USER_2 = new User(2, "user2", "user2", "ROLE_USER");
    private static final User USER_3 = new User(3, "user3", "user3", "ROLE_USER");
    private static final User USER_4 = new User(5, "renter1", "user3", "ROLE_RENTER");
    private static final User USER_5 = new User(6, "employee1", "user3", "ROLE_EMPLOYEE");



    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void happyPathSetUserToMaintenance()
    {
        User newUser = USER_5;
        int maintenanceID = 1;
        String landlordName = "landlord1";
        boolean test = sut.setUserToMaintenance(newUser.getId(), maintenanceID, landlordName);
        Assert.assertTrue(test);
    }

   @Test
    public void unhappyPathSetUserToMaintenance_LandlordIsNotALandlord()
    {
        User newUser = USER_5;
        int maintenanceID = 1;
        String landlordName = "tom";
        boolean test = sut.setUserToMaintenance(newUser.getId(), maintenanceID, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToMaintenance_EmployeeIsNotAnEmployee()
    {
        User newUser = USER_4;
        int maintenanceID = 1;
        String landlordName = "landlord1";
        boolean test = sut.setUserToMaintenance(newUser.getId(), maintenanceID, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToMaintenance_InvalidDataProp()
    {
        User newUser = USER_5;
        int maintenanceID = 0;
        String landlordName = "landlord1";
        boolean test = sut.setUserToMaintenance(newUser.getId(), maintenanceID, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToMaintenance_InvalidDataUser()
    {
        User newUser = USER_4;
        int maintenanceID = 1;
        String landlordName = "landlord1";
        boolean test = sut.setUserToMaintenance(newUser.getId(), maintenanceID, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToMaintenance_LandlordIsTheWrongLandlord()
    {
        User newUser = USER_5;
        int maintenanceID = 1;
        String landlordName = "landlord2";
        boolean test = sut.setUserToMaintenance(newUser.getId(), maintenanceID, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void happyPathSetUserToRental()
    {
        User newUser = USER_4;
        int property = 1;
        String landlordName = "landlord1";
        boolean test = sut.setUserToProperty(newUser.getId(), property, landlordName);
        Assert.assertTrue(test);
    }

    @Test
    public void unhappyPathSetUserToRental_LandlordIsNotALandlord()
    {
        User newUser = USER_4;
        int property = 1;
        String landlordName = "tom";
        boolean test = sut.setUserToProperty(newUser.getId(), property, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToRental_LandlordIsTheWrongLandlord()
    {
        User newUser = USER_4;
        int property = 1;
        String landlordName = "landlord2";
        boolean test = sut.setUserToProperty(newUser.getId(), property, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToRental_RenterIsNotARenter()
    {
        User newUser = USER_3;
        int property = 1;
        String landlordName = "landlord1";
        boolean test = sut.setUserToProperty(newUser.getId(), property, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToRental_InvalidDataProp()
    {
        User newUser = USER_4;
        int property = 0;
        String landlordName = "tom";
        boolean test = sut.setUserToProperty(newUser.getId(), property, landlordName);
        Assert.assertFalse(test);
    }

    @Test
    public void unhappyPathSetUserToRental_InvalidDataUser()
    {
        User newUser = USER_3;
        int property = 1;
        String landlordName = "tom";
        boolean test = sut.setUserToProperty(newUser.getId(), property, landlordName);
        Assert.assertFalse(test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findIdByUsername_given_null_throws_exception() {
        sut.findIdByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findIdByUsername_given_invalid_username_throws_exception() {
        sut.findIdByUsername("invalid");
    }

    @Test
    public void findIdByUsername_given_valid_user_returns_user_id() {
        int actualUserId = sut.findIdByUsername(USER_1.getUsername());

        Assert.assertEquals(USER_1.getId(), actualUserId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByUsername_given_null_throws_exception() {
        sut.findByUsername(null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByUsername_given_invalid_username_throws_exception() {
        sut.findByUsername("invalid");
    }

    @Test
    public void findByUsername_given_valid_user_returns_user() {
        User actualUser = sut.findByUsername(USER_1.getUsername());

        Assert.assertEquals(USER_1, actualUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserById_given_invalid_user_id_throws_exception() {
        sut.getUserById(-1);
    }

    @Test
    public void getUserById_given_valid_user_id_returns_user() {
        User actualUser = sut.getUserById(USER_1.getId());

        Assert.assertEquals(USER_1, actualUser);
    }

    @Test
    public void findAll_returns_all_users() {
        List<User> users = sut.findAll();

        Assert.assertNotNull(users);
        Assert.assertEquals(9, users.size());
        Assert.assertEquals(USER_1, users.get(0));
        Assert.assertEquals(USER_2, users.get(1));
    }

   /* @Test(expected = DataIntegrityViolationException.class)
    public void create_user_with_null_username() {
        sut.create(null, USER_3.getPassword(), "ROLE_USER");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void create_user_with_existing_username() {
        sut.create(USER_1.getUsername(), USER_3.getPassword(), "ROLE_USER");
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_user_with_null_password() {
        sut.create(USER_3.getUsername(), null, "ROLE_USER");
    }

    @Test
    public void create_user_creates_a_user() {
        User newUser = new User(-1, "new", "user", "ROLE_USER");

        boolean userWasCreated = sut.create(newUser.getUsername(), newUser.getPassword(), "ROLE_USER");

        Assert.assertTrue(userWasCreated);

        User actualUser = sut.findByUsername(newUser.getUsername());
        newUser.setId(actualUser.getId());

        actualUser.setPassword(newUser.getPassword()); // reset password back to unhashed password for testing
        Assert.assertEquals(newUser, actualUser);
    }

    */
}
