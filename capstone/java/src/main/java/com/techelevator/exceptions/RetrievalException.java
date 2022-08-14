package com.techelevator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Could not find user retrieval questions")
public class RetrievalException extends Exception{
    public RetrievalException(){
        super("Could not find user retrieval questions");
    }
}
