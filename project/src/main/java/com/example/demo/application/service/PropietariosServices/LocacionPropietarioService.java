package com.example.demo.application.service.PropietariosServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPaseadorDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPropietarioProjection;

import java.util.List;
import java.util.Optional;

public interface LocacionPropietarioService {

    // Definiendo interfaz para realizar el registro de Locaciones de Propietarios
    LocacionPropietarioDTO registrarLocacionPropietario(LocacionPropietarioDTO locacionPropietarioDTO);

    // Definiendo interfaz para realizar la edición de Locaciones de Propietarios
    LocacionPropietarioDTO editarLocacionPropietario(Integer id_locacion_propietario, LocacionPropietarioDTO locacionPropietarioDTO);

    // Definiendo Interfaz para mostrar las locaciones de propietarios registrados
    List<LocacionPropietarioProjection> findBy();

    // Definiendo interfaz para mostrar una locacion de propietario por su ID
    Optional<LocacionPropietarioProjection> findLocacionPropietarioByIdLocacionPropietario(Integer idLocacionPropietario);

    // Definiendo interfaz para eliminar una locación de propietario por su ID
    boolean deleteLocacionPropietarioById(Integer idLocacionPropietario);

}
