package com.example.demo.domain.repository.Usuarios;

import com.example.demo.domain.entity.usuarios.User;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.UserProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

        // Método para buscar un usuario por su email
        // Carga explícitament el tipo de usuario
        @EntityGraph(attributePaths = "tiposUsuario")
        Optional<User> findByEmail(String email);

        // Método para mostrar el id del tipo de usuario de un usuario
        @Query("SELECT u FROM User u LEFT JOIN FETCH u.tiposUsuario WHERE u.id = :id")
        User loadTipoUsuario(@Param("id") Integer id);

        // Método para obtener todos los usuarios con la proyección
        List<UserProjection> findBy(); // Método para obtener la proyección

        // Método para obtener un usuario por su ID
        Optional<UserProjection> findUserById(Integer id);

}
