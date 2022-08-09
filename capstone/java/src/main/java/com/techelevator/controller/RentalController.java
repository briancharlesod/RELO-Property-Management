package com.techelevator.controller;


import com.techelevator.dao.RentalDao;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class RentalController {
    private RentalDao dao;
    private List<String> Rental;

    @RequestMapping(path = "/rental", method = RequestMethod.GET)
    public List<String> viewAllAvailableProperties() {
        return viewAllAvailableProperties();
    }

    @RequestMapping(path = "/rental/{id}", method = RequestMethod.GET)
    public List<String> viewSpecificProperty(@PathVariable int id) {
        return (List<String>) dao.viewSpecificProperty(id);
    }

    @RequestMapping(path = "/rental", method = RequestMethod.POST)
    public void addNewProperty(String request) {
        if (request != null) {
            Rental.add(request);
        }
    }


}

