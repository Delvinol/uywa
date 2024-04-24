package com.example.demo.domain.serviceImpl.Mascotas;

import com.example.demo.application.exceptions.MascotasExceptions.NotFound.MascotaNotFounException;
import com.example.demo.application.exceptions.MascotasExceptions.NotFound.TipoMascotaNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.Exist.PaseadorExistenteException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.CategoriaNotFoundException;
import com.example.demo.application.exceptions.PaseadoresExceptions.NotFound.PaseadorNotFoundException;
import com.example.demo.application.exceptions.PropietariosExceptions.NotFound.PropietarioNotFoundException;
import com.example.demo.application.exceptions.UsuariosExceptions.NotFound.UserNotFoundException;
import com.example.demo.application.service.MascotasServices.MascotaService;
import com.example.demo.domain.entity.mascotas.Mascotas;
import com.example.demo.domain.entity.mascotas.TiposMascota;
import com.example.demo.domain.entity.paseadores.Categorias;
import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Mascotas.MascotasRepository;
import com.example.demo.domain.repository.Propietarios.PropietarioRepository;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.MascotaDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.MascotaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final PropietarioRepository propietarioRepository;
    private final MascotasRepository mascotasRepository;
    private final TiposMascotaRepository tiposMascotaRepository;


    // Servicio para registrar Mascotas
    @Override
    public MascotaDTO registrarMascota(MascotaDTO mascotaDTO) {
        Propietarios propietarios = propietarioRepository.findById(mascotaDTO.getIdPropietario())
                .orElseThrow(() -> new PropietarioNotFoundException("Id de dueño de mascota no encontrado"));
        TiposMascota tiposMascota = tiposMascotaRepository.findById(mascotaDTO.getIdTipoMascota())
                .orElseThrow(()-> new TipoMascotaNotFoundException("Id del tipo de mascota no encontrado"));

        Mascotas mascotas = Mascotas.builder()
                .propietarios(propietarios)
                .tiposMascota(tiposMascota)
                .nombre(mascotaDTO.getNombre())
                .raza(mascotaDTO.getRaza())
                .peso(mascotaDTO.getPeso())
                .edad(mascotaDTO.getEdad())
                .necesidades(mascotaDTO.getNecesidades())
                .build();

        // Guardando a la mascota en la base de datos usando el repositorio
        mascotas = mascotasRepository.save(mascotas);
        // Personalizando respuesta
        Integer IdMascota = mascotas.getIdMascota();
        Integer IdTipoUsuario = mascotas.getPropietarios().getUser().getTiposUsuario().getIdTipoUsuario();
        Integer IdUsuario = mascotas.getPropietarios().getUser().getId();
        Integer IdPropietario = mascotas.getPropietarios().getIdPropietario();
        String nombrePropietario = mascotas.getPropietarios().getUser().getNombres();
        String nombreMascota = mascotas.getNombre();

        return MascotaDTO.builder()
                .IdMascota(IdMascota)
                .idTipoMascota(IdMascota)
                .nombre(nombreMascota)
                .IdTipoUsuario(IdTipoUsuario)
                .idPropietario(IdPropietario)
                .IdUsuario(IdUsuario)
                .PropietarioNombres(nombrePropietario)
                .raza(mascotaDTO.getRaza())
                .peso(mascotaDTO.getPeso())
                .edad(mascotaDTO.getEdad())
                .necesidades(mascotaDTO.getNecesidades())
                .build();
    }

    // Servicio para editar Mascotas
    @Override
    public MascotaDTO editarMascota(Integer id_mascota, MascotaDTO mascotaDTO) {
        Mascotas mascotasExistentes = mascotasRepository.findById(id_mascota)
                .orElseThrow(() -> new MascotaNotFounException("Mascota no encontrado"));

        Propietarios propietarios = mascotasExistentes.getPropietarios();
        if (mascotaDTO.getIdPropietario() != null) {
            propietarios = propietarioRepository.findById(mascotaDTO.getIdPropietario())
                    .orElseThrow(() -> new PropietarioNotFoundException("Id de propietario no encontrado"));
        }
        TiposMascota tiposMascota = mascotasExistentes.getTiposMascota();
        if (mascotaDTO.getIdTipoMascota() != null) {
            tiposMascota = tiposMascotaRepository.findById(mascotaDTO.getIdTipoMascota())
                    .orElseThrow(() -> new TipoMascotaNotFoundException("Id del tipo de mascota no encontrado"));
        }

        mascotasExistentes.setNombre(mascotaDTO.getNombre());
        mascotasExistentes.setRaza(mascotaDTO.getRaza());
        mascotasExistentes.setPeso(mascotaDTO.getPeso());
        mascotasExistentes.setEdad(mascotaDTO.getEdad());
        mascotasExistentes.setNecesidades(mascotaDTO.getNecesidades());

        mascotasExistentes = mascotasRepository.save(mascotasExistentes);
        // Personalizando respuesta
        Integer IdMascota = mascotasExistentes.getIdMascota();
        Integer IdTipoUsuario = mascotasExistentes.getPropietarios().getUser().getTiposUsuario().getIdTipoUsuario();
        Integer IdUsuario = mascotasExistentes.getPropietarios().getUser().getId();
        Integer IdPropietario = mascotasExistentes.getPropietarios().getIdPropietario();
        String nombrePropietario = mascotasExistentes.getPropietarios().getUser().getNombres();
        String nombreMascota = mascotasExistentes.getNombre();

        return MascotaDTO.builder()
                .IdMascota(IdMascota)
                .idTipoMascota(IdMascota)
                .nombre(nombreMascota)
                .IdTipoUsuario(IdTipoUsuario)
                .idPropietario(IdPropietario)
                .IdUsuario(IdUsuario)
                .PropietarioNombres(nombrePropietario)
                .raza(mascotaDTO.getRaza())
                .peso(mascotaDTO.getPeso())
                .edad(mascotaDTO.getEdad())
                .necesidades(mascotaDTO.getNecesidades())
                .build();
    }

    // Servicio para mostrar a las mascotas registradas
    @Override
    public List<MascotaProjection> findBy() {
        return mascotasRepository.findBy();
    }


    // Servicio para listar un registro de mascota por su ID
    @Override
    public Optional<MascotaProjection> findMascotaByIdMascota(Integer idMascota) {
        return mascotasRepository.findMascotasByIdMascota(idMascota);
    }


    // Servicio para eliminar un registro de mascota por su ID
    @Override
    public boolean deleteMascotaById(Integer idMascota) {
        Mascotas mascotas = mascotasRepository.findById(idMascota)
                .orElseThrow(()-> new MascotaNotFounException("Id DE LA MASCOTA INEXISTENTE" + idMascota));
        mascotasRepository.delete(mascotas);
        System.out.println("Se eliminó correcramente a la mascota ");
        return true;
    }
}
