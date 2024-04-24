package com.example.demo.application.exceptions.PaseadoresExceptions.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaseadorNotFoundException extends  RuntimeException{

    public  PaseadorNotFoundException (String mesasge){
        super(mesasge);
    }
}
