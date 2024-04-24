package com.example.demo.application.service.PaseosServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseoDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseoProjection;

import java.util.List;
import java.util.Optional;

public interface PaseoService {

    // Definiendo interfaz para realizar el registro de paseos
    PaseoDTO registrarPaseos(PaseoDTO paseoDTO);

    // Definimos interfaz para realizar la edición de paseos
    PaseoDTO editarPaseo(Integer id_paseo, PaseoDTO paseoDTO);

    // Definiendo interfaz para mostrar los paseos registrados
    List<PaseoProjection> findBy();

    // Definimos interfaz para mostrar un paseo por su ID
    Optional<PaseoProjection> findPaseoByIdPaseo(Integer IdPaseo);

    // Definiendo interfaz para eliminar un paseo por su ID
    boolean deletePaseoById(Integer idPaseo);
}
