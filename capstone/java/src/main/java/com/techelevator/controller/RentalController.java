package com.techelevator.controller;


import com.techelevator.dao.JdbcRentalDao;
import com.techelevator.dao.RentalDao;

import com.techelevator.dao.UserDao;
import com.techelevator.model.Rental;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")

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

    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/rental", method = RequestMethod.POST)
    public void addNewProperty(@RequestBody @Valid Rental request, Principal principal) {
//        if (request != null) {
//            Rental.add(request);
//        }
        int id = rentalDao.addNewProperty(request, principal.getName());
    }
    @PreAuthorize("hasRole('ROLE_RENTER')")
    @RequestMapping(path = "/rent", method = RequestMethod.GET)
    public int rentDue(){
        return rentalDao.rentDueDate();
    }
//    @CrossOrigin
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/rental", method = RequestMethod.POST)
//    public boolean addNewProperty(@RequestBody Rental rental) {
//          return dao.addNewProperty(rental);
//
//    }

    @CrossOrigin
    @RequestMapping(path = "/rental", method = RequestMethod.PUT)
    public boolean updateRental(@RequestBody Rental rental) {
        return rentalDao.updateProperty(rental);

    }

    @PreAuthorize("hasRole('ROLE_RENTER')")
    @RequestMapping(path = "/rent/{amount}", method = RequestMethod.GET)
    public BigDecimal rentAmount(@PathVariable int amount, Principal principal){
        return rentalDao.getRent(amount, principal.getName());
    }
    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/landlord/{id}")
    public List<Rental> byLandlord(@PathVariable int id, Principal principal){
        return rentalDao.propertiesByLandlord(id, principal.getName());
    }



}

