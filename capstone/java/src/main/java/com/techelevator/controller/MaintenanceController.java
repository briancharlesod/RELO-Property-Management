package com.techelevator.controller;

import com.sun.tools.javac.Main;
import com.techelevator.dao.JdbcMaintenanceDao;
import com.techelevator.dao.MaintenanceDao;
import com.techelevator.model.Maintenance;

import com.techelevator.model.User;
import com.techelevator.model.UserMaintenance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
public class MaintenanceController {
    private JdbcMaintenanceDao dao;
    private List<String> Maintenance;

    public MaintenanceController(JdbcMaintenanceDao dao) {
        this.dao = dao;
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/maintenance/all/{id}", method = RequestMethod.GET)
    public List<Maintenance> viewMaintenanceRequests(@PathVariable int id, Principal principal) {

        return dao.viewMaintenanceRequests(id, principal.getName());
    }
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/maintenance/{id}", method = RequestMethod.GET)
    public Maintenance getMaintenanceRequest(@PathVariable int id, Principal principal) {
        return dao.viewSpecificMaintenanceRequests(id, principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD') OR hasRole('ROLE_RENTER')")
    @RequestMapping(path = "/maintenance", method = RequestMethod.POST)
    public void addMaintenanceRequest(@RequestBody @Valid Maintenance request, Principal principal) {
        dao.addMaintenanceRequest(request, principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/maintenance/complete/{id}", method = RequestMethod.POST)
    public void setCompleted(@PathVariable int id, Principal principal)
    {
        dao.completeMaintenanceRequest(id, principal.getName());
    }

}


