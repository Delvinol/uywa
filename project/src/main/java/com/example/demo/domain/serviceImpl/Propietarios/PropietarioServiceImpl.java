package com.example.demo.domain.serviceImpl.Propietarios;

import com.example.demo.application.exceptions.PropietariosExceptions.Exist.PropietarioExistenteException;
import com.example.demo.application.exceptions.PropietariosExceptions.NotFound.PropietarioNotFoundException;
import com.example.demo.application.exceptions.UsuariosExceptions.NotFound.UserNotFoundException;
import com.example.demo.application.service.PropietariosServices.PropietarioService;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Propietarios.PropietarioRepository;
import com.example.demo.domain.repository.Usuarios.UserRepository;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PropietarioProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropietarioServiceImpl implements PropietarioService {

    private final UserRepository userRepository;
    private final PropietarioRepository propietarioRepository;


    // Servicio para registrar Propietarios
    @Override
    public PropietarioDTO registrarPropietario(PropietarioDTO propietarioDTO) {
        User user = userRepository.findById(propietarioDTO.getIdUsuario())
                .orElseThrow(()-> new UserNotFoundException("Id del usuario no encontrado"));

        // Lógica para verificar si el usuario ya tiene un propietario asignado
        if (propietarioRepository.existsByUser(user)){
            throw new PropietarioExistenteException("Este usuario ya tiene una cuenta como propietario");
        }
        // Realizando la inserción de datos
        Propietarios propietarios = Propietarios.builder()
                .user(user)
                .calificacion(propietarioDTO.getCalificacion())
                .comentario(propietarioDTO.getComentario())
                .preferenciasPaseo(propietarioDTO.getPreferenciasPaseo())
                .saldo(propietarioDTO.getSaldo())
                .disponibilidad(propietarioDTO.getDisponibilidad())
                .ubicacion(propietarioDTO.getUbicacion())
                .build();

        propietarios = propietarioRepository.save(propietarios);
        // Personalizando respuesta
        Integer IdUsuario = propietarios.getUser().getId();
        Integer IdTipoUsuario = propietarios.getUser().getTiposUsuario().getIdTipoUsuario();
        Integer IdPropietario = propietarios.getIdPropietario();
        String nombres = propietarios.getUser().getNombres();

        return PropietarioDTO.builder()
                .IdUsuario(IdUsuario)
                .IdTipoUsuario(IdTipoUsuario)
                .IdPropietario(IdPropietario)
                .nombres(nombres)
                .calificacion(propietarioDTO.getCalificacion())
                .comentario(propietarioDTO.getComentario())
                .preferenciasPaseo(propietarioDTO.getPreferenciasPaseo())
                .saldo(propietarioDTO.getSaldo())
                .disponibilidad(propietarioDTO.getDisponibilidad())
                .ubicacion(propietarioDTO.getUbicacion())
                .build();
    }


    // Servicio para editar un propietario
    @Override
    public PropietarioDTO editarPropietario(Integer id_propietario, PropietarioDTO propietarioDTO) {
        Propietarios propietarioExistente = propietarioRepository.findById(id_propietario)
                .orElseThrow(()-> new PropietarioNotFoundException("Propietario no encontrado "));

        User user = propietarioExistente.getUser();
        if (propietarioDTO.getIdUsuario() != null){
            user = userRepository.findById(propietarioDTO.getIdUsuario())
                    .orElseThrow(()-> new UserNotFoundException("Id de usuario no encontrado"));
        }

        propietarioExistente.setCalificacion(propietarioDTO.getCalificacion());
        propietarioExistente.setComentario(propietarioDTO.getComentario());
        propietarioExistente.setPreferenciasPaseo(propietarioDTO.getPreferenciasPaseo());
        propietarioExistente.setSaldo(propietarioDTO.getSaldo());
        propietarioExistente.setDisponibilidad(propietarioDTO.getDisponibilidad());
        propietarioExistente.setUbicacion(propietarioDTO.getUbicacion());

        propietarioExistente = propietarioRepository.save(propietarioExistente);
        // Personalizando respuesta
        Integer IdUsuario = propietarioExistente.getUser().getId();
        Integer IdTipoUsuario = propietarioExistente.getUser().getTiposUsuario().getIdTipoUsuario();
        Integer IdPropietario = propietarioExistente.getIdPropietario();
        String nombres = propietarioExistente.getUser().getNombres();

        return PropietarioDTO.builder()
                .IdUsuario(IdUsuario)
                .IdTipoUsuario(IdTipoUsuario)
                .IdPropietario(IdPropietario)
                .nombres(nombres)
                .calificacion(propietarioDTO.getCalificacion())
                .comentario(propietarioDTO.getComentario())
                .preferenciasPaseo(propietarioDTO.getPreferenciasPaseo())
                .saldo(propietarioDTO.getSaldo())
                .disponibilidad(propietarioDTO.getDisponibilidad())
                .ubicacion(propietarioDTO.getUbicacion())
                .build();
    }

    @Override
    public List<PropietarioProjection> findBy() {
        return propietarioRepository.findBy();
    }

    @Override
    public Optional<PropietarioProjection> findPropietariosByIdPropietario(Integer idPropietario) {
        return propietarioRepository.findPropietariosByIdPropietario(idPropietario);
    }

    @Override
    public boolean deletePropietarioById(Integer idPropietario) {
        Propietarios propietarios = propietarioRepository.findById(idPropietario)
                .orElseThrow(()-> new PropietarioNotFoundException("Id del propietario inexistente"+ idPropietario));
        propietarioRepository.delete(propietarios);
        System.out.println("Se eliminó el propietario con el Id: " + idPropietario);
        return true;
    }
}
