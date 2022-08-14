package com.techelevator.controller;


import com.techelevator.dao.JdbcRentalDao;

import com.techelevator.dao.UserDao;
import com.techelevator.exceptions.AddPropertyException;
import com.techelevator.exceptions.AvailablePropertyException;
import com.techelevator.exceptions.UnauthorizedAccessException;
import com.techelevator.model.Rental;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
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
    @ResponseStatus(HttpStatus.OK)
    public List<Rental> viewAllAvailableProperties() throws AvailablePropertyException {
        List<Rental> rentalList = rentalDao.viewAllAvailableProperties();
        if(rentalList == null)
        {
            throw new AvailablePropertyException();
        }
        return rentalList;
    }


    @RequestMapping(path = "/rental/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Rental viewSpecificProperty(@PathVariable int id) throws AvailablePropertyException {
        Rental rental =  rentalDao.viewSpecificProperty(id);
        if(rental == null)
        {
            throw new AvailablePropertyException();
        }
        return rental;
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/rental", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewProperty(@RequestBody @Valid Rental request, Principal principal) throws AddPropertyException {
        int id = rentalDao.addNewProperty(request, principal.getName());
        if(id > 0)
        {
            throw new AddPropertyException();
        }
    }
    @PreAuthorize("hasRole('ROLE_RENTER')")
    @RequestMapping(path = "/rent", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int rentDue(){
        return rentalDao.rentDueDate();
    }


    @CrossOrigin
    @RequestMapping(path = "/rental", method = RequestMethod.PUT)
    public boolean updateRental(@RequestBody Rental rental, Principal principal) {
        return rentalDao.updateProperty(rental, principal.getName());

    }

    @PreAuthorize("hasRole('ROLE_RENTER')")
    @RequestMapping(path = "/rent/{amount}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal rentAmount(@PathVariable int amount, Principal principal) throws UnauthorizedAccessException {
        BigDecimal rentAmount = rentalDao.getRent(amount, principal.getName());
        if(rentAmount == null)
        {
            throw new UnauthorizedAccessException();
        }
        return rentAmount;
    }
    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/landlord/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Rental> byLandlord(@PathVariable int id, Principal principal)throws UnauthorizedAccessException{
        List<Rental> rentalList = rentalDao.propertiesByLandlord(id, principal.getName());
        if(rentalList == null)
        {
            throw new UnauthorizedAccessException();
        }
        return rentalList;
    }



}

