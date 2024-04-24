package com.example.demo.infrastructure.security.configuration.authentication;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

    // Funciona como una DTO

    private String token;
    private Integer IdTipoUsuario;// Agregando el campo tipo de Usuari
    private Integer IdPaseador;
    private Integer IdPropietario;
    private  Integer Id;
    private String nombres;
    private String apellidos;
}
