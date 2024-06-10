package com.kindergarten.kindergarten.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class WrongActionException extends RuntimeException{
    public WrongActionException(String message) {
        super(message);
    }
}