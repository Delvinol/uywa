package com.example.demo.application.service.PaseosServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.ReservaDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.TransaccionesDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.ReservaProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.TransaccionProjection;

import java.util.List;
import java.util.Optional;

public interface ReservaService {
    // Definiendo interfaz para realizar el registro de reservas
    ReservaDTO registrarReservas(ReservaDTO reservaDTO);

    // Definiendo interfaz para realizar la edición de reservas
    ReservaDTO editarReservas(Integer id_reserva, ReservaDTO reservaDTO);

    // Definiendo Interfaz para mostrar las reservas registradas
    List<ReservaProjection> findBy();

    // Definiendo interfaz para mostrar una reserva por su ID
    Optional<ReservaProjection> findReservaByIdReserva(Integer IdReserva);

    // Definiendo interfaz para eliminar una reserva por su ID
    boolean deleteReservaById(Integer idReserva);
}
