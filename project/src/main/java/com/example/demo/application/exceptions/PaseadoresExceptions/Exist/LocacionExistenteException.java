package com.example.demo.application.exceptions.PaseadoresExceptions.Exist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LocacionExistenteException extends RuntimeException {
    public  LocacionExistenteException(String message){
        super(message);
    }
}
