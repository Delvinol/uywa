package com.example.demo.domain.serviceImpl.Propietarios;

import com.example.demo.application.exceptions.PaseadoresExceptions.Exist.LocacionExistenteException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.LocacionPaseadorNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.PaseadorNotFoundException;
import com.example.demo.application.exceptions.PropietariosExceptions.NotFound.LocacionPropietarioNotFoundException;
import com.example.demo.application.exceptions.PropietariosExceptions.NotFound.PropietarioNotFoundException;
import com.example.demo.application.service.PaseadoresServices.LocacionPaseadorService;
import com.example.demo.application.service.PropietariosServices.LocacionPropietarioService;
import com.example.demo.domain.entity.paseadores.LocacionPaseador;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.propietarios.LocacionPropietario;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.repository.Propietarios.LocacionPropietarioRepository;
import com.example.demo.domain.repository.Propietarios.PropietarioRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPaseadorDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPropietarioProjection;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocacionPropietarioServiceImpl implements LocacionPropietarioService {

    private final LocacionPropietarioRepository locacionPropietarioRepository;
    private final PropietarioRepository propietarioRepository;

    // Servicio para registrar la locación de propietarios
    @Override
    public LocacionPropietarioDTO registrarLocacionPropietario(LocacionPropietarioDTO locacionPropietarioDTO) {
        Propietarios propietarios = propietarioRepository.findById(locacionPropietarioDTO.getIdPropietario())
                .orElseThrow(() -> new PropietarioNotFoundException("Id del paseador no encontrado"));
        // Lógica para verificar si la locación del paseador ya tiene un ID de paseador asignado
        if (locacionPropietarioRepository.existsByPropietarios(propietarios)) {
            throw new LocacionExistenteException("Esta locación ya tiene un Id de paseador vinculado");
        }

        LocacionPropietario locacionPropietario = LocacionPropietario.builder()
                .latitud(locacionPropietarioDTO.getLatitud())
                .longitud(locacionPropietarioDTO.getLongitud())
                .propietarios(propietarios)
                .build();

        // Guardando la locación del paseador en la base de datos usando el repositorio
        locacionPropietario = locacionPropietarioRepository.save(locacionPropietario);

        // Personalizando respuesta
        BigDecimal longitud = locacionPropietario.getLongitud();
        BigDecimal latitud = locacionPropietario.getLatitud();
        Integer idPropietario = locacionPropietario.getPropietarios().getIdPropietario();
        Integer idUsuario = locacionPropietario.getPropietarios().getUser().getId();
        Integer tipoUsuario = locacionPropietario.getPropietarios().getUser().getTiposUsuario().getIdTipoUsuario();
        String nombres = locacionPropietario.getPropietarios().getUser().getNombres();

        return LocacionPropietarioDTO.builder()
                .longitud(longitud)
                .latitud(latitud)
                .IdPropietario(idPropietario)
                .IdUsuario(idUsuario)
                .IdTipoUsuario(tipoUsuario)
                .nombres(nombres)
                .build();
    }

    // Servicio para editar la locación del paseador por su ID
    @Override
    public LocacionPropietarioDTO editarLocacionPropietario(Integer id_locacion_propietario, LocacionPropietarioDTO locacionPropietarioDTO) {
        LocacionPropietario locacionPropietarioExistente = locacionPropietarioRepository.findById(id_locacion_propietario)
                .orElseThrow(() -> new LocacionPropietarioNotFoundException("Locacion de paseador no encontrada"));

        Propietarios propietarios = locacionPropietarioExistente.getPropietarios();
        if (locacionPropietarioDTO.getIdPropietario() != null) {
            propietarios = propietarioRepository.findById(locacionPropietarioDTO.getIdPropietario())
                    .orElseThrow(() -> new PaseadorNotFoundException("Id del paseador no encontrado"));
        }

        locacionPropietarioExistente.setLatitud(locacionPropietarioDTO.getLatitud());
        locacionPropietarioExistente.setLongitud(locacionPropietarioDTO.getLongitud());

        locacionPropietarioExistente = locacionPropietarioRepository.save(locacionPropietarioExistente);
        // Personalizando respuesta
        BigDecimal longitud = locacionPropietarioExistente.getLongitud();
        BigDecimal latitud = locacionPropietarioExistente.getLatitud();
        Integer idPropietario = locacionPropietarioExistente.getPropietarios().getIdPropietario();
        Integer idUsuario = locacionPropietarioExistente.getPropietarios().getUser().getId();
        Integer tipoUsuario = locacionPropietarioExistente.getPropietarios().getUser().getTiposUsuario().getIdTipoUsuario();
        String nombres = locacionPropietarioExistente.getPropietarios().getUser().getNombres();

        return LocacionPropietarioDTO.builder()
                .longitud(longitud)
                .latitud(latitud)
                .IdPropietario(idPropietario)
                .IdUsuario(idUsuario)
                .IdTipoUsuario(tipoUsuario)
                .nombres(nombres)
                .build();
    }

    // Servicio para listar la locación de los paseadores registrados
    @Override
    public List<LocacionPropietarioProjection> findBy() {
        return locacionPropietarioRepository.findBy();
    }

    // Servicio para listar una locación de paseador por su ID
    @Override
    public Optional<LocacionPropietarioProjection> findLocacionPropietarioByIdLocacionPropietario(Integer idLocacionPropietario) {
        return locacionPropietarioRepository.findLocacionPropietarioByIdLocacionPropietario(idLocacionPropietario);
    }

    // Servicio para eliminar la locación de paseador
    @Override
    public boolean deleteLocacionPropietarioById(Integer idLocacionPropietario) {
        LocacionPropietario locacionPropietario = locacionPropietarioRepository.findById(idLocacionPropietario)
                .orElseThrow(() -> new LocacionPropietarioNotFoundException("Id de la locación de l paseador no encontrada"));
        locacionPropietarioRepository.delete(locacionPropietario);
        System.out.println("Se eliminió la locación del propipetario con el ID: " + idLocacionPropietario);
        return true;
    }
}
