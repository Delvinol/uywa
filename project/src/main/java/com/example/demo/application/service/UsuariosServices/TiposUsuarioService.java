package com.example.demo.application.service.UsuariosServices;

import com.example.demo.domain.entity.usuarios.TiposUsuario;
import com.example.demo.domain.repository.Usuarios.TipoUsuarioRepository;

import java.util.List;

public class TiposUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public TiposUsuarioService(TipoUsuarioRepository tipoUsuarioRepository){
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    public List<TiposUsuario> obtenerTodosLosTiposUsuario(){
        return tipoUsuarioRepository.findAll();
    }
}
