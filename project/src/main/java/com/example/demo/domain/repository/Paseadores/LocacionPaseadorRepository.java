package com.example.demo.domain.repository.Paseadores;

import com.example.demo.domain.entity.paseadores.LocacionPaseador;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocacionPaseadorRepository extends JpaRepository<LocacionPaseador, Integer> {

    // Método para encontrar paseadores únicos para cada locación
    boolean existsByPaseadores(Paseadores paseadores);

    // Método para obtener todas las locaciones de paseadores
    List<LocacionPaseadorProjection> findBy();

    // Método apra obtener una locación de paseador por su ID
    Optional<LocacionPaseadorProjection> findLocacionPaseadorByIdLocacionPaseador(Integer idLocacionPaseador);

}
