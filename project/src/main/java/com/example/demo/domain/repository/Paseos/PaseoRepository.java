package com.example.demo.domain.repository.Paseos;

import com.example.demo.domain.entity.paseos.Paseos;
import com.example.demo.domain.entity.paseos.Reservas;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaseoRepository  extends JpaRepository<Paseos, Integer> {

    // Para verificar que solo haya una reserva relacionada
    boolean existsByReservas(Reservas reservas);

    // Método para obtener todos los paseos por proyección
    List<PaseoProjection> findBy();

    // Método para obtener un paseo por su ID
    Optional<PaseoProjection> findPaseosByIdPaseo(Integer idPaseo);
}
