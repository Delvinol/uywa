package com.example.demo.domain.repository.Paseos;

import com.example.demo.domain.entity.paseos.Reservas;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.ReservaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reservas, Integer> {

    // Método para obtener todos las reservas por proyección
    List<ReservaProjection> findBy();

    // Método para obtener una reserva por su ID
    Optional<ReservaProjection> findReservasByIdReserva(Integer idReserva);

}
