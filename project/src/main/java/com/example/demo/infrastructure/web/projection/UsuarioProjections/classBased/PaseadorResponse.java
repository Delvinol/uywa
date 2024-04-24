package com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// DEFINIENDO DTO QUE SALDRA COMO RESPUESTA AL REGISTRAR Y EDITAR UN USUARIO
public class PaseadorResponse {

    private Integer IdPaseador;
    private Integer IdUsuario;
    private String nombreCategoria;
}
