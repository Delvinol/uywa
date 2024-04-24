package com.example.demo.application.service.PaseosServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.CalificacionesComentariosDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseoDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.CalificacionesComentariosProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseoProjection;

import java.util.List;
import java.util.Optional;

public interface CalificacionComentarioService {

    // Definiendo interfaz para realizar el registro de Calificaciones y comentarios
    CalificacionesComentariosDTO registrarCalificacionesComentarios(CalificacionesComentariosDTO calificacionesComentariosDTO);

    // Definimos interfaz para realizar la edición de calificaciones y comentarios
    CalificacionesComentariosDTO editarCalificacionesComentarios(Integer id_calificacioncomentario, CalificacionesComentariosDTO calificacionesComentariosDTO);

    // Definiendo interfaz para mostrar las calificaciones y comentarios registrados
    List<CalificacionesComentariosProjection> findBy();

    // Definimos interfaz para mostrar un paseo por su ID
    Optional<CalificacionesComentariosProjection> findCalificacionComentarioByIdCalificaionComentario(Integer IdCalificacionComentario);

    // Definiendo interfaz para eliminar un paseo por su ID
    boolean deleteCalificacionComentarioById(Integer idCalificacionComentario);

}
