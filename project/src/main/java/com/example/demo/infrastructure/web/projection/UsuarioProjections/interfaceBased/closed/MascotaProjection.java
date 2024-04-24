package com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface MascotaProjection {

    //Campos que se mostrarán
    Integer getIdMascota();
    String getNombre();
    String getRaza();
    String getEdad();
    String getNecesidades();
}
