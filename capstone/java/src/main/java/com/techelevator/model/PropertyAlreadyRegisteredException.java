package com.techelevator.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PropertyAlreadyRegisteredException {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Property Already Listed")
    public class PropertyAlreadyRegistered extends RuntimeException {
    }}
