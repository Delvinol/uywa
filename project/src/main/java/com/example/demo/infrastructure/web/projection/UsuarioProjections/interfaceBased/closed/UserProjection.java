package com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserProjection {

    // La convención de nomenclatura de Spring Data JPA sigue un patrón
    // donde interpreta los métodos en la interfaz de proyección según
    // los nombres de las propiedades de la entidad. En este caso, al
    // nombrar el método como getUserNombres(), Spring Data JPA asumirá
    // que está accediendo al campo nombres de la entidad User. Esto es
    // posible gracias al análisis de la expresión del método y su mapeo
    // con los campos de la entidad.

    // Renombrando para evitar ambiguedad
    String getNombres();
    String getApellidos();
    String getApodo();
    String getDireccion();
    Integer getEdad();
    String getCelular();
    String getDni();
    String getEmail();

}
