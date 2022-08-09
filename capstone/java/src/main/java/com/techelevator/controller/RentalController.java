package com.techelevator.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping
@RestController
@CrossOrigin
public class RentalController {


    private List<String> rentals;

    public RentalController() {
        rentals = new ArrayList<>(Arrays.asList("Rental ID", "Address", "Rent", "Bathroom",
                "Bedroom", "Rented"));
    }

    @RequestMapping(path = "/properties", method = RequestMethod.GET)
    public List<String> getProperties() {
        return rentals;
    }

    @RequestMapping(path = "/properties/{id}", method = RequestMethod.GET)
    public List<String> getProperty() {
        return dao.getRentals(id); //change when dao is built

    }

    @RequestMapping(path = "/properties", method = RequestMethod.POST)
    public void addProperty(String property) {
        if (property != null) {
            property.add(property); //fix this
        }
    }

    ;
}
