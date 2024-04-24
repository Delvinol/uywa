package com.example.demo.application.service.UsuariosServices;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.UserDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.UserProjection;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Implementacion para mostrar usuarios registrados con proyecciones
    List<UserProjection> findBy();

    // Implementación para mostrar un usuario registrado por su ID con proyeccion
    Optional<UserProjection> findUserById(Integer id);

    // Implementación para editar un usuario registrado por medio de su ID
    String ediUserDetails(Integer id, UserDTO request);

    // Implementación para eliminar un usuario por su ID
    boolean deleteUserById(Integer id);


}
