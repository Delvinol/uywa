package com.example.demo.domain.repository.Transacciones;

import com.example.demo.domain.entity.transacciones.TiposTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTransaccionRepository  extends JpaRepository<TiposTransaccion, Integer> {
}
