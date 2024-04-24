package com.example.demo.domain.repository.Paseos;

import com.example.demo.domain.entity.paseos.CalificacionesComentarios;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.CalificacionesComentariosProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionComentarioRepository extends JpaRepository<CalificacionesComentarios, Integer> {

    // Método para obtener todas las calificaciones y comentarios por paseo
    List<CalificacionesComentariosProjection> findBy();

    // Método para obtener una calificacion con comentario por su ID
    Optional<CalificacionesComentariosProjection> findCalificacionesComentariosByIdCalificacioncomentario(Integer IdCalificacionComentario);
}
