package com.techelevator.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PropertyNotFoundException {
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Property Not Found.")
    public class PropertyNotFound extends RuntimeException {
    }
}
