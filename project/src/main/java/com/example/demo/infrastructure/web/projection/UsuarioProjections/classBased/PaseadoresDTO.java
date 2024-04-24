package com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased;

import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.FileProcessingException;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.IOException;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaseadoresDTO {

    // Definiendo los campos para el registro del paseador
    @NotNull(message = "El id de usuario no puede ser nula")
    @NotBlank(message = "El id del usuario no puede estar vacía")
    private Integer idUsuario;

    // Definiendo los campos para la edición del paseador y su edición
    @NotNull(message = "El id de la categoría no puede ser nula")
    @NotBlank(message = "El id de la categoría no puede estar vacía")
    private Integer idCategoria;

    //@NotNull(message = "La calificacion no puede ser nula")
    //@NotBlank(message = "La calificacion no puede estar vacía")
    private Integer calificacion;

    //@NotNull(message = "La descripción no puede ser nula")
    //@NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 255, message = "La descripción debe tener entre 1 y 255 caracteres")
    private String descripcion;

    //@NotNull(message = "La experiencia no puede ser nula")
    //@NotBlank(message = "La experiencia no puede estar vacía")
    @Size(max = 255, message = "La experiencia debe tener entre 1 y 255 caracteres")
    private String experiencia;

    //@NotNull(message = "La ubicación no puede ser nula")
    //@NotBlank(message = "La ubicació no puede estar vacía")
    @Size(max = 20, message = "La ubicación debe tener entre 1 y 20 caracteres")
    private String ubicacion;

    //@NotNull(message = "La tarifa no puede ser nula")
    //@NotBlank(message = "La tarifa no puede estar vacía")
    @Digits(integer = 10, fraction = 2, message = "La tarifa debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private BigDecimal tarifa;

    //@NotNull(message = "El saldo no puede ser nula")
    //@NotBlank(message = "La tarifa no puede estar vacía")
    @Digits(integer = 10, fraction = 2, message = "El saldo debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private BigDecimal saldo;

    @NotNull(message = "La disponibilidad no puede ser nula")
    @NotBlank(message = "La disponibilidad no puede estar vacía")
    private Boolean disponibilidad;

    // Campos adicionales para enviar respuesta
    private Integer IdTipoUsuario;
    private Integer IdPaseador;
    private String nombres;


}
