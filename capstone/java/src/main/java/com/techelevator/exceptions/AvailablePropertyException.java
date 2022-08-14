package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Could not find any available properties")
public class AvailablePropertyException extends Exception{
    public AvailablePropertyException()
    {
        super("Could not find any properties");
    }
}
