package com.example.demo.domain.repository.Propietarios;

import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.propietarios.LocacionPropietario;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPropietarioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocacionPropietarioRepository  extends JpaRepository<LocacionPropietario , Integer> {
    // Método para encontrar paseadores únicos para cada locación
    boolean existsByPropietarios(Propietarios propietarios);

    // Método para obtener todas las locaciones de paseadores
    List<LocacionPropietarioProjection> findBy();

    // Método apra obtener una locación de paseador por su ID
    Optional<LocacionPropietarioProjection> findLocacionPropietarioByIdLocacionPropietario(Integer idLocacionPropietario);
}
