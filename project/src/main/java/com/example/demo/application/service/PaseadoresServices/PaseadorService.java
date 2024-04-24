package com.example.demo.application.service.PaseadoresServices;


import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;

import java.util.List;
import java.util.Optional;

public interface PaseadorService {

    // DEFINIENDO INTERFAZ para realizar el registro de Paseadores
    PaseadoresDTO registrarPaseador(PaseadoresDTO paseadoresDTO);

    // DEFINIENDO INTERFAZ para realizar la edición de Paseadores
    PaseadoresDTO editarPaseador(Integer id_paseador, PaseadoresDTO paseadoresDTO);

    // DEFINIENDO INTERFAZ para mostrar a los paseadores registrados
    List<PaseadorProjection> findBy();

    // DEFINIENDO INTERFAZ para mostrar a un paseador por su ID
    Optional<PaseadorProjection> findPaseadoresByIdPaseador(Integer idPaseador);

    // DEFINIENDO INTERFAZ para eliminar un paseador por su ID
    boolean deletePaseadorById(Integer idPaseador);
}

