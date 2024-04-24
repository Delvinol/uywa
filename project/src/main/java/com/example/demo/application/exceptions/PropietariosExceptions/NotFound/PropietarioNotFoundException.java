package com.example.demo.application.exceptions.PropietariosExceptions.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PropietarioNotFoundException extends RuntimeException {

    public PropietarioNotFoundException(String message){
        super(message);
    }
}
