package com.example.demo.domain.repository.Usuarios;

import com.example.demo.domain.entity.usuarios.TiposUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TiposUsuario, Integer> {

}
