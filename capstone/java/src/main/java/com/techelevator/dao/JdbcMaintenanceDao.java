package com.techelevator.dao;

import com.techelevator.model.Maintenance;
import com.techelevator.model.User;
import com.techelevator.model.UserMaintenance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.ArrayList;
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
        String sql = "Insert Into maintenance (maintenance_request, rental_id, completed) " +
                "Values(?, ?, ?) Returning maintenance_id;";
        Integer newRequest = -1;
        try {
            newRequest = jdbcTemplate.queryForObject(sql, Integer.class, request.getMaintenanceRequest(), request.getRentalID(), request.isCompleted());
        }catch (Exception e)
        {
            System.out.println("Could not create report");
        }
        return newRequest;
    }

    @Override
    public boolean addMaintenanceToUser(UserMaintenance request) {
        String sql = "Insert Into user_maintenance (user_id, maintenance_id) " +
                "Values(?, ?);";
        try{
            jdbcTemplate.update(sql, request.getUserID(), request.getMaintenanceID());
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public List<Maintenance> viewMaintenanceRequests(int userID) {

        List<Integer> propertiesList = getRentalProperties(userID);
        List<Maintenance> requestList = null;
        String inStatement = "(";
        for (int i = 0; i < propertiesList.size(); i++) {
            if(i < propertiesList.size()-1) {
                inStatement = inStatement + propertiesList.get(i) + ",";
            }
            else{
                inStatement = inStatement + propertiesList.get(i) + ")";
            }
        }
        System.out.println(inStatement);
        String sql = "Select * " +
                "From maintenance " +
                "Where rental_id In "+inStatement+";";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            requestList = new ArrayList<>();
            while(result.next())
            {
                requestList.add(mapRowToMaintenance(result));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not access maintenance list");
        }
        return requestList;
    }

    public Maintenance viewSpecificMaintenanceRequests(int maintenanceID) {
        Maintenance requestList = new Maintenance();
        String sql = "Select * " +
                "From maintenance " +
                "Where maintenance_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, maintenanceID);

            if(result.next())
            {
                requestList = mapRowToMaintenance(result);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not access maintenance list");
        }
        return requestList;
    }

    private List<Integer> getRentalProperties(int userID)
    {
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
            System.out.println(rentalList);
        }catch (Exception e)
        {
            System.out.println("Could not get any properties");
        }
        return rentalList;
    }

    @Override
    public boolean completeMaintenanceRequest(int maintenanceID) {
        String sql = "Update maintenance " +
                "Set completed = ?, " +
                "completion_date = ? " +
                "Where maintenance_id = ?;";
        LocalDate lt = LocalDate.now();
        try{
            jdbcTemplate.update(sql, true, lt, maintenanceID);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
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
