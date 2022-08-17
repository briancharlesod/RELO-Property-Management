package com.techelevator.dao;

import com.sun.tools.javac.Main;
import com.techelevator.model.Maintenance;
import com.techelevator.model.Rental;
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
    public int addMaintenanceRequest(Maintenance request, String username) {
        boolean landlordCheck = getUserIDFromUsername(username) == request.getRentalID();
        boolean renterCheck = getUserIDFromUsername(username) == request.getRentalID();
        if(renterCheck || landlordCheck) {
            String sql = "Insert Into maintenance (maintenance_request, rental_id, completed) " +
                    "Values(?, ?, ?) Returning maintenance_id;";
            Integer newRequest = -1;
            try {
                newRequest = jdbcTemplate.queryForObject(sql, Integer.class, request.getMaintenanceRequest(), checkProperty(username), request.isCompleted());
            } catch (Exception e) {
                System.out.println("Could not create report");
            }
            return newRequest;
        }
        System.out.println("Could not create a maintenance request for someone else's unit");
        return -1;
    }

    @Override
    public List<Maintenance> maintenanceByProperty(int rental_id, String username) {

        List<Maintenance> maintenanceList = null;
        String sql = "Select * " +
                "From maintenance " +
                "Where rental_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, rental_id);
            maintenanceList = new ArrayList<>();
            while(result.next())
            {
                maintenanceList.add(mapRowToMaintenance(result));
            }
        }catch (Exception e)
        {
            System.out.println(e);
            System.out.println("Could not find any properties");
        }
        return maintenanceList;
    }



    private int getUserIDFromRentalID(int rentalID)
    {
        int userID = -1;
        String sql = "Select user_id " +
                "From user_rental " +
                "Where rental_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, rentalID);
            if(result.next())
            {
                userID = result.getInt("user_id");
            }
        }catch (Exception e)
        {
            System.out.println("Could not find property");
        }
        return userID;
    }

    @Override
    public List<Maintenance> viewMaintenanceRequests(int userID, String username) {

        if(userID != getUserIDFromUsername(username))
        {
            System.out.println("Cannot access someone else's maintenance list");
            return null;
        }
        List<Integer> propertiesList = getRentalProperties(userID);
        List<Maintenance> requestList = null;
        String inStatement = getStatement(propertiesList);
        if(inStatement == null)
        {
            System.out.println("1");
            return null;
        }
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

    public List<Maintenance> getAllRequests()
    {
        List<Maintenance> newList = new ArrayList<>();
        String sql = "Select * " +
                "From maintenance;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while(result.next())
            {
                newList.add(mapRowToMaintenance(result));
            }
        }catch (Exception e)
        {
            System.out.println("nopes");
        }
        return newList;
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

    public Maintenance viewSpecificMaintenanceRequests(int maintenanceID, String username) {
        boolean landlordCheck = getUserIDFromUsername(username) == getLandlordFromRentalID(getRentalIDFromMaintenanceID(maintenanceID));
        boolean employeeCheck = getUserIDFromUsername(username) == getEmployeeFromMaintenanceID(maintenanceID);
        if(landlordCheck || employeeCheck) {
            Maintenance requestList = new Maintenance();
            String sql = "Select * " +
                    "From maintenance " +
                    "Where maintenance_id = ?;";
            try {
                SqlRowSet result = jdbcTemplate.queryForRowSet(sql, maintenanceID);
                if (result.next()) {
                    requestList = mapRowToMaintenance(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not access maintenance list");
            }
            return requestList;
        }
        System.out.println("Not authorized to access this report");
        return null;
    }

    private int getEmployeeFromMaintenanceID(int maintenanceID)
    {
        int employeeID = -1;
        String sql = "Select user_id " +
                "From user_maintenance " +
                "Where maintenance_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, maintenanceID);
            if(result.next()){
                employeeID = result.getInt("user_id");
            }
        }catch (Exception e)
        {
            System.out.println("Could not find maintenance request");
        }
        return employeeID;
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
    public boolean completeMaintenanceRequest(int maintenanceID, String username) {
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

    private int getRentalIDFromMaintenanceID(int maintenanceID)
    {
        int rentalID = -1;
        String sql = "Select rental_id " +
                "From maintenance " +
                "Where maintenance_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, maintenanceID);
            if(result.next())
            {
               rentalID = result.getInt("rental_id");
            }
        }catch (Exception e)
        {
            System.out.println("could not find that maintenance request");
        }

        return rentalID;
    }

    private int getLandlordFromRentalID(int rentalID){
        int landlordID = -1;
        String sql = "Select landlord_id " +
                "From rental_property " +
                "Where rental_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, rentalID);
            if(result.next())
            {
                landlordID = result.getInt("landlord_id");
            }
        }catch (Exception e)
        {
            System.out.println("Could not find rental property");
        }
        return landlordID;
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
