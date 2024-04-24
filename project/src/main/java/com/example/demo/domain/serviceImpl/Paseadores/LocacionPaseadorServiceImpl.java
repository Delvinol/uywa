package com.example.demo.domain.serviceImpl.Paseadores;

import com.example.demo.application.exceptions.PaseadoresExceptions.Exist.LocacionExistenteException;
import com.example.demo.application.exceptions.PaseadoresExceptions.Exist.PaseadorExistenteException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.LocacionPaseadorNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.PaseadorNotFoundException;
import com.example.demo.application.service.PaseadoresServices.LocacionPaseadorService;
import com.example.demo.domain.entity.paseadores.LocacionPaseador;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Paseadores.LocacionPaseadorRepository;
import com.example.demo.domain.repository.Paseadores.PaseadorRepository;
import com.example.demo.domain.repository.Usuarios.UserRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPaseadorDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocacionPaseadorServiceImpl implements LocacionPaseadorService {

    private final LocacionPaseadorRepository locacionPaseadorRepository;
    private final PaseadorRepository paseadorRepository;

    // Servicio para registrar la locación de paseadores
    @Override
    public LocacionPaseadorDTO registrarLocacionPaseador(LocacionPaseadorDTO locacionPaseadorDTO) {
        Paseadores paseadores = paseadorRepository.findById(locacionPaseadorDTO.getIdPaseador())
                .orElseThrow(()-> new PaseadorNotFoundException("Id del paseador no encontrado"));
        // Lógica para verificar si la locación del paseador ya tiene un ID de paseador asignado
        if (locacionPaseadorRepository.existsByPaseadores(paseadores)){
            throw new LocacionExistenteException("Esta locación ya tiene un Id de paseador vinculado");
        }

        LocacionPaseador locacionPaseador = LocacionPaseador.builder()
                .latitud(locacionPaseadorDTO.getLatitud())
                .longitud(locacionPaseadorDTO.getLongitud())
                .paseadores(paseadores)
                .build();

        // Guardando la locación del paseador en la base de datos usando el repositorio
        locacionPaseador = locacionPaseadorRepository.save(locacionPaseador);
        // Personalizando respuesta
        BigDecimal longitud = locacionPaseador.getLongitud();
        BigDecimal latitud = locacionPaseador.getLatitud();
        Integer idPropietario = locacionPaseador.getPaseadores().getIdPaseador();
        Integer idUsuario = locacionPaseador.getPaseadores().getUser().getId();
        Integer tipoUsuario = locacionPaseador.getPaseadores().getUser().getTiposUsuario().getIdTipoUsuario();
        String nombres = locacionPaseador.getPaseadores().getUser().getNombres();
        Integer IdLocacionPaseador = locacionPaseador.getIdLocacionPaseador();
        String Apellidos = locacionPaseador.getPaseadores().getUser().getApellidos();

        return LocacionPaseadorDTO.builder()
                .IdLocacionPaseador(IdLocacionPaseador)
                .longitud(longitud)
                .latitud(latitud)
                .IdPaseador(idPropietario)
                .IdUsuario(idUsuario)
                .IdTipoUsuario(tipoUsuario)
                .nombres(nombres)
                .apellidos(Apellidos)
                .build();
    }


    // Servicio para editar la locación del paseador por su ID
    @Override
    public LocacionPaseadorDTO editarLocacionPaseador(Integer id_locacion_paseador, LocacionPaseadorDTO locacionPaseadorDTO) {
        LocacionPaseador locacionPaseadorExistente = locacionPaseadorRepository.findById(id_locacion_paseador)
                .orElseThrow(()-> new LocacionPaseadorNotFoundException("Locacion de paseador no encontrada"));

        Paseadores paseadores = locacionPaseadorExistente.getPaseadores();
        if (locacionPaseadorDTO.getIdPaseador() != null){
            paseadores = paseadorRepository.findById(locacionPaseadorDTO.getIdPaseador())
                    .orElseThrow(()-> new PaseadorNotFoundException("Id del paseador no encontrado"));
        }

        locacionPaseadorExistente.setLatitud(locacionPaseadorDTO.getLatitud());
        locacionPaseadorExistente.setLongitud(locacionPaseadorDTO.getLongitud());

        locacionPaseadorExistente = locacionPaseadorRepository.save(locacionPaseadorExistente);
        // Personalizando respuesta
        BigDecimal longitud = locacionPaseadorExistente.getLongitud();
        BigDecimal latitud = locacionPaseadorExistente.getLatitud();
        Integer idPropietario = locacionPaseadorExistente.getPaseadores().getIdPaseador();
        Integer idUsuario = locacionPaseadorExistente.getPaseadores().getUser().getId();
        Integer tipoUsuario = locacionPaseadorExistente.getPaseadores().getUser().getTiposUsuario().getIdTipoUsuario();
        String nombres = locacionPaseadorExistente.getPaseadores().getUser().getNombres();
        Integer IdLocacionPaseador = locacionPaseadorExistente.getIdLocacionPaseador();
        String Apellidos = locacionPaseadorExistente.getPaseadores().getUser().getApellidos();

        return LocacionPaseadorDTO.builder()
                .IdLocacionPaseador(IdLocacionPaseador)
                .longitud(longitud)
                .latitud(latitud)
                .IdPaseador(idPropietario)
                .IdUsuario(idUsuario)
                .IdTipoUsuario(tipoUsuario)
                .nombres(nombres)
                .apellidos(Apellidos)
                .build();

    }


    // Servicio para listar la locación de los paseadores registrados
    @Override
    public List<LocacionPaseadorProjection> findBy() {
        return locacionPaseadorRepository.findBy();
    }


    // Servicio para listar una locación de paseador por su ID
    @Override
    public Optional<LocacionPaseadorProjection> findLocacionPaseadorByIdLocacionPaseador(Integer idLocacionPaseador) {
        return locacionPaseadorRepository.findLocacionPaseadorByIdLocacionPaseador(idLocacionPaseador);
    }


    // Servicio para eliminar la locación de paseador
    @Override
    public boolean deleteLocacionPaseadorById(Integer idLocacionPaseador) {
        LocacionPaseador locacionPaseador = locacionPaseadorRepository.findById(idLocacionPaseador)
                .orElseThrow(()-> new LocacionPaseadorNotFoundException("Id de la locación de l paseador no encontrada"));
        locacionPaseadorRepository.delete(locacionPaseador);
        System.out.println("Se eliminió la locación del paseador con el ID: "+ idLocacionPaseador);
        return true;
    }
}
