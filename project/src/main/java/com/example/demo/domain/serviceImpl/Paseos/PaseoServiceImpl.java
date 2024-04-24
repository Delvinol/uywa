package com.example.demo.domain.serviceImpl.Paseos;

import com.example.demo.application.exceptions.PaseosExceptions.NotFound.PaseoNotFoundException;
import com.example.demo.application.exceptions.PaseosExceptions.NotFound.ReservaNotFoundException;
import com.example.demo.application.service.PaseosServices.PaseoService;
import com.example.demo.domain.entity.paseos.Paseos;
import com.example.demo.domain.entity.paseos.Reservas;
import com.example.demo.domain.repository.Paseos.PaseoRepository;
import com.example.demo.domain.repository.Paseos.ReservaRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseoDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaseoServiceImpl implements PaseoService {

    private final PaseoRepository paseoRepository;
    private final ReservaRepository reservaRepository;
    @Override
    public PaseoDTO registrarPaseos(PaseoDTO paseoDTO) {
        Reservas reservas = reservaRepository.findById(paseoDTO.getIdReserva())
                .orElseThrow(()-> new ReservaNotFoundException("Id de la reserva no encontrada"));
        Paseos paseos = Paseos.builder()
                .reservas(reservas)
                .fechaPaseo(paseoDTO.getFechaPaseo())
                .duracionReal(paseoDTO.getDuracionReal())
                .lugar(paseoDTO.getLugar())
                .comentario(paseoDTO.getComentario())
                .calificacion(paseoDTO.getCalificacion())
                .costo(paseoDTO.getCosto())
                .build();
        // Guardando los cambios haciendo uso del repositorio de paseo
        paseos = paseoRepository.save(paseos);
        // Personalizadno respuesta
        Integer IdPaseo = paseos.getIdPaseo();
        Integer IdReserva = paseos.getReservas().getIdReserva();
        Integer IdPaseador = paseos.getReservas().getPaseadores().getIdPaseador();
        Integer IdPropietario = paseos.getReservas().getPropietarios().getIdPropietario();
        Timestamp FechaPaseo = paseos.getFechaPaseo();
        BigDecimal costo = paseos.getCosto();

        return PaseoDTO.builder()
                .IdPaseo(IdPaseo)
                .IdReserva(IdReserva)
                .IdPropietario(IdPropietario)
                .IdPaseador(IdPaseador)
                .fechaPaseo(FechaPaseo)
                .duracionReal(paseoDTO.getDuracionReal())
                .lugar(paseoDTO.getLugar())
                .comentario(paseoDTO.getComentario())
                .calificacion(paseoDTO.getCalificacion())
                .costo(costo)
                .build();
    }

    @Override
    public PaseoDTO editarPaseo(Integer id_paseo, PaseoDTO paseoDTO) {
        Paseos paseoExistente = paseoRepository.findById(id_paseo)
                .orElseThrow(()-> new PaseoNotFoundException("Id del paseo no encontrado"));
        Reservas reservas = paseoExistente.getReservas();
        if (paseoDTO.getIdReserva() != null){
            reservas = reservaRepository.findById(paseoDTO.getIdReserva())
                    .orElseThrow(()-> new ReservaNotFoundException("Id de la reserva no encontrado"));
        }
        // Actualizando campos
        paseoExistente.setFechaPaseo(paseoDTO.getFechaPaseo());
        paseoExistente.setDuracionReal(paseoDTO.getDuracionReal());
        paseoExistente.setLugar(paseoDTO.getLugar());
        paseoExistente.setComentario(paseoDTO.getComentario());
        paseoExistente.setCalificacion(paseoDTO.getCalificacion());
        paseoExistente.setCosto(paseoDTO.getCosto());
        // Guardando actualizacion
        paseoExistente = paseoRepository.save(paseoExistente);

        // Personalizando respuesta
        Integer IdPaseo = paseoExistente.getIdPaseo();
        Integer IdReserva = paseoExistente.getReservas().getIdReserva();
        Integer IdPaseador = paseoExistente.getReservas().getPaseadores().getIdPaseador();
        Integer IdPropietario = paseoExistente.getReservas().getPropietarios().getIdPropietario();
        Timestamp FechaPaseo = paseoExistente.getFechaPaseo();
        BigDecimal costo = paseoExistente.getCosto();

        return PaseoDTO.builder()
                .IdPaseo(IdPaseo)
                .IdReserva(IdReserva)
                .IdPropietario(IdPropietario)
                .IdPaseador(IdPaseador)
                .fechaPaseo(FechaPaseo)
                .duracionReal(paseoDTO.getDuracionReal())
                .lugar(paseoDTO.getLugar())
                .comentario(paseoDTO.getComentario())
                .calificacion(paseoDTO.getCalificacion())
                .costo(costo)
                .build();
    }

    @Override
    public List<PaseoProjection> findBy() {
        return paseoRepository.findBy();
    }

    @Override
    public Optional<PaseoProjection> findPaseoByIdPaseo(Integer IdPaseo) {
        return paseoRepository.findPaseosByIdPaseo(IdPaseo);
    }

    @Override
    public boolean deletePaseoById(Integer idPaseo) {
        Paseos paseos = paseoRepository.findById(idPaseo)
                .orElseThrow(()-> new PaseoNotFoundException("Id del paso no encontrado"));
        paseoRepository.delete(paseos);
        System.out.println("Se eliminó correctamente el paseo");
        return true;
    }
}
