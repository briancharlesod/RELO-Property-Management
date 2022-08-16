package com.techelevator.controller;

import com.sun.tools.javac.Main;
import com.techelevator.dao.JdbcMaintenanceDao;
import com.techelevator.dao.MaintenanceDao;
import com.techelevator.exceptions.UnauthorizedAccessException;
import com.techelevator.model.Maintenance;

import com.techelevator.model.User;
import com.techelevator.model.UserMaintenance;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class MaintenanceController {
    private JdbcMaintenanceDao dao;
    private List<String> Maintenance;

    public MaintenanceController(JdbcMaintenanceDao dao) {
        this.dao = dao;
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/maintenance/all/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Maintenance> viewMaintenanceRequests(@PathVariable int id, Principal principal) throws UnauthorizedAccessException {
        System.out.println("Round1");
        List<Maintenance> maintenanceList = dao.viewMaintenanceRequests(id, principal.getName());
        System.out.println("good so far");
        if(maintenanceList == null)
        {
            throw new UnauthorizedAccessException();
        }
        System.out.println(maintenanceList.size());
        return maintenanceList;
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/maintenance/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Maintenance getMaintenanceRequest(@PathVariable int id, Principal principal) throws UnauthorizedAccessException{
        Maintenance maintenance = dao.viewSpecificMaintenanceRequests(id, principal.getName());
        if(maintenance == null)
        {
            throw new UnauthorizedAccessException();
        }
        return maintenance;
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/rental/maintenance/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Maintenance> getMaintenanceByProperty(@PathVariable int id, Principal principal) throws UnauthorizedAccessException{
        List<Maintenance> maintenance = dao.maintenanceByProperty(id, principal.getName());
        if(maintenance == null)
        {
            throw new UnauthorizedAccessException();
        }
        return maintenance;
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD') OR hasRole('ROLE_RENTER')")
    @RequestMapping(path = "/maintenance", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMaintenanceRequest(@RequestBody @Valid Maintenance request, Principal principal) throws UnauthorizedAccessException{
        int maintenanceRequest = dao.addMaintenanceRequest(request, principal.getName());
        if(maintenanceRequest > 0)
        {
            throw new UnauthorizedAccessException();
        }
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/maintenance/complete/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void setCompleted(@PathVariable int id, Principal principal) throws UnauthorizedAccessException {
        boolean complete = dao.completeMaintenanceRequest(id, principal.getName());
        if(!complete)
        {
            throw new UnauthorizedAccessException();
        }
    }

}


