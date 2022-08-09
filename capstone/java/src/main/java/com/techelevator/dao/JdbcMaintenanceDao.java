package com.techelevator.dao;

import com.techelevator.model.Maintenance;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.List;
@Component
@CrossOrigin
public class JdbcMaintenanceDao implements MaintenanceDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcMaintenanceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addMaintenanceRequest(Maintenance request) {
        String sql = "Insert Into maintenance (maintenance_request, rental_id) " +
                "Values(?, ?) Returning maintenance_id;";
        Integer newRequest = -1;
        try {
            newRequest = jdbcTemplate.queryForObject(sql, Integer.class, request.getMaintenanceRequest(), request.getRentalID());
        }catch (Exception e)
        {
            System.out.println("Could not create report");
        }
        return newRequest;
    }

    @Override
    public boolean addMaintenanceToUser(Maintenance request, User user) {
        String sql = "Insert Into sser_maintenance (user_id, maintenance_id) " +
                "Values(?, ?);";
        try{
            jdbcTemplate.update(sql, user.getId(), request.getMaintenanceID());
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public List<Maintenance> viewMaintenanceRequests(List<Integer> rentalProperties, int userID) {

        List<Integer> propertiesList = getRentalProperties(userID);
        List<Maintenance> requestList = null;
        String sql = "Select * " +
                "From maintenance " +
                "Where rental_id In ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, propertiesList);
            while(result.next())
            {
                requestList.add(mapRowToMaintenance(result));
            }
        }catch (Exception e)
        {
            System.out.println("Could not access maintenance list");
        }
        return requestList;
    }

    private List<Integer> getRentalProperties(int userID)
    {
        List<Integer> rentalList = null;
        String sql = "Select rental_id " +
                "From user_rentals " +
                "Where user_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            while(result.next())
            {
                rentalList.add(result.getInt("user_id"));
            }
        }catch (Exception e)
        {
            System.out.println("Could not get any properties");
        }
        return rentalList;
    }

    @Override
    public boolean completeMaintenanceRequest(int maintenanceID) {
        String sql = "Update maintenance " +
                "Set completed = ? " +
                "Where maintenance_id = ?;";
        LocalDate lt = LocalDate.now();
        try{
            jdbcTemplate.update(sql, lt, maintenanceID);
            return true;
        }catch (Exception e)
        {
            System.out.println("Could not update record");
            return false;
        }
    }

    private Maintenance mapRowToMaintenance(SqlRowSet result)
    {
        Maintenance maintenance = new Maintenance();
        maintenance.setCompletionDate(result.getString("completion_date"));
        maintenance.setCompleted(result.getBoolean("completed"));
        maintenance.setMaintenanceID(result.getInt("maintenance_id"));
        maintenance.setMaintenanceRequest(result.getString("maintenance_request"));
        maintenance.setRentalID(result.getInt("rental_id"));
        return maintenance;
    }



}
