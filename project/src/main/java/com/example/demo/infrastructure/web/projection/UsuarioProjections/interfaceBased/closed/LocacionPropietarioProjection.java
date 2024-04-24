package com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)

public interface LocacionPropietarioProjection {

    // Proyección para mostrar locaciones de paseadores totales o por ID
    @Value("#{target.Propietarios.User.nombres}")
    String getNombres();

    BigDecimal getLatitud();

    BigDecimal getLongitud();

}
