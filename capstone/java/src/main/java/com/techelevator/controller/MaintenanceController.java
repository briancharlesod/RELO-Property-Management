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
public class MaintenanceController {


        private List<String> maintenanceRequest;

        public MaintenanceController(){
            maintenanceRequest = new ArrayList<>(Arrays.asList("maintenance_request","rental_id","maintenance_if"));
        }
        @RequestMapping(path="/maintenance", method = RequestMethod.GET)
        public List<String> getMaintenanceRequests(){
            return maintenanceRequest;
        }
        @RequestMapping(path="/maintenance/{id}", method = RequestMethod.GET)
        public List<String> getMaintenanceRequest(){
            return dao.getMaintenanceRequest(id);

        }
        @RequestMapping(path="/maintenance",method = RequestMethod.POST)
        public void addProperty(String property){
            if(property != null){
                property.add(maintenanceRequest);
            }
        };
    }

