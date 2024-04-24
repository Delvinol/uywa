package com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface LocacionPaseadorProjection {

    // Proyección para mostrar locaciones de paseadores totales o por ID
    @Value("#{target.Paseadores.User.nombres}")
    String getNombres();

    @Value("#{target.Paseadores.User.id}")
    Integer getId();

    @Value("#{target.Paseadores.idPaseador}")
    Integer getIdPaseador();
    @Value("#{target.idLocacionPaseador}")
    Integer getIdLocacionPaseador();
    @Value("#{target.Paseadores.User.apellidos}")
    String getApellidos();
    BigDecimal getLatitud();

    BigDecimal getLongitud();
}
