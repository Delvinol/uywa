package com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropietarioDTO {

    // Creando DTO para realizar el registro y edición de Propietario
    @NotNull(message = "El id de usuario no puede ser nula")
    @NotBlank(message = "El id del usuario no puede estar vacía")
    private Integer IdUsuario;

    //@NotNull(message = "La calificacion no puede ser nula")
    //@NotBlank(message = "la calificacion no puede estar vacía")
    private Integer calificacion;

    //@NotNull(message = "El comentario no puede ser nula")
    //@NotBlank(message = "El comentario no puede estar vacía")
    @Size(max = 255, message = "El comentario no debe tener entre 1 y 255 caracteres")
    private String comentario;

    //@NotNull(message = "La preferencia del paseo no puede ser nula")
    //@NotBlank(message = "La preferencia del paseo no puede estar vacía")
    @Size(max = 255, message = "La preferencia del paseo no debe tener entre 1 y 255 caracteres")
    private String preferenciasPaseo;

    //@NotNull(message = "El saldo no puede ser nula")
    //@NotBlank(message = "El saldo no puede estar vacía")
    @Digits(integer = 10, fraction = 2, message = "La tarifa debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private BigDecimal saldo;

    //@NotNull(message = "La disponibilidad no puede ser nula")
    //@NotBlank(message = "La disponibilidad no puede estar vacía")
    private Boolean disponibilidad;

    //@NotNull(message = "La ubicación no puede ser nula")
    //@NotBlank(message = "La ubicació no puede estar vacía")
    @Size(max = 20, message = "La ubicación debe tener entre 1 y 20 caracteres")
    private String ubicacion;

    // Campos adicionales como respuesta
    private Integer IdTipoUsuario;
    private Integer IdPropietario;
    private String nombres;

}
