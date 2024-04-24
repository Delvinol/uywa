package com.example.demo.infrastructure.security.configuration.authentication;

import com.example.demo.application.exceptions.UsuariosExceptions.NotFound.TipoUsuarioNotFoundException;
import com.example.demo.application.service.UsuariosServices.TiposUsuarioService;
import com.example.demo.domain.entity.usuarios.TiposUsuario;
import com.example.demo.domain.repository.Usuarios.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService service;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private TiposUsuarioService tiposUsuarioService;


    //ENDPOINT para acceder a los servicios de Registros
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        Integer idTipoUsuario = request.getTipoUsuarioId();
        TiposUsuario tipoUsuario = tipoUsuarioRepository.findById(idTipoUsuario)
                .orElseThrow(() -> new TipoUsuarioNotFoundException("Tipo de usuario no encontrado"));
        //Llamando al servicio de registro y pasándole el tipo de usuario asociado
        AuthenticationResponse response = service.register(request, idTipoUsuario);
        return ResponseEntity.ok(response);
    }

    // ENDPOINT para acceder a los servicios de autenticación
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.authenticate(request);

        return ResponseEntity.ok(service.authenticate(request));
    }
}
