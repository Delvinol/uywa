package com.example.demo.infrastructure.security.configuration.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Representación del DTO
    private String nombres;
    private String apellidos;
    private Integer tipoUsuarioId;
    private String apodo;
    private String direccion;
    private Integer edad;
    private String celular;
    private String dni;
    private String email;
    private String password;
}
