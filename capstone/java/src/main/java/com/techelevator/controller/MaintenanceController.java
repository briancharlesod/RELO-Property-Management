package com.techelevator.controller;

import com.sun.tools.javac.Main;
import com.techelevator.dao.JdbcMaintenanceDao;
import com.techelevator.dao.MaintenanceDao;
import com.techelevator.model.Maintenance;

import com.techelevator.model.User;
import com.techelevator.model.UserMaintenance;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MaintenanceController {
    private JdbcMaintenanceDao dao;
    private List<String> Maintenance;

    public MaintenanceController(JdbcMaintenanceDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/maintenance/all/{id}", method = RequestMethod.GET)
    public List<Maintenance> viewMaintenanceRequests(@PathVariable int id) {

        return dao.viewMaintenanceRequests(id);
    }

    @RequestMapping(path = "/maintenance/{id}", method = RequestMethod.GET)
    public Maintenance getMaintenanceRequest(@PathVariable int id) {
        return dao.viewSpecificMaintenanceRequests(id);
    }

    @RequestMapping(path = "/maintenance", method = RequestMethod.POST)
    public void addMaintenanceRequest(@RequestBody Maintenance request) {
//        if (request != null) {
//            Maintenance.add(request);
//        }
        dao.addMaintenanceRequest(request);
    }

    @RequestMapping(path = "/maintenance/complete", method = RequestMethod.POST)
    public void setCompleted(@RequestBody Maintenance id)
    {
        dao.completeMaintenanceRequest(id.getMaintenanceID());
    }

}


