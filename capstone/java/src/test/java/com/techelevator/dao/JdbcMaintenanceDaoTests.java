package com.techelevator.dao;

import com.sun.tools.javac.Main;
import com.techelevator.model.Maintenance;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.jta.ManagedTransactionAdapter;

import java.util.List;

public class JdbcMaintenanceDaoTests extends BaseDaoTests{

    protected static final User USER_1 = new User(1, "user1", "user1", "ROLE_USER");
    protected static final User USER_2 = new User(2, "user2", "user2", "ROLE_USER");
    private static final User USER_3 = new User(3, "user3", "user3", "ROLE_USER");
    private static final User USER_4 = new User(5, "renter1", "user3", "ROLE_RENTER");
    private static final User USER_5 = new User(6, "employee1", "user3", "ROLE_EMPLOYEE");

    private JdbcMaintenanceDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcMaintenanceDao(jdbcTemplate);
    }

    @Test
    public void happyPathAddMaintenanceRequestFromLandlord()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(1);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "landlord1";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(6, test);
    }
    @Test
    public void happyPathAddMaintenanceRequestFromRenter()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(1);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "renter1";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(5, test);
    }

    @Test
    public void unhappyPathAddMaintenanceRequestFromRenter_WrongDataID()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(0);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "renter1";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(-1, test);
    }

    @Test
    public void unhappyPathAddMaintenanceRequestFromRenter_WrongRequester()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(0);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "employee1";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(-1, test);
    }

    @Test
    public void unhappyPathAddMaintenanceRequestFromRenter_WrongRequester2()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(0);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "renter2";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(-1, test);
    }

    @Test
    public void unhappyPathAddMaintenanceRequestFromLandlord_WrongDataID()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(0);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "landlord1";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(-1, test);
    }

    @Test
    public void unhappyPathAddMaintenanceRequestFromLandlord_WrongRequester()
    {
        Maintenance maint = new Maintenance();
        maint.setCompleted(false);
        maint.setRentalID(0);
        maint.setMaintenanceRequest("Roof is leaking again");
        String username = "landlord2";
        int test = sut.addMaintenanceRequest(maint, username);
        Assert.assertEquals(-1, test);
    }

    @Test
    public void happyPathViewMaintenanceRequests()
    {
        List<Maintenance> test = sut.viewMaintenanceRequests(3, "landlord1");
        Assert.assertEquals(4, test.size());
    }

    @Test
    public void unhappyPathViewMaintenanceRequestsWrongLandlordName()
    {
        List<Maintenance> test = sut.viewMaintenanceRequests(3, "landlord2");
        Assert.assertNull(test);
    }

    @Test
    public void unhappyPathViewMaintenanceRequestsWrongLandlordID()
    {
        List<Maintenance> test = sut.viewMaintenanceRequests(4, "landlord1");
        Assert.assertNull(test);
    }

    @Test
    public void happyPathCompleteRequest()
    {
        Assert.assertTrue(sut.completeMaintenanceRequest(1, "landlord1"));
    }

    @Test
    public void unhappyPathCompleteRequestWrongLandlord()
    {
        Assert.assertFalse(sut.completeMaintenanceRequest(1, "landlord2"));
    }

    @Test
    public void unhappyPathCompleteRequestWrongID()
    {
        Assert.assertFalse(sut.completeMaintenanceRequest(-1, "landlord2"));
    }
}
