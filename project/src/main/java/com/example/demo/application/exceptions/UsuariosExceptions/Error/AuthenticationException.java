package com.example.demo.application.exceptions.UsuariosExceptions.Error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Se usa cuando la solicitud enviada por el cliente es incorrecta o no se puede procesar.
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message){

        super(message);
    }
}
