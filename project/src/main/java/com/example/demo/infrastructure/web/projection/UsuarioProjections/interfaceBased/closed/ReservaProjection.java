package com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;
@JsonInclude(JsonInclude.Include.NON_NULL)

public interface ReservaProjection {

    Integer getIdReserva();
    BigDecimal getMonto();
    Timestamp getFechaReserva();
    LocalTime getDuracionPaseo();
    String getDetalles();
    String getPuntoEncuentro();

    String getLugarPaseo();

}
