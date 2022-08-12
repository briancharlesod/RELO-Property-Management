package com.techelevator.dao;

import com.techelevator.model.User;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;

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


    

}
