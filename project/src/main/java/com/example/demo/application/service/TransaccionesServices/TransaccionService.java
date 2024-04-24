package com.example.demo.application.service.TransaccionesServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.TransaccionesDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.TransaccionProjection;

import java.util.List;
import java.util.Optional;

public interface TransaccionService {

    // Definiendo interfaz para realizar el registro de transacciones
    TransaccionesDTO registrarTransacciones(TransaccionesDTO transaccionesDTO);

    // Definiendo interfaz para realizar la edición de transacciones
    TransaccionesDTO editarTransacciones(Integer id_transaccion, TransaccionesDTO transaccionesDTO);

    // Definiendo Interfaz para mostrar las transacciones registradas
    List<TransaccionProjection> findBy();

    // Definiendo interfaz para mostrar una transaccion por su ID
    Optional<TransaccionProjection> findTransaccionByIdTransaccionr(Integer idTransaccion);

    // Definiendo interfaz para eliminar una transacción por su ID
    boolean deleteTransaccionById(Integer idTransaccion);
}
