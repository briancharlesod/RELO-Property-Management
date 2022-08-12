package com.techelevator.controller;


import com.techelevator.dao.JdbcRentalDao;
import com.techelevator.dao.RentalDao;

import com.techelevator.dao.UserDao;
import com.techelevator.model.Rental;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping
public class RentalController {
    private JdbcRentalDao rentalDao;
    private UserDao userDao;
    private List<String> Rental;

    public RentalController(JdbcRentalDao dao, UserDao userDao) {
        this.rentalDao = dao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/rental", method = RequestMethod.GET)
    public List<Rental> viewAllAvailableProperties() {
        return rentalDao.viewAllAvailableProperties();
    }

    @RequestMapping(path = "/rental/{id}", method = RequestMethod.GET)
    public Rental viewSpecificProperty(@PathVariable int id) {
        return rentalDao.viewSpecificProperty(id);
    }

    @RequestMapping(path = "/rental", method = RequestMethod.POST)
    public void addNewProperty(@RequestBody Rental request) {
//        if (request != null) {
//            Rental.add(request);
//        }
        int id = rentalDao.addNewProperty(request);
    }

    @RequestMapping(path = "/rent", method = RequestMethod.GET)
    public int rentDue(){
        return rentalDao.rentDueDate();
    }

    @RequestMapping(path = "/rent/{amount}", method = RequestMethod.GET)
    public BigDecimal rentAmount(@PathVariable int amount){
        return rentalDao.getRent(amount);
    }



    @RequestMapping(path = "/landlord/{id}")
    public List<Rental> byLandlord(@PathVariable int id){
        return rentalDao.propertiesByLandlord(id);
    }

}

