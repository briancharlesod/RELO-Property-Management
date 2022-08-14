package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not set user to the maintenance request")
public class UserToMaintenanceException extends Exception{
    public UserToMaintenanceException(){
        super("Could not set the employee to the maintenance request. Either you were trying to add a user that was not an employee, " +
                "or you were trying to set an employee to a maintenance request on a property you do not own");
    }
}
