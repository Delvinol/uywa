package com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "El nombre del usuario no puede ser nulo")
    @NotBlank(message = "El nombre del usuario no puede estar vacía")
    @Size(max = 20)// Estableciendo el mínimo y máximom de caracteres en Strings
    private String nombres;

    @NotNull(message = "El apellido del usuario no puede ser nulo")
    @NotBlank(message = "El apellido del usuario no puede estar vacío")
    @Size(max = 20)
    private String apellidos;

    @NotNull(message = "El apodo del usuario no puede ser nulo")
    @NotBlank(message = "El apodo del usuario no puede estar vacío")
    @Size(max = 20)
    private String apodo;

    @NotNull(message = "La direccion del usuario no puede ser nulo")
    @NotBlank(message = "El direccion del usuario no puede estar vacía")
    @Size(max = 20)
    private String direccion;

    @NotNull(message = "La edad del usuario no puede ser nula")
    @NotBlank(message = "La edad del usuario no puede estar vacía")
    @Min(value = 18, message = "La edad mínima es 18")
    @Max(value = 80, message = "La edad máxima es 100")
    private Integer edad;

    @NotNull(message = "El celular del usuario no puede ser nulo")
    @NotBlank(message = "El celular del usuario no puede estar vacío")
    @Size(max = 9)
    private String celular;

    @NotNull(message = "El dni del usuario no puede ser nulo")
    @NotBlank(message = "El dni del usuario no puede estar vacía")
    @Size(max = 8)
    private String dni;

    @NotNull(message = "El email del usuario no puede ser nulo")
    @NotBlank(message = "El email del usuario no puede estar vacía")
    @Size(max = 50)
    @Email
    private String email;

    @NotNull(message = "La contraseña del usuario no puede ser nulo")
    @NotBlank(message = "La contraseña del usuario no puede estar vacía")
    @Size(max = 255)
    private String password;
}
