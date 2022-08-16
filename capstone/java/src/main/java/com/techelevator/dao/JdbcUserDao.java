package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.model.Renter;
import com.techelevator.model.UserNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			throw new UserNotFoundException();
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    public boolean updatePassword(int userID, String password)
    {
        String sql = "Update users " +
                "Set password_hash = ? " +
                "Where user_id = ?;";
        try{
            String newPassword = new BCryptPasswordEncoder().encode(password);
            return jdbcTemplate.update(sql, newPassword, userID) == 1;
        }catch (Exception e)
        {
            System.out.println("Could not change password");
            return false;
        }
    }

    @Override
    public boolean create(String username, String password, String role, String questionOne, String questionTwo, String answerOne, String answerTwo) {
        String insertUserSql = "Start Transaction; " +
                "insert into users (username,password_hash,role) values (?,?,?); " +
                "Insert Into user_retrieval (user_id, question_one, question_two, answer_one, answer_two)" +
                "Values((Select user_id From users Where username = ?), ?, ?, ?, ?); " +
                "Commit;";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String q1 = new BCryptPasswordEncoder().encode(questionOne);
        String q2 = new BCryptPasswordEncoder().encode(questionTwo);
        String a1 = new BCryptPasswordEncoder().encode(answerOne);
        String a2 = new BCryptPasswordEncoder().encode(answerTwo);

        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole, username, q1, q2, a1, a2) == 0;
    }

    @Override
    public String getQuestionOne(int userID)
    {
        String questionOne = null;
        String sql = "Select question_one " +
                "From user_retrieval " +
                "Where user_id = ?;";
        try {
            questionOne = jdbcTemplate.queryForObject(sql, String.class, userID);
        }catch (Exception e) {
            System.out.println("Could not retrieve user");
        }
        return questionOne;
    }


    @Override
    public String getQuestionTwo(int userID)
    {
        String questionTwo = null;
        String sql = "Select question_two " +
                "From user_retrieval " +
                "Where user_id = ?;";
        try {
            questionTwo = jdbcTemplate.queryForObject(sql, String.class, userID);
        }catch (Exception e)
        {
            System.out.println("Could not retrieve user");
        }
        return questionTwo;
    }

    @Override
    public String getAnswerOne(int userID)
    {
        String answerOne = null;
        String sql = "Select answer_one " +
                "From user_retrieval " +
                "Where user_id = ?;";
        try {
            answerOne = jdbcTemplate.queryForObject(sql, String.class, userID);
        }catch (Exception e)
        {
            System.out.println("Could not retrieve user");
        }
        return answerOne;
    }

    @Override
    public String getAnswerTwo(int userID)
    {
        String answerTwo = null;
        String sql = "Select answer_two " +
                "From user_retrieval " +
                "Where user_id = ?;";
        try {
            answerTwo = jdbcTemplate.queryForObject(sql, String.class, userID);
        }catch (Exception e)
        {
            System.out.println("Could not retrieve user");
        }
        return answerTwo;
    }

    @Override
    public List<Integer> getAllPropertiesByUser(int userID) {
        List<Integer> listOfProperties = null;
        String sql = "Select rental_id " +
                "From user_rental " +
                "Where user_id = ?;";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userID);
            while(result.next())
            {
                listOfProperties.add(result.getInt("rental_id"));
            }
        }catch (Exception e)
        {
            System.out.println("Could not find any properties by that ID");
        }
        return listOfProperties;
    }

    @Override
    public boolean setUserToProperty(String userID, int rentalID, String username) {
        int usersid = getUserIDFromUsername(userID);
        if(!getRole(usersid).contains("RENTER")){
            System.out.println("Enter a renter");
            return false;
        }
        if(getUserIDFromUsername(username) != getLandlordFromRentalID(rentalID))
        {
            System.out.println("Could not assign a renter if you do not own the property");
            return false;
        }
        String sql = "Start Transaction; " +
                "Insert Into user_rental (user_id, rental_id) " +
                "Values (?, ?); " +
                "Update rental_property " +
                "Set is_rented = 'true' " +
                "Where rental_id = ?; " +
                "Commit;";
        try{
            jdbcTemplate.update(sql, usersid, rentalID, rentalID);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not add record");
            return false;
        }
    }

    @Override
    public boolean setUserToMaintenance(int userID, int maintenanceID, String username) {
        if(!getRole(userID).contains("EMPLOYEE"))
        {
            System.out.println("Enter an employee");
            return false;
        }
        if(getUserIDFromUsername(username) != getLandlordFromRentalID(getRentalIDFromMaintenanceID(maintenanceID)))
        {
            System.out.println("Could not assign maintenance if you do not own the property");
            return false;
        }
        String sql = "Insert Into user_maintenance (user_id, maintenance_id) " +
                "Values (?, ?);";
        try{
            jdbcTemplate.update(sql, userID, maintenanceID);
            return true;
        }catch (Exception e)
        {
            System.out.println("Could not add record");
            return false;
        }
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

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }

    private Renter mapRowToRenter(SqlRowSet rs) {
        Renter renter = new Renter();
        renter.setUsername(rs.getString("username"));
        renter.setUser_id(rs.getInt("user_id"));
        renter.setLast_paid(rs.getString("last_paid"));
        return renter;
    }
    public List<Renter> getUsersOfRentersByRentalId(int rental_id) {
        List<Renter> users = new ArrayList<>();

                String sql = "Select * from user_rental Join users using(user_id) Where rental_id = ?;";
        try {
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, rental_id);
        while(results.next()) {
            users.add(mapRowToRenter(results));
        }
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
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
