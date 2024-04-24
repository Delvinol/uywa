package com.example.demo.application.service.PaseadoresServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPaseadorDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;

import java.util.List;
import java.util.Optional;

public interface LocacionPaseadorService {

    // Definiendo interfaz para realizar el registro de Locaciones de Paseadores
    LocacionPaseadorDTO registrarLocacionPaseador(LocacionPaseadorDTO locacionPaseadorDTO);

    // Definiendo interfaz para realizar la edición de Locaciones de Paseadores
    LocacionPaseadorDTO editarLocacionPaseador(Integer id_locacion_paseador, LocacionPaseadorDTO locacionPaseadorDTO);

    // Definiendo Interfaz para mostrar las locaciones de paseadores registrados
    List<LocacionPaseadorProjection> findBy();

    // Definiendo interfaz para mostrar una locacion de paseador por su ID
    Optional<LocacionPaseadorProjection> findLocacionPaseadorByIdLocacionPaseador(Integer idLocacionPaseador);

    // Definiendo interfaz para eliminar una locación de paseador por su ID
    boolean deleteLocacionPaseadorById(Integer idLocacionPaseador);
}
