package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PropietariosServices.PropietarioService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PropietarioProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/propietario")
@RequiredArgsConstructor
public class PropietarioController {

    private final PropietarioService propietarioService;

    // Endpoint para registrar Propietarios
    @PostMapping("/register")
    public ResponseEntity<PropietarioDTO> registrarPropietario(@RequestBody PropietarioDTO propietarioDTO){
        PropietarioDTO propietarioRegistrado = propietarioService.registrarPropietario(propietarioDTO);
        return ResponseEntity.ok(propietarioRegistrado);
    }

    // Endpoint para editar Propietarios
    @PutMapping("/edit/{id}")
    public ResponseEntity<PropietarioDTO> editarPropietario(
            @PathVariable("id") Integer id,
            @RequestBody PropietarioDTO propietarioDTO
    ){
        PropietarioDTO propietarioActualizado = propietarioService.editarPropietario(id, propietarioDTO);
        return ResponseEntity.ok(propietarioActualizado);
    }

    // Endpoint para listar a todos los propietarios usando proyecciones
    @GetMapping("/findAllPropietarios")
    public List<PropietarioProjection> findAllPropietarios(){
        return propietarioService.findBy(); // Obtiene todos los propietarios con la proyección
    }

    // Endpoint para listar un propietario por un ID
    @GetMapping("/findPropietarioById/{idPropietario}")
    public Optional<PropietarioProjection> findPropietarioById(@PathVariable Integer idPropietario){
        return propietarioService.findPropietariosByIdPropietario(idPropietario);
    }

    // Endpoint para eliminar un propietario por un ID
    @DeleteMapping("/delete/{idPropietario}")
    public ResponseEntity<String> deletePropietarioById(@PathVariable("idPropietario") Integer idPropietario){
        boolean deleted = propietarioService.deletePropietarioById(idPropietario);

        if (deleted){
            return ResponseEntity.ok("Propietario eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el propietario a eliminar");
        }
    }
}
