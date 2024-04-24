package com.example.demo.domain.serviceImpl.Paseadores;

import com.example.demo.application.exceptions.PaseadoresExceptions.Exist.PaseadorExistenteException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.CategoriaNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.PaseadorNotFoundException;
import com.example.demo.application.exceptions.UsuariosExceptions.NotFound.UserNotFoundException;
import com.example.demo.application.service.PaseadoresServices.PaseadorService;
import com.example.demo.domain.entity.paseadores.Categorias;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Paseadores.CategoriaRepository;
import com.example.demo.domain.repository.Paseadores.PaseadorRepository;
import com.example.demo.domain.repository.Usuarios.UserRepository;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaseadorServiceImpl implements PaseadorService {


    private final UserRepository userRepository;
    private final PaseadorRepository paseadorRepository;
    private final CategoriaRepository categoriaRepository;

    // Servicio para registrar Paseadores
    @Override
    public PaseadoresDTO registrarPaseador(PaseadoresDTO paseadoresDTO) {
        User user = userRepository.findById(paseadoresDTO.getIdUsuario())
                .orElseThrow(() -> new UserNotFoundException("Id de usuario no encontrado"));

        // Lógica para verificar si el usuario ya tiene un paseador asignado
        if (paseadorRepository.existsByUser(user)) {
            throw new PaseadorExistenteException("Este paseador ya tiene una cuenta como paseador");
        }

        Categorias categorias = categoriaRepository.findById(paseadoresDTO.getIdCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("Id de la categoría no encontrado"));

        // Implementar validaciones necesarias después
        Paseadores paseadores = Paseadores.builder()
                .calificacion(paseadoresDTO.getCalificacion())
                .descripcion(paseadoresDTO.getDescripcion())
                .experiencia(paseadoresDTO.getExperiencia())
                .ubicacion(paseadoresDTO.getUbicacion())
                .tarifa(paseadoresDTO.getTarifa())
                .saldo(paseadoresDTO.getSaldo())
                .disponibilidad(paseadoresDTO.getDisponibilidad())
                .categorias(categorias)
                .user(user)
                .build();

        // Guardando al paseador en la base de datos usando el repositorio
        paseadores = paseadorRepository.save(paseadores);
        // Personalizando respuesta
        Integer IdUsuario = paseadores.getUser().getId();
        Integer IdTipoUsuario = paseadores.getUser().getTiposUsuario().getIdTipoUsuario();
        Integer IdPaseador = paseadores.getIdPaseador();
        String nombres = paseadores.getUser().getNombres();

        return PaseadoresDTO.builder()
                .idUsuario(IdUsuario)
                .IdTipoUsuario(IdTipoUsuario)
                .IdPaseador(IdPaseador)
                .nombres(nombres)
                .idCategoria(paseadoresDTO.getIdCategoria())
                .calificacion(paseadoresDTO.getCalificacion())
                .descripcion(paseadoresDTO.getDescripcion())
                .experiencia(paseadoresDTO.getExperiencia())
                .ubicacion(paseadoresDTO.getUbicacion())
                .tarifa(paseadoresDTO.getTarifa())
                .saldo(paseadoresDTO.getSaldo())
                .disponibilidad(paseadoresDTO.getDisponibilidad())
                .build();
    }


    // Servicio para editar paseadores
    @Override
    public PaseadoresDTO editarPaseador(Integer id_paseador, PaseadoresDTO paseadoresDTO) {
        Paseadores paseadorExistente = paseadorRepository.findById(id_paseador)
                .orElseThrow(() -> new PaseadorNotFoundException("Paseador no encontrado"));

        User user = paseadorExistente.getUser();
        if (paseadoresDTO.getIdUsuario() != null) {
            user = userRepository.findById(paseadoresDTO.getIdUsuario())
                    .orElseThrow(() -> new UserNotFoundException("Id de usuario no encontrado"));
        }
        Categorias categorias = paseadorExistente.getCategorias();
        if (paseadoresDTO.getIdCategoria() != null) {
            categorias = categoriaRepository.findById(paseadoresDTO.getIdCategoria())
                    .orElseThrow(() -> new CategoriaNotFoundException("Id de la categoría no encontrado"));
        }

        // Verifica cada campo antes de actualizar
        if (paseadoresDTO.getCalificacion() != null) {
            paseadorExistente.setCalificacion(paseadoresDTO.getCalificacion());
        }
        if (paseadoresDTO.getDescripcion() != null) {
            paseadorExistente.setDescripcion(paseadoresDTO.getDescripcion());
        }
        if (paseadoresDTO.getExperiencia() != null) {
            paseadorExistente.setExperiencia(paseadoresDTO.getExperiencia());
        }
        if (paseadoresDTO.getUbicacion() != null) {
            paseadorExistente.setUbicacion(paseadoresDTO.getUbicacion());
        }
        if (paseadoresDTO.getTarifa() != null) {
            paseadorExistente.setTarifa(paseadoresDTO.getTarifa());
        }
        if (paseadoresDTO.getSaldo() != null) {
            paseadorExistente.setSaldo(paseadoresDTO.getSaldo());
        }
        if (paseadoresDTO.getDisponibilidad() != null) {
            paseadorExistente.setDisponibilidad(paseadoresDTO.getDisponibilidad());
        }

        paseadorExistente = paseadorRepository.save(paseadorExistente);
        // Personalizando respuesta
        Integer IdUsuario = paseadorExistente.getUser().getId();
        Integer IdTipoUsuario = paseadorExistente.getUser().getTiposUsuario().getIdTipoUsuario();
        Integer IdPaseador = paseadorExistente.getIdPaseador();
        String nombres = paseadorExistente.getUser().getNombres();

        // Devuelve la respuesta con los campos actualizados
        return PaseadoresDTO.builder()
                .idUsuario(paseadorExistente.getUser().getId())
                .IdTipoUsuario(paseadorExistente.getUser().getTiposUsuario().getIdTipoUsuario())
                .IdPaseador(paseadorExistente.getIdPaseador())
                .nombres(paseadorExistente.getUser().getNombres())
                .idCategoria(paseadoresDTO.getIdCategoria())
                .calificacion(paseadorExistente.getCalificacion())
                .descripcion(paseadorExistente.getDescripcion())
                .experiencia(paseadorExistente.getExperiencia())
                .ubicacion(paseadorExistente.getUbicacion())
                .tarifa(paseadorExistente.getTarifa())
                .saldo(paseadorExistente.getSaldo())
                .disponibilidad(paseadorExistente.getDisponibilidad())
                .build();
    }

    // Servicio para mostrar a los paseadores registrados
    @Override
    public List<PaseadorProjection> findBy() {
        return paseadorRepository.findBy();
    }


    // Servicio para listar un registro de paseador por ID
    @Override
    public Optional<PaseadorProjection> findPaseadoresByIdPaseador(Integer idPaseador) {
        return paseadorRepository.findPaseadoresByIdPaseador(idPaseador);
    }


    // Servicio para eliminar la locación de un paseador
    @Override
    public boolean deletePaseadorById(Integer idPaseador) {
        Paseadores paseadores = paseadorRepository.findById(idPaseador)
                .orElseThrow(()-> new PaseadorNotFoundException("Id del paseador inexistente"+ idPaseador));
        paseadorRepository.delete(paseadores);
        System.out.println("Se eliminó al paseador con el ID: "+ idPaseador);
        return true;

    }


}

