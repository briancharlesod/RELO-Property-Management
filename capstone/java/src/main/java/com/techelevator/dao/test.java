package com.techelevator.dao;

import java.time.LocalDateTime;

public class test {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now().plusMonths(3);
        LocalDateTime monthLater = LocalDateTime.of(now.getYear(), now.plusMonths(1).getMonthValue(), 1, 1, 1);
        System.out.println(monthLater.minusDays(now.getDayOfMonth()).getDayOfMonth());
    }
}
