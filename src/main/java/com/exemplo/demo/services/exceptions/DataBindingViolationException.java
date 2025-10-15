package com.exemplo.demo.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.dao.DataIntegrityViolationException;


@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataBindingViolationException extends DataIntegrityViolationException{

     public DataBindingViolationException (String message) {
        super(message);
    }
}
