package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends Exception{
    public UserNotFoundException()
    {
        super("Could not find user, try again");
    }
}
