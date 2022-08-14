package com.techelevator.controller;


import com.techelevator.dao.RentalDao;

import com.techelevator.model.Rental;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")

public class RentalController {
    private RentalDao dao;
    private List<String> Rental;



    public RentalController(RentalDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/rental", method = RequestMethod.GET)
    public List<String> viewAllAvailableProperties() {
        return viewAllAvailableProperties();
    }


    @RequestMapping(path = "/rental/{id}", method = RequestMethod.GET)
    public Rental viewSpecificProperty(@PathVariable int id) {
        return dao.viewSpecificProperty(id);
    }

    @RequestMapping(path = "/rental/landlord/{id}", method = RequestMethod.GET)
    public List<Rental> viewSpecificOwnedProperty(@PathVariable int id) {
        return dao.propertiesByLandlord(id);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/rental", method = RequestMethod.POST)
    public boolean addNewProperty(@RequestBody Rental rental) {
          return dao.addNewProperty(rental);

    }

    @CrossOrigin
    @RequestMapping(path = "/rental", method = RequestMethod.PUT)
    public boolean updateRental(@RequestBody Rental rental) {
        return dao.updateProperty(rental);

    }




}

