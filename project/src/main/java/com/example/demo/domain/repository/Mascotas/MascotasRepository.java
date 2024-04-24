package com.example.demo.domain.repository.Mascotas;

import com.example.demo.domain.entity.mascotas.Mascotas;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.MascotaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotasRepository extends JpaRepository<Mascotas, Integer> {

    // Método para verificar existencia de propietario
    boolean existsByPropietarios(Propietarios propietarios);

    // Método para obtener todas las mascotas con la proyección
    List<MascotaProjection> findBy();

    // Método para obtener una mascota por su ID
    Optional<MascotaProjection> findMascotasByIdMascota(Integer idMascota);
}
