package com.example.demo.domain.serviceImpl.Transacciones;

import com.example.demo.application.exceptions.MascotasExceptions.NotFound.TransaccionNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.PaseadorNotFoundException;
import com.example.demo.application.exceptions.PropietariosExceptions.NotFound.PropietarioNotFoundException;
import com.example.demo.application.exceptions.TransaccionesExceptions.NotFound.EstadoTransaccionNotFoundException;
import com.example.demo.application.exceptions.TransaccionesExceptions.NotFound.TipoTransaccionNotFoundException;
import com.example.demo.application.service.TransaccionesServices.TransaccionService;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.entity.transacciones.EstadosTransaccion;
import com.example.demo.domain.entity.transacciones.TiposTransaccion;
import com.example.demo.domain.entity.transacciones.Transacciones;
import com.example.demo.domain.repository.Paseadores.PaseadorRepository;
import com.example.demo.domain.repository.Propietarios.PropietarioRepository;
import com.example.demo.domain.repository.Transacciones.EstadoTransaccionRepository;
import com.example.demo.domain.repository.Transacciones.TipoTransaccionRepository;
import com.example.demo.domain.repository.Transacciones.TransaccionRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.TransaccionesDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.TransaccionProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final EstadoTransaccionRepository estadoTransaccionRepository;
    private final TipoTransaccionRepository tipoTransaccionRepository;
    private final PropietarioRepository propietarioRepository;
    private final PaseadorRepository paseadorRepository;

    // Servicio para registrar transacciones
    @Override
    public TransaccionesDTO registrarTransacciones(TransaccionesDTO transaccionesDTO) {
        Paseadores paseadores = paseadorRepository.findById(transaccionesDTO.getIdPaseador())
                .orElseThrow(()-> new PaseadorNotFoundException("Id del paseador no encontrado"));
        Propietarios propietarios = propietarioRepository.findById(transaccionesDTO.getIdPropietario())
                .orElseThrow(()-> new PropietarioNotFoundException("Id del propietario no encontrado"));
        TiposTransaccion tiposTransaccion = tipoTransaccionRepository.findById(transaccionesDTO.getIdTipoTransaccion())
                .orElseThrow(()-> new TipoTransaccionNotFoundException("Id del tipo de transacción no encontrado"));
        EstadosTransaccion estadosTransaccion = estadoTransaccionRepository.findById(transaccionesDTO.getIdEstadoTransaccion())
                .orElseThrow(()-> new EstadoTransaccionNotFoundException("Id del tipo de estado de transaccion no encontrado"));

        Transacciones transacciones = Transacciones.builder()
                .paseadores(paseadores)
                .propietarios(propietarios)
                .tiposTransaccion(tiposTransaccion)
                .estadoTransaccion(estadosTransaccion)
                .monto(transaccionesDTO.getMonto())
                .build();
        // Guardando la transaccion en la base de datos usando su repositorio
        transacciones = transaccionRepository.save(transacciones);
        //Personalizando respuesta
        Integer IdTransaccion = transacciones.getIdTransaccion();
        Integer IdPropietario = transacciones.getPropietarios().getIdPropietario();
        Integer IdPaseador = transacciones.getPaseadores().getIdPaseador();
        BigDecimal monto = transacciones.getMonto();

        return TransaccionesDTO.builder()
                .IdTransaccion(IdTransaccion)
                .IdPropietario(IdPropietario)
                .IdPaseador(IdPaseador)
                .IdTipoTransaccion(transaccionesDTO.getIdTipoTransaccion())
                .IdEstadoTransaccion(transaccionesDTO.getIdEstadoTransaccion())
                .monto(monto)
                .build();
    }

    // Servicio para editar una transaccion por su ID
    @Override
    public TransaccionesDTO editarTransacciones(Integer id_transaccion, TransaccionesDTO transaccionesDTO) {
        Transacciones transaccionExistente = transaccionRepository.findById(id_transaccion)
                .orElseThrow(()-> new TransaccionNotFoundException("Id de la transacción no encontrada."));
        EstadosTransaccion estadosTransaccion = transaccionExistente.getEstadoTransaccion();
        if (transaccionesDTO.getIdEstadoTransaccion() != null){
            estadosTransaccion = estadoTransaccionRepository.findById(transaccionesDTO.getIdEstadoTransaccion())
                    .orElseThrow(()-> new EstadoTransaccionNotFoundException("Id del estado de transacción no ecnontrado"));
        }
        TiposTransaccion tiposTransaccion = transaccionExistente.getTiposTransaccion();
        if (transaccionesDTO.getIdTipoTransaccion() != null){
            tiposTransaccion = tipoTransaccionRepository.findById(transaccionesDTO.getIdTipoTransaccion())
                    .orElseThrow(()-> new TipoTransaccionNotFoundException("Id del tipo de transacción no encontrado"));
        }
        Paseadores paseadores = transaccionExistente.getPaseadores();
        if (transaccionesDTO.getIdPaseador() != null){
            paseadores = paseadorRepository.findById(transaccionesDTO.getIdPaseador())
                    .orElseThrow(()-> new PaseadorNotFoundException("Id del paseador no encontrado"));
        }
        Propietarios propietarios = transaccionExistente.getPropietarios();
        if (transaccionesDTO.getIdPropietario() != null){
            propietarios = propietarioRepository.findById(transaccionesDTO.getIdPropietario())
                    .orElseThrow(()-> new PropietarioNotFoundException("Id del propietario no ecnontrado"));
        }
        // Actualizando campos
        transaccionExistente.setMonto(transaccionesDTO.getMonto());
        // Guardando campos
        transaccionExistente = transaccionRepository.save(transaccionExistente);

        //Personalizando respuesta
        Integer IdTransaccion = transaccionExistente.getIdTransaccion();
        Integer IdPropietario = transaccionExistente.getPropietarios().getIdPropietario();
        Integer IdPaseador = transaccionExistente.getPaseadores().getIdPaseador();
        BigDecimal monto = transaccionExistente.getMonto();

        return TransaccionesDTO.builder()
                .IdTransaccion(IdTransaccion)
                .IdPropietario(IdPropietario)
                .IdPaseador(IdPaseador)
                .IdTipoTransaccion(transaccionesDTO.getIdTipoTransaccion())
                .IdEstadoTransaccion(transaccionesDTO.getIdEstadoTransaccion())
                .monto(monto)
                .build();
    }

    @Override
    public List<TransaccionProjection> findBy() {
        return transaccionRepository.findBy();
    }

    @Override
    public Optional<TransaccionProjection> findTransaccionByIdTransaccionr(Integer idTransaccion) {
        return transaccionRepository.findTransaccionesByIdTransaccion(idTransaccion);
    }

    @Override
    public boolean deleteTransaccionById(Integer idTransaccion) {
        Transacciones transacciones = transaccionRepository.findById(idTransaccion)
                .orElseThrow(()-> new TransaccionNotFoundException("Id de la transaccion no encontrada"));
        transaccionRepository.delete(transacciones);
        System.out.println("Se eliminó correctamente la transacción con el ID: "+ idTransaccion);
        return true;
    }
}
