package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Could not access the rental information of that property")
public class UnauthorizedAccessException extends Exception{
    public UnauthorizedAccessException(){
        super("You are not the associated with that property and cannot access the rental information");
    }
}
