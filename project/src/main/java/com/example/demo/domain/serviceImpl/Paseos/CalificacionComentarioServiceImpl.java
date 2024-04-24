package com.example.demo.domain.serviceImpl.Paseos;

import com.example.demo.application.exceptions.PaseosExceptions.NotFound.CalificacionComentarioNotFoundException;
import com.example.demo.application.exceptions.PaseosExceptions.NotFound.PaseoNotFoundException;
import com.example.demo.application.service.PaseosServices.CalificacionComentarioService;
import com.example.demo.domain.entity.paseos.CalificacionesComentarios;
import com.example.demo.domain.entity.paseos.Paseos;
import com.example.demo.domain.repository.Paseos.CalificacionComentarioRepository;
import com.example.demo.domain.repository.Paseos.PaseoRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.CalificacionesComentariosDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.CalificacionesComentariosProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalificacionComentarioServiceImpl implements CalificacionComentarioService {

    private final CalificacionComentarioRepository calificacionComentarioRepository;
    private final PaseoRepository paseoRepository;
    @Override
    public CalificacionesComentariosDTO registrarCalificacionesComentarios(CalificacionesComentariosDTO calificacionesComentariosDTO) {
        Paseos paseos = paseoRepository.findById(calificacionesComentariosDTO.getIdPaseo())
                .orElseThrow(()-> new PaseoNotFoundException("Id del paso no encontrado"));
        CalificacionesComentarios calificacionesComentarios = CalificacionesComentarios.builder()
                .paseos(paseos)
                .comentario(calificacionesComentariosDTO.getComentario())
                .calificacion(calificacionesComentariosDTO.getCalificacion())
                .build();
        // Guardando los cambios haciendo uso del repositorio de LocacionComentario
        calificacionesComentarios = calificacionComentarioRepository.save(calificacionesComentarios);
        // Personalizando respuesta
        Integer IdCaliCome = calificacionesComentarios.getIdCalificacioncomentario();
        Integer IdPaseo = calificacionesComentarios.getPaseos().getIdPaseo();
        Integer IdReserva = calificacionesComentarios.getPaseos().getReservas().getIdReserva();
        Integer IdPropietario = calificacionesComentarios.getPaseos().getReservas().getPropietarios().getIdPropietario();
        Integer IdPaseador = calificacionesComentarios.getPaseos().getReservas().getPaseadores().getIdPaseador();

        return CalificacionesComentariosDTO.builder()
                .IdCalificacionComentario(IdCaliCome)
                .idPaseo(IdPaseo)
                .IdReserva(IdReserva)
                .IdPropietario(IdPropietario)
                .IdPaseador(IdPaseador)
                .comentario(calificacionesComentariosDTO.getComentario())
                .calificacion(calificacionesComentariosDTO.getCalificacion())
                .build();
    }

    @Override
    public CalificacionesComentariosDTO editarCalificacionesComentarios(Integer id_calificacioncomentario, CalificacionesComentariosDTO calificacionesComentariosDTO) {
        CalificacionesComentarios calificacionesComentariosExistente = calificacionComentarioRepository.findById(id_calificacioncomentario)
                .orElseThrow(()-> new CalificacionComentarioNotFoundException("Id de calificacion y comentario no encontrados"));
        Paseos paseos = calificacionesComentariosExistente.getPaseos();
        if (calificacionesComentariosDTO.getIdPaseo() != null){
            paseos = paseoRepository.findById(calificacionesComentariosDTO.getIdPaseo())
                    .orElseThrow(()-> new PaseoNotFoundException("Id del paseo no encontrado"));
        }
        // Actualizando campos
        calificacionesComentariosExistente.setComentario(calificacionesComentariosDTO.getComentario());
        calificacionesComentariosExistente.setCalificacion(calificacionesComentariosDTO.getCalificacion());
        // Guardando los datos actualizados
        calificacionesComentariosExistente = calificacionComentarioRepository.save(calificacionesComentariosExistente);

        // Personalizando respuesta
        Integer IdCaliCome = calificacionesComentariosExistente.getIdCalificacioncomentario();
        Integer IdPaseo = calificacionesComentariosExistente.getPaseos().getIdPaseo();
        Integer IdReserva = calificacionesComentariosExistente.getPaseos().getReservas().getIdReserva();
        Integer IdPropietario = calificacionesComentariosExistente.getPaseos().getReservas().getPropietarios().getIdPropietario();
        Integer IdPaseador = calificacionesComentariosExistente.getPaseos().getReservas().getPaseadores().getIdPaseador();

        return CalificacionesComentariosDTO.builder()
                .IdCalificacionComentario(IdCaliCome)
                .idPaseo(IdPaseo)
                .IdReserva(IdReserva)
                .IdPropietario(IdPropietario)
                .IdPaseador(IdPaseador)
                .comentario(calificacionesComentariosDTO.getComentario())
                .calificacion(calificacionesComentariosDTO.getCalificacion())
                .build();
    }

    @Override
    public List<CalificacionesComentariosProjection> findBy() {
        return calificacionComentarioRepository.findBy();
    }

    @Override
    public Optional<CalificacionesComentariosProjection> findCalificacionComentarioByIdCalificaionComentario(Integer IdCalificacionComentario) {
        return calificacionComentarioRepository.findCalificacionesComentariosByIdCalificacioncomentario(IdCalificacionComentario);
    }

    @Override
    public boolean deleteCalificacionComentarioById(Integer idCalificacionComentario) {
        CalificacionesComentarios calificacionesComentarios = calificacionComentarioRepository.findById(idCalificacionComentario)
                .orElseThrow(()-> new CalificacionComentarioNotFoundException("Id de la califiacion y comentario no encontrado"));
        calificacionComentarioRepository.delete(calificacionesComentarios);
        System.out.println("Se eliminó correctamente la calificacion y comentario del paseo");
        return true;
    }
}
