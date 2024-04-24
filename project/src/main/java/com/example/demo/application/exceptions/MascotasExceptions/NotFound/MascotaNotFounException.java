package com.example.demo.application.exceptions.MascotasExceptions.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MascotaNotFounException extends RuntimeException {

    public MascotaNotFounException (String message){
        super(message);
    }
}
