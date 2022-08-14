package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not list the property")
public class AddPropertyException extends Exception{
    public AddPropertyException(){
        super("Could not add the property, either you are listing a property in someone else's name, or you are not a landlord");
    }
}
