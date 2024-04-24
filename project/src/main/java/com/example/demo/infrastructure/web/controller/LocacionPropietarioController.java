package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PaseadoresServices.LocacionPaseadorService;
import com.example.demo.application.service.PropietariosServices.LocacionPropietarioService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPaseadorDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPropietarioProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/LocacionPropietario")
@RequiredArgsConstructor
public class LocacionPropietarioController {

    private final LocacionPropietarioService locacionPropietarioService;

    // Endpoint para registrar Locación de Paseadores
    @PostMapping("/register")
    public ResponseEntity<LocacionPropietarioDTO> registrarLocacionPropietario(@RequestBody LocacionPropietarioDTO locacionPropietarioDTO){
        LocacionPropietarioDTO locacionPropietarioRegistrado = locacionPropietarioService.registrarLocacionPropietario(locacionPropietarioDTO);
        return ResponseEntity.ok(locacionPropietarioRegistrado);
    }

    // Endpoint para editar Locación de Paseadores
    @PutMapping("/edit/{id}")
    public ResponseEntity<LocacionPropietarioDTO> editarLocacionPropietario(
            @PathVariable("id") Integer id,
            @RequestBody LocacionPropietarioDTO locacionPropietarioDTO
    ){
        LocacionPropietarioDTO locacionPropietarioActualizado = locacionPropietarioService.editarLocacionPropietario(id, locacionPropietarioDTO);
        return ResponseEntity.ok(locacionPropietarioActualizado);
    }

    // Endpoint para listar todas las locaciones de los paseadores usando proyecciones
    @GetMapping("/findAllLocacionPropietarios")
    public List<LocacionPropietarioProjection> findAllLocacionPropietario(){
        return locacionPropietarioService.findBy();
    }

    // Endpoint para listar una locación de paseador por su ID
    @GetMapping("/findLocacionPropietario/{idLocacionPropietario}")
    public Optional<LocacionPropietarioProjection> findLocacionPropietarioById(@PathVariable Integer idLocacionPropietario){
        return locacionPropietarioService.findLocacionPropietarioByIdLocacionPropietario(idLocacionPropietario);
    }

    // Endpoint para eliminar una locación de paseador por su ID
    @DeleteMapping("/delete/{idLocacionPropietario}")
    public ResponseEntity<String> deleteLocacionPropietarioById(@PathVariable("idLocacionPropietario") Integer idLocacionPropietario){
        boolean deleted = locacionPropietarioService.deleteLocacionPropietarioById(idLocacionPropietario);

        if (deleted) {
            return ResponseEntity.ok("Locacion de propietario eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar la locación del propietario a eliminar");
        }

    }
}
