package com.techelevator.controller;

import com.techelevator.dao.MaintenanceDao;
import com.techelevator.model.Maintenance;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class MaintenanceController {
    private MaintenanceDao dao;
    private List<String> Maintenance;

    @RequestMapping(path = "/maintenance", method = RequestMethod.GET)
    public List<String> viewMaintenanceRequests() {
        return viewMaintenanceRequests();
    }

    @RequestMapping(path = "/maintenance/{id}", method = RequestMethod.GET)
    public List<Maintenance> getMaintenanceRequest(@PathVariable int id) {
        return dao.viewMaintenanceRequests(id);
    }

    @RequestMapping(path = "/maintenance", method = RequestMethod.POST)
    public void addMaintenanceRequest(String request) {
        if (request != null) {
            Maintenance.add(request);
        }
    }


}


