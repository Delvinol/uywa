package com.example.demo.application.exceptions.MascotasExceptions.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransaccionNotFoundException extends RuntimeException{

    public TransaccionNotFoundException(String message){
        super(message);
    }
}
