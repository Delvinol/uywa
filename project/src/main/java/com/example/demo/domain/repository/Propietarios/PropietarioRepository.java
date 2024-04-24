package com.example.demo.domain.repository.Propietarios;

import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PropietarioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietarios, Integer> {

    boolean existsByUser(User user);

    // Método para obtener todos los paseadores con la proyección
    List<PropietarioProjection> findBy();

    // Método para obtener un propietario por su ID
    Optional<PropietarioProjection> findPropietariosByIdPropietario(Integer idPropietario);
}
