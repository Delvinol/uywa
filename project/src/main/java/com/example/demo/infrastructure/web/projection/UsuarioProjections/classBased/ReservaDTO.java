package com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaDTO {
    // Campos para registrar y editar
    @NotNull(message = "El id de Propietario no puede ser nula")
    @NotBlank(message = "El id del Propietario no puede estar vacía")
    private Integer IdPropietario;

    @NotNull(message = "El id del Paseador no puede ser nula")
    @NotBlank(message = "El id del Paseador no puede estar vacía")
    private Integer IdPaseador;

    @NotNull(message = "El monto no puede ser nulo")
    @NotBlank(message = "El monto no puede estar vacío")
    @Digits(integer = 10, fraction = 2, message = "El monto debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private BigDecimal monto;


    @Future(message = "La fecha de reserva debe estar en el futuro")
    @NotNull(message = "La fecha de reserva no puede ser nula")
    private Timestamp fechaReserva;

    @NotNull(message = "La duración del paseo no puede ser nula")
    @Min(value = 30, message = "La duración mínima del paseo es de 30 minutos")
    @Max(value = 240, message = "La duración máxima del paseo es de 240 minutos (4 horas)")
    private LocalTime duracionPaseo;

    @NotNull(message = "El detalle no puede ser nulo")
    @NotBlank(message = "El detalle no puede estar vacío")
    @Size(min = 1, max = 255, message = "El monto debe tener un máximo de 255 caracteres")
    private String detalles;

    @NotNull(message = "El punto de encuentro no puede ser nulo")
    @NotBlank(message = "El punto de encuentro no puede estar vacío")
    @Size(min = 1, max = 20, message = "El punto de encuentro  debe tener un máximo de 20 caracteres")
    private String puntoEncuentro;

    @NotNull(message = "El lugar de paseo no puede ser nulo")
    @NotBlank(message = "El lugar de paseo no puede estar vacío")
    @Size(min = 1, max = 20, message = "El lugar de paseo debe tener un máximo de 20 caracteres")
    private String lugarPaseo;


    // Respuesta para frontend idreserva, id propietario, id paseador, monto,
    private Integer IdReserva;
}
