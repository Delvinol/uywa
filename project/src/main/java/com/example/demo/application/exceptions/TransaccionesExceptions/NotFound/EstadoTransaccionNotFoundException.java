package com.example.demo.application.exceptions.TransaccionesExceptions.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstadoTransaccionNotFoundException  extends  RuntimeException{

    public EstadoTransaccionNotFoundException(String message){
        super(message);
    }
}
