package com.example.demo.application.exceptions.PaseosExceptions.NotFound;

public class ReservaNotFoundException extends RuntimeException{

    public ReservaNotFoundException(String message){
        super(message);
    }
}
