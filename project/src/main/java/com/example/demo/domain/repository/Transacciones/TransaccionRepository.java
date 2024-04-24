package com.example.demo.domain.repository.Transacciones;

import com.example.demo.domain.entity.transacciones.Transacciones;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.TransaccionProjection;
import jakarta.websocket.OnOpen;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transacciones, Integer> {

    // Método para obtener todas las transacciones con proyecciones
    List<TransaccionProjection> findBy();

    // Método para obtener una transacción por su ID
    Optional<TransaccionProjection> findTransaccionesByIdTransaccion(Integer idTransaccion);
}
