package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Unable to properly add a user to a rental property")
public class UserToPropertyException extends Exception{
    public UserToPropertyException()
    {
        super("Could not add the renter to the property. Either you were trying to add a " +
                "user that was not a renter, or you were adding a renter to someone else's " +
                "listing");
    }
}
