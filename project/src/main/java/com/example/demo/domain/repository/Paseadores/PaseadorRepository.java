package com.example.demo.domain.repository.Paseadores;

import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaseadorRepository extends JpaRepository<Paseadores, Integer> {

    boolean existsByUser(User user);

    // Método para obtener todos los paseadores con la proyección
    List<PaseadorProjection> findBy();


    // Método para obtener un paseador por su ID
    Optional<PaseadorProjection> findPaseadoresByIdPaseador(Integer idPaseador);
}
