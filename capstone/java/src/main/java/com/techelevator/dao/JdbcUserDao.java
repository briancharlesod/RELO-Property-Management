package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean create(String username, String password, String role) {
        String insertUserSql = "insert into users (username,password_hash,role) values (?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole) == 1;
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
    public boolean setUserToProperty(int userID, int rentalID) {
        String sql = "Start Transaction; " +
                "Insert Into user_rental (user_id, rental_id) " +
                "Values (?, ?); " +
                "Update rental_property " +
                "Set is_rented = 'true' " +
                "Where rental_id = ?; " +
                "Commit;";
        try{
            jdbcTemplate.update(sql, userID, rentalID, rentalID);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not add record");
            return false;
        }
    }

    @Override
    public boolean setUserToMaintenance(int userID, int maintenanceID) {
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

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }
}
