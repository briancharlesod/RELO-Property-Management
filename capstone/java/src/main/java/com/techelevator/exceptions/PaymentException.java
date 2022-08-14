package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Could not pay rent")
public class PaymentException extends Exception{
    public PaymentException(){
        super("Problem encountered paying rent, try again");
    }
}
