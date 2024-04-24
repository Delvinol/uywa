package com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalificacionesComentariosDTO {



    // Definiendo los campos para el registro del paseador
    @NotNull(message = "El id del paseo no puede ser nulo")
    @NotBlank(message = "El id del paseo no puede estar vacio")
    private Integer idPaseo;

    @NotNull(message = "El comentario no puede ser nula")
    @NotBlank(message = "La comentario no puede estar vacía")
    @Size(max = 255, message = "El comentario debe tener entre 1 y 255 caracteres")
    private String comentario;

    @NotNull(message = "El id de la calificacion no puede ser nulo")
    @NotBlank(message = "El id de la calificacion no puede estar vacio")
    private Integer calificacion;

    // Campos adicionales para enviar respuesta
    private Integer IdCalificacionComentario;
    private Integer IdReserva;
    //  Id del paseo
    private Integer IdPropietario;
    private Integer IdPaseador;

}
