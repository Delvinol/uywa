package com.example.demo.application.service.PaseosServices;

import com.example.demo.application.exceptions.PaseosExceptions.NotFound.SaldoInsuficienteException;
import com.example.demo.application.exceptions.TransaccionesExceptions.NotFound.EstadoTransaccionNotFoundException;
import com.example.demo.application.exceptions.TransaccionesExceptions.NotFound.TipoTransaccionNotFoundException;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.paseos.Paseos;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.entity.transacciones.EstadosTransaccion;
import com.example.demo.domain.entity.transacciones.TiposTransaccion;
import com.example.demo.domain.entity.transacciones.Transacciones;
import com.example.demo.domain.repository.Paseadores.PaseadorRepository;
import com.example.demo.domain.repository.Paseos.PaseoRepository;
import com.example.demo.domain.repository.Propietarios.PropietarioRepository;
import com.example.demo.domain.repository.Transacciones.EstadoTransaccionRepository;
import com.example.demo.domain.repository.Transacciones.TipoTransaccionRepository;
import com.example.demo.domain.repository.Transacciones.TransaccionRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PagoRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PaseadorRepository paseadorRepository;

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Autowired
    private PaseoRepository paseoRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private EstadoTransaccionRepository estadoTransaccionRepository;

    @Autowired
    private TipoTransaccionRepository tipoTransaccionRepository;

    public void procesarPago(PagoRequestDTO pagoRequest) {
        Optional<Paseadores> optionalPaseador = paseadorRepository.findById(pagoRequest.getIdPaseador());
        Optional<Propietarios> optionalPropietario = propietarioRepository.findById(pagoRequest.getIdPropietario());
        Optional<Paseos> optionalPaseo = paseoRepository.findById(pagoRequest.getIdPaseo());

        if (optionalPaseador.isPresent() && optionalPropietario.isPresent() && optionalPaseo.isPresent()) {
            Paseadores paseador = optionalPaseador.get();
            Propietarios propietario = optionalPropietario.get();
            Paseos paseo = optionalPaseo.get();

            BigDecimal costoPaseo = paseo.getCosto();

            // Verificar si el propietario tiene saldo suficiente
            BigDecimal saldoPropietario = propietario.getSaldo();
            if (saldoPropietario.compareTo(costoPaseo) < 0) {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar el pago");
            }

            // Realizar el pago: restar saldo al propietario y sumar al paseador
            BigDecimal nuevoSaldoPropietario = saldoPropietario.subtract(costoPaseo);
            BigDecimal nuevoSaldoPaseador = paseador.getSaldo().add(costoPaseo);

            // Actualizar los saldos
            propietario.setSaldo(nuevoSaldoPropietario);
            paseador.setSaldo(nuevoSaldoPaseador);

            // Crear una nueva transacción
            Transacciones transaccion = new Transacciones();
            transaccion.setPaseadores(paseador);
            transaccion.setPropietarios(propietario);
            transaccion.setMonto(costoPaseo);

            // Asignar las claves foráneas (FK)
            EstadosTransaccion estadoTransaccion = estadoTransaccionRepository.findById(pagoRequest.getIdEstadoTransaccion())
                    .orElseThrow(() -> new EstadoTransaccionNotFoundException("Estado de transacción no encontrado"));
            transaccion.setEstadoTransaccion(estadoTransaccion);

            TiposTransaccion tipoTransaccion = tipoTransaccionRepository.findById(pagoRequest.getIdTipoTransaccion())
                    .orElseThrow(() -> new TipoTransaccionNotFoundException("Tipo de transacción no encontrado"));
            transaccion.setTiposTransaccion(tipoTransaccion);

            // Guardar la transacción y actualizar los saldos en la base de datos
            transaccionRepository.save(transaccion);
            propietarioRepository.save(propietario);
            paseadorRepository.save(paseador);

            // Resto de tu lógica para procesar el pago
            // ...
        } else {
            // Manejo si no se encuentran los elementos
            throw new EntityNotFoundException("Paseador, Propietario o Paseo no encontrados");
        }
    }
}
