package com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
@JsonInclude(JsonInclude.Include.NON_NULL)

public interface PropietarioProjection {

    @Value("#{target.User.nombres}")
    String getNombres();
    @Value("#{target.User.apellidos}")
    String getApellidos();
    Integer getCalificacion();
    String getComentario();
    String getPreferenciasPaseo();
    BigDecimal getSaldo();
    Boolean getDisponibilidad();
    String getUbicacion();
}
