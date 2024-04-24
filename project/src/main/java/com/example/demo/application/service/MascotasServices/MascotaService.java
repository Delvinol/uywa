package com.example.demo.application.service.MascotasServices;

import com.example.demo.domain.entity.mascotas.Mascotas;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.MascotaDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.MascotaProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;

import java.util.List;
import java.util.Optional;

public interface MascotaService {

    // DEFINIENDO INTERFAZ para realizar el registro de Mascotas
    MascotaDTO registrarMascota(MascotaDTO mascotaDTO);

    // DEFINIENDO INTERFAZ para realizar la edición de Mascotas
    MascotaDTO editarMascota(Integer id_mascota, MascotaDTO mascotaDTO);

    // DEFINIENDO INTERFAZ para mostrar a las mascotas registrados
    List<MascotaProjection> findBy();

    // DEFINIENDO INTERFAZ para mostrar a una mascota por su ID
    Optional<MascotaProjection> findMascotaByIdMascota(Integer idMascota);

    // DEFINIENDO INTERFAZ para eliminar una mascota por su ID
    boolean deleteMascotaById(Integer idMascota);
}
