package com.example.demo.domain.serviceImpl.Usuarios;

import com.example.demo.application.service.UsuariosServices.UserService;
import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.domain.repository.Usuarios.UserRepository;
import com.example.demo.infrastructure.security.configuration.ProjectService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.UserDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.UserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectService projectService;

    // Servicio para Listar a los usuarios
    @Override
    public List<UserProjection> findBy() {
        return userRepository.findBy();
    }

    // Servicio para listar un registro por ID
    @Override
    public Optional<UserProjection> findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    // Servicio para editar un usuario por su ID
    @Override
    public String ediUserDetails(Integer id, UserDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (request.getNombres() != null) {
            user.setNombres(request.getNombres());
        }
        if (request.getApellidos() != null) {
            user.setApellidos(request.getApellidos());
        }
        if (request.getApodo() != null) {
            user.setApodo(request.getApodo());
        }
        if (request.getDireccion() != null) {
            user.setDireccion(request.getDireccion());
        }
        if (request.getEdad() != null) {
            user.setEdad(request.getEdad());
        }
        if (request.getCelular() != null) {
            user.setCelular(request.getCelular());
        }
        if (request.getDni() != null) {
            user.setDni(request.getDni());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        // Si la contraseña se actualiza, codifícala y genera un nuevo token
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            // Generando un nuevo token con la informacion actualizada del usuario
            var jwtToken = projectService.generateToken(user);
            // Guardando cambios en la base de datos
            userRepository.save(user);
            // Devolver nuevo token
            return "Usuario actualizado con éxito\nId del usuario actualizado: " + user.getId() + "\nNuevo token: " + jwtToken;
        }
        userRepository.save(user);
        return null;
    }

    // Servicio para eliminar un usuario por su ID
    @Override
    public boolean deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        userRepository.delete(user);
        System.out.println("Se eliminó al usuario con ID: "+ id);
        return true;
    }

}


