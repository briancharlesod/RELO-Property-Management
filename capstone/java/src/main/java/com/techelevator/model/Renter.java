package com.techelevator.model;

public class Renter {
    private int user_id;
    private String username;
    private String last_paid;

    public Renter(int user_id, String username, String last_paid) {
        this.user_id = user_id;
        this.username = username;
        this.last_paid = last_paid;
    }
    public Renter() {}
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_paid() {
        return last_paid;
    }

    public void setLast_paid(String last_paid) {
        this.last_paid = last_paid;
    }
}
